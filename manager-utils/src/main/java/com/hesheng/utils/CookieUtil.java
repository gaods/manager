//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil {
    private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public CookieUtil() {
    }

    public static Cookie createCookie(String key, String value) {
        return createCookie(key, value, true);
    }

    public static Cookie createCookie(String key, String value, boolean httpOnly) {
        Cookie cookie = null;

        try {
            cookie = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
            cookie.setHttpOnly(httpOnly);
            cookie.setMaxAge(-1);
            cookie.setPath("/");
        } catch (UnsupportedEncodingException var5) {
            logger.error("encode error!", var5);
        }

        return cookie;
    }

    public static String findCookieValue(Cookie[] cookies, String key) {
        if(cookies != null) {
            Cookie[] var2 = cookies;
            int var3 = cookies.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Cookie cookie = var2[var4];
                if(cookie.getName().equals(key)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException var7) {
                        logger.error("decode error!", var7);
                    }
                }
            }
        }

        return null;
    }

    public static Cookie expireCookieWithPath(String key, String path) {
        Cookie cookie = new Cookie(key, (String)null);
        cookie.setMaxAge(0);
        cookie.setPath(path);
        return cookie;
    }
}
