//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.token;

import java.io.Serializable;

public class TokenInfo implements Serializable {
    private static final long serialVersionUID = 4365301079641325282L;
    String token;
    String processor;
    String expr;

    public TokenInfo() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProcessor() {
        return this.processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getExpr() {
        return this.expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public Integer getIntegerExpr() {
        return Integer.valueOf(Integer.parseInt(this.getExpr()));
    }
}
