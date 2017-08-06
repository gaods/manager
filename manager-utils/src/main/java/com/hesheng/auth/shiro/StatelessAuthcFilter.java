//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.shiro;


import com.alibaba.fastjson.JSONObject;
import com.hesheng.auth.token.ITokenProcessor;
import com.hesheng.auth.token.TokenFactory;
import com.hesheng.auth.token.TokenParameter;
import com.hesheng.context.InvocationInfoProxy;
import com.hesheng.utils.CookieUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class StatelessAuthcFilter extends AccessControlFilter {
    private static final Logger log = LoggerFactory.getLogger(StatelessAuthcFilter.class);
    public static final int HTTP_STATUS_AUTH = 306;
    private static final String DEDAULT_LOCALE = "zh_CN";
    private String sysid;
    @Autowired
    private TokenFactory tokenFactory;
    private String[] esc = new String[]{"/","null","/logout", "/login", "/formLogin", ".jpg", ".png", ".gif", ".css", ".js", ".jpeg", "/oauth_login", "/oauth_approval",".woff2","/fonts",".woff","tologin","/hs","/ad"};
    private List<String> excludCongtextKeys = Arrays.asList(new String[]{"u_sysid", "tenantid", "u_callid", "u_usercode", "token", "u_logints", "u_locale", "u_theme", "u_timezone", "current_user_name", "call_thread_id", "current_tenant_id"});

    public StatelessAuthcFilter() {
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public void setTokenFactory(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    public void setEsc(String[] esc) {
        this.esc = esc;
    }

    public void setExcludCongtextKeys(List<String> excludCongtextKeys) {
        this.excludCongtextKeys = excludCongtextKeys;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean isAjax = this.isAjax(request);
        HttpServletRequest hReq = (HttpServletRequest)request;
        Cookie[] cookies = hReq.getCookies();
//        String authority = hReq.getHeader("Authority");
//        String logints;
//        String appCode;
//        String providerCode;
//        if(StringUtils.isNotBlank(authority)) {
//            HashSet tokenStr = new HashSet();
//            String[] cookieUserName = authority.split(";");
//            String[] theme = cookieUserName;
//            int locale = cookieUserName.length;
//
//            for(int timeZone = 0; timeZone < locale; ++timeZone) {
//                logints = theme[timeZone];
//                String[] callerThreadId = logints.split("=");
//                if(callerThreadId.length >= 2) {
//                    appCode = StringUtils.trim(callerThreadId[0]);
//                    providerCode = StringUtils.trim(callerThreadId[1]);
//                    Cookie username = new Cookie(appCode, providerCode);
//                    tokenStr.add(username);
//                }
//            }
//
//            cookies = (Cookie[])tokenStr.toArray(new Cookie[0]);
//        }

        String var25 = CookieUtil.findCookieValue(cookies, "token");
        String var26 = CookieUtil.findCookieValue(cookies, "username");

        if(StringUtils.isEmpty(this.sysid)) {
            throw new Exception("sysid is empty! add  sysid  parameter to \'StatelessAuthcFilter\' bean in application-shiro.xml");
        } else {
            String var31 = request.getParameter("username");
            if(var31 == null && StringUtils.isNotBlank(var26)) {
                var31 = var26;
            }

            boolean needCheck = !this.include(hReq);
            if(!needCheck) {
                return true;
            } else if(var25 != null && var31 != null) {
//                HashMap params = new HashMap(request.getParameterMap());
//                ITokenProcessor tokenProcessor = this.tokenFactory.getTokenProcessor(var25);
//                TokenParameter tp = tokenProcessor.getTokenParameterFromCookie(cookies);
//                StatelessToken token = new StatelessToken(var31, tokenProcessor, tp, params, new String(var25));

                try {
                    InvocationInfoProxy.setSysid(this.sysid);
                    //this.getSubject(request, response).login(token);
                    InvocationInfoProxy.setUserid(var31);
                    InvocationInfoProxy.setToken(var25);

                    this.initExtendParams(cookies);
                   // this.initMDC();
                    this.afterValidate(hReq);
                    return true;
                } catch (Exception var24) {
                    log.error(var24.getMessage(), var24);
                    if(isAjax && var24 instanceof AuthenticationException) {
                        this.onAjaxAuthFail(request, response);
                        return false;
                    } else {
                        this.onLoginFail(request, response);
                        return false;
                    }
                }
            } else {
                if(isAjax) {
                    this.onAjaxAuthFail(request, response);
                } else {
                    this.onLoginFail(request, response);
                }

                return false;
            }
        }
    }

    protected void prepareInvocationInfo(String theme, String locale, String timeZone, ServletRequest request, String appCode, String providerCode) {

    }

    private String getLocaleByUserAgent(ServletRequest request) {
        Locale clientLocale = request.getLocale();
        return clientLocale.toString();
    }

    private boolean isAjax(ServletRequest request) {
        boolean isAjax = false;
        if(request instanceof HttpServletRequest) {
            HttpServletRequest rq = (HttpServletRequest)request;
            String requestType = rq.getHeader("X-Requested-With");
            if(requestType != null && "XMLHttpRequest".equals(requestType)) {
                isAjax = true;
            }
        }

        return isAjax;
    }

    protected void onAjaxAuthFail(ServletRequest request, ServletResponse resp) throws IOException {
        HttpServletResponse response = (HttpServletResponse)resp;
        JSONObject json = new JSONObject();
        json.put("msg", "auth check error!");
        response.setStatus(306);
        response.getWriter().write(json.toString());
    }

    protected void onLoginFail(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setStatus(306);
        this.redirectToLogin(request, httpResponse);
    }

    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest hReq = (HttpServletRequest)request;
        String rURL = hReq.getRequestURI();
        rURL = Base64.encodeBase64URLSafeString(rURL.getBytes());
      //  String loginUrl = this.getLoginUrl() + "?r=" + rURL;
        String loginUrl = this.getLoginUrl() ;
        WebUtils.issueRedirect(request, response, loginUrl);
    }

    public boolean include(HttpServletRequest request) {
        String u = request.getRequestURI();
        String[] exeludeStr = this.esc;
        int customExcludes = exeludeStr.length;

        for(int arr$ = 0; arr$ < customExcludes; ++arr$) {
            String len$ = exeludeStr[arr$];
            if(u.endsWith(len$)) {
                return true;
            }
        }

//        String var9 = PropertyUtil.getPropertyByKey("filter_excludes");
//        if(StringUtils.isNotBlank(var9)) {
//            String[] var10 = var9.split(",");
//            String[] var11 = var10;
//            int var12 = var10.length;
//
//            for(int i$ = 0; i$ < var12; ++i$) {
//                String e = var11[i$];
//                if(u.endsWith(e)) {
//                    return true;
//                }
//            }
//        }

        return false;
    }

    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        super.afterCompletion(request, response, exception);
        InvocationInfoProxy.reset();
        this.clearMDC();
    }

    private void initExtendParams(Cookie[] cookies) {
        Cookie[] arr$ = cookies;
        int len$ = cookies.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Cookie cookie = arr$[i$];
            String cname = cookie.getName();
            String cvalue = cookie.getValue();
            if(!this.excludCongtextKeys.contains(cname)) {
                InvocationInfoProxy.setParameter(cname, cvalue);
            }
        }

    }

    private void initMDC() {
        String username = "";
        Subject subject = SecurityUtils.getSubject();
        if(subject != null && subject.getPrincipal() != null) {
            username = (String)SecurityUtils.getSubject().getPrincipal();
        }

        MDC.put("current_user_name", username);
        String call_thread_id = InvocationInfoProxy.getCallid();
        if(StringUtils.isBlank(call_thread_id)) {
            //call_thread_id = ThreadCallerIdGenerator.genCallerThreadId();
            InvocationInfoProxy.setCallid(call_thread_id);
        } else {
            MDC.put("call_thread_id", call_thread_id);
        }

        MDC.put("current_tenant_id", InvocationInfoProxy.getTenantid());
        MDC.put("current_app_code", InvocationInfoProxy.getAppCode());
        MDC.put("current_provider_code", InvocationInfoProxy.getProviderCode());
        this.initCustomMDC();
    }

    protected void initCustomMDC() {
    }

    protected void afterValidate(HttpServletRequest hReq) {
    }

    protected void clearMDC() {
        MDC.remove("current_user_name");
        MDC.remove("call_thread_id");
        MDC.remove("current_tenant_id");
        MDC.remove("current_app_code");
        MDC.remove("current_provider_code");
        this.clearCustomMDC();
    }

    protected void clearCustomMDC() {
    }
}
