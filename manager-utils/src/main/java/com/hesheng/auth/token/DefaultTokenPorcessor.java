//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.token;



import com.hesheng.auth.session.SessionManager;
import com.hesheng.security.utils.security.esapi.EncryptException;

import com.hesheng.utils.CookieUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class DefaultTokenPorcessor implements ITokenProcessor {
    private static Logger log = LoggerFactory.getLogger(DefaultTokenPorcessor.class);
    private static int HTTPVERSION = 3;
    private String id;
    private String domain;
    private String path = "/";
    private Integer expr;
    private int hashIterations = 2;
    @Autowired
    private SessionManager sessionManager;
    private List<String> exacts = new ArrayList();

    public DefaultTokenPorcessor() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getExpr() {
        return this.expr;
    }

    public void setExpr(Integer expr) {
        this.expr = expr;
    }

    public void setExacts(List<String> exacts) {
        this.exacts = exacts;
    }

    public int getHashIterations() {
        return this.hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public String generateToken(TokenParameter tp) {
        try {
            String e = this.sessionManager.findSeed();
          //  String token = IUAPESAPI.encryptor().hash(this.id + tp.getUserid() + tp.getLogints() + this.getSummary(tp) + this.getExpr(), e, this.getHashIterations());
            String token = this.id + "," + this.getExpr() + "," + tp.getUserid();
            return Base64.encodeBase64URLSafeString(StringUtils.getBytesUtf8(token));
        } catch (EncryptException var4) {
            log.error("TokenParameter is not validate!", var4);
            throw new IllegalArgumentException("TokenParameter is not validate!");
        }
    }

    public Cookie[] getCookieFromTokenParameter(TokenParameter tp) {
        ArrayList cookies = new ArrayList();
        String tokenStr = this.generateToken(tp);
        Cookie token = new Cookie("token", tokenStr);
        if(HTTPVERSION == 3) {
            token.setHttpOnly(true);
        }

        if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
            token.setDomain(this.domain);
        }

        token.setPath(this.path);
        cookies.add(token);

        try {
            Cookie it = new Cookie("u_usercode", URLEncoder.encode(tp.getUserid(), "UTF-8"));
            if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
                it.setDomain(this.domain);
            }

            it.setPath(this.path);
            cookies.add(it);
            Cookie i = new Cookie("u_logints", URLEncoder.encode(tp.getLogints(), "UTF-8"));
            if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
                i.setDomain(this.domain);
            }

            i.setPath(this.path);
            cookies.add(i);
        } catch (UnsupportedEncodingException var8) {
            log.error("encode error!", var8);
        }

        if(!tp.getExt().isEmpty()) {
            Iterator it1 = tp.getExt().entrySet().iterator();

            while(it1.hasNext()) {
                Entry i1 = (Entry)it1.next();
                Cookie ext = new Cookie((String)i1.getKey(), (String)i1.getValue());
                if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
                    ext.setDomain(this.domain);
                }

                ext.setPath(this.path);
                cookies.add(ext);
            }
        }

        this.sessionManager.registOnlineSession(tp.getUserid(), tokenStr, this);
        return (Cookie[])cookies.toArray(new Cookie[0]);
    }

    public TokenParameter getTokenParameterFromCookie(Cookie[] cookies) {
        TokenParameter tp = new TokenParameter();
        String token = CookieUtil.findCookieValue(cookies, "token");
        TokenInfo ti = TokenFactory.getTokenInfo(token);
        if(ti.getIntegerExpr().intValue() != this.getExpr().intValue()) {
            throw new IllegalArgumentException("illegal token!");
        } else {
            String userid = CookieUtil.findCookieValue(cookies, "u_usercode");
            tp.setUserid(userid);
            String logints = CookieUtil.findCookieValue(cookies, "u_logints");
            tp.setLogints(logints);
//            if(this.exacts != null && !this.exacts.isEmpty()) {
//                for(int i = 0; i < cookies.length; ++i) {
//                    Cookie cookie = cookies[i];
//                    String name = cookie.getName();
//                    if(this.exacts.contains(name)) {
//                        tp.getExt().put(name, cookie.getValue());
//                    }
//                }
//            }

            return tp;
        }
    }

    protected String getSummary(TokenParameter tp) {
        if(this.exacts != null && !this.exacts.isEmpty()) {
            int len = this.exacts.size();
            String[] exa = new String[len];

            for(int i = 0; i < len; ++i) {
                String name = (String)this.exacts.get(i);
                if(i != -1) {
                    exa[i] = (String)tp.getExt().get(name);
                }
            }

            return org.apache.commons.lang.StringUtils.join(exa, "#");
        } else {
            return "";
        }
    }

    public Cookie[] getLogoutCookie(String tokenStr, String uid) {
        ArrayList cookies = new ArrayList();
        Cookie token = new Cookie("token", (String)null);
        if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
            token.setDomain(this.domain);
        }

        token.setPath(this.path);
        cookies.add(token);
        Cookie userid = new Cookie("u_usercode", (String)null);
        if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
            userid.setDomain(this.domain);
        }

        userid.setPath(this.path);
        cookies.add(userid);
        Cookie logints = new Cookie("u_logints", (String)null);
        if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
            logints.setDomain(this.domain);
        }

        logints.setPath(this.path);
        cookies.add(logints);
        Iterator i$ = this.exacts.iterator();

        while(i$.hasNext()) {
            String exact = (String)i$.next();
            Cookie ext = new Cookie(exact, (String)null);
            if(org.apache.commons.lang.StringUtils.isNotEmpty(this.domain)) {
                ext.setDomain(this.domain);
            }

            ext.setPath(this.path);
            cookies.add(ext);
        }

        this.sessionManager.delOnlineSession(uid, tokenStr);
        return (Cookie[])cookies.toArray(new Cookie[0]);
    }

    static {
        URL res = DefaultTokenPorcessor.class.getClassLoader().getResource("javax/servlet/annotation/WebServlet.class");
        if(res == null) {
            HTTPVERSION = 2;
        }

    }
}
