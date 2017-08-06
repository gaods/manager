//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.token;

import java.util.HashMap;
import java.util.Map;

public class TokenParameter {
    private String userid;
    private String logints;
    Map<String, String> ext = new HashMap();

    public TokenParameter() {
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Map<String, String> getExt() {
        return this.ext;
    }

    public String getLogints() {
        return this.logints;
    }

    public void setLogints(String logints) {
        this.logints = logints;
    }
}
