//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class InvocationInfo {
    String sysid;
    String tenantid;
    String userid;
    String callid;
    String token;
    String theme;
    String locale;
    String logints;
    String timezone;
    String username;
    String appCode;
    String providerCode;
    Map<Object, Object> extendAttributes = new HashMap();
    Map<String, String> parameters = new HashMap();

    InvocationInfo() {
    }

    public Iterator<Entry<String, String>> getSummry() {
        HashMap map = new HashMap();
        map.putAll(this.parameters);
        map.put("sysid", this.sysid);
        map.put("token", this.token);
        map.put("tenantid", this.tenantid);
        map.put("userid", this.userid);
        map.put("callid", this.callid);
        map.put("locale", this.locale);
        map.put("logints", this.logints);
        map.put("theme", this.theme);
        map.put("timezone", this.timezone);
        map.put("username", this.username);
        map.put("appCode", this.appCode);
        map.put("providerCode", this.providerCode);
        return map.entrySet().iterator();
    }
}
