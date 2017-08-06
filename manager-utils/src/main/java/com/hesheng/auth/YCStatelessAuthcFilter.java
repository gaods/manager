//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


import com.hesheng.auth.shiro.StatelessAuthcFilter;
import com.hesheng.context.InvocationInfoProxy;
import com.hesheng.utils.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YCStatelessAuthcFilter extends StatelessAuthcFilter {
    private static final Logger logger = LoggerFactory.getLogger(YCStatelessAuthcFilter.class);

    public YCStatelessAuthcFilter() {
    }

    protected void afterValidate(HttpServletRequest hReq) {
        Cookie[] cookies = hReq.getCookies();
        String wb_userCode = CookieUtil.findCookieValue(cookies, "_A_P_userLoginName");
        String userCode = wb_userCode;
        if(StringUtils.isEmpty(wb_userCode)) {
            userCode = CookieUtil.findCookieValue(cookies, "userCode");
        }

        InvocationInfoProxy.setParameter("userCode", userCode);
    }
}
