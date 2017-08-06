//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.context;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class InvocationInfoProxy {
    private static final ThreadLocal<InvocationInfo> threadLocal = new ThreadLocal() {
        protected InvocationInfo initialValue() {
            return new InvocationInfo();
        }
    };

    public InvocationInfoProxy() {
    }

    public static void reset() {
        threadLocal.remove();
    }

    public static String getSysid() {
        return ((InvocationInfo)threadLocal.get()).sysid;
    }

    public static void setSysid(String sysid) {
        ((InvocationInfo)threadLocal.get()).sysid = sysid;
    }

    public static String getTenantid() {
        return ((InvocationInfo)threadLocal.get()).tenantid;
    }

    public static void setTenantid(String tenantid) {
        ((InvocationInfo)threadLocal.get()).tenantid = tenantid;
    }

    public static String getUserid() {
        return ((InvocationInfo)threadLocal.get()).userid;
    }

    public static void setUserid(String userid) {
        ((InvocationInfo)threadLocal.get()).userid = userid;
    }

    public static String getCallid() {
        return ((InvocationInfo)threadLocal.get()).callid;
    }

    public static void setCallid(String callid) {
        ((InvocationInfo)threadLocal.get()).callid = callid;
    }

    public static String getToken() {
        return ((InvocationInfo)threadLocal.get()).token;
    }

    public static void setToken(String token) {
        ((InvocationInfo)threadLocal.get()).token = token;
    }

    public static String getLogints() {
        return ((InvocationInfo)threadLocal.get()).logints;
    }

    public static void setLogints(String logints) {
        ((InvocationInfo)threadLocal.get()).logints = logints;
    }

    public static String getTheme() {
        return ((InvocationInfo)threadLocal.get()).theme;
    }

    public static void setTheme(String theme) {
        ((InvocationInfo)threadLocal.get()).theme = theme;
    }

    public static String getUsername() {
        return ((InvocationInfo)threadLocal.get()).username;
    }

    public static void setUsername(String username) {
        ((InvocationInfo)threadLocal.get()).username = username;
    }

    public static String getAppCode() {
        return ((InvocationInfo)threadLocal.get()).appCode;
    }

    public static void setAppCode(String appCode) {
        ((InvocationInfo)threadLocal.get()).appCode = appCode;
    }

    public static String getProviderCode() {
        return ((InvocationInfo)threadLocal.get()).providerCode;
    }

    public static void setProviderCode(String providerCode) {
        ((InvocationInfo)threadLocal.get()).providerCode = providerCode;
    }

    public static String getLocale() {
        return ((InvocationInfo)threadLocal.get()).locale;
    }

    public static void setLocale(String locale) {
        ((InvocationInfo)threadLocal.get()).locale = locale;
    }

    public static Object getExtendAttribute(String key) {
        return ((InvocationInfo)threadLocal.get()).extendAttributes.get(key);
    }

    public static <T> T getExtendAttribute(Class<T> cls) {
        return (T)((InvocationInfo)threadLocal.get()).extendAttributes.get(cls);
    }

    public static void setExtendAttribute(Object key, Object value) {
        ((InvocationInfo)threadLocal.get()).extendAttributes.put(key, value);
    }

    public static Map<String, String> getParamters() {
        return ((InvocationInfo)threadLocal.get()).parameters;
    }

    public static String getParameter(String parameter) {
        return (String)((InvocationInfo)threadLocal.get()).parameters.get(parameter);
    }

    public static String setParameter(String parameter, String value) {
        return (String)((InvocationInfo)threadLocal.get()).parameters.put(parameter, value);
    }

    public static Iterator<Entry<String, String>> getSummry() {
        return ((InvocationInfo)threadLocal.get()).getSummry();
    }

    public static String getTimeZone() {
        return ((InvocationInfo)threadLocal.get()).timezone;
    }

    public static void setTimeZone(String timeZone) {
        if(timeZone != null) {
            ((InvocationInfo)threadLocal.get()).timezone = timeZone;
        }

    }
}
