//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.token;

import javax.servlet.http.Cookie;

public interface ITokenProcessor {
    String getId();

    Integer getExpr();

    String generateToken(TokenParameter var1);

    Cookie[] getCookieFromTokenParameter(TokenParameter var1);

    Cookie[] getLogoutCookie(String var1, String var2);

    TokenParameter getTokenParameterFromCookie(Cookie[] var1);
}
