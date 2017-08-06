//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.session;


import com.hesheng.auth.token.ITokenProcessor;
import com.hesheng.security.utils.security.esapi.EncryptException;

import java.io.Serializable;
import java.util.Map;

public interface ISessionManager {
    String TOKEN_SEED = "token_seed";
    String SESSION_PREFIX = "IUAP_SESSION_USER:";
    String ONLINE_SESSIONS_MAP = "ONLINE_SESSIONS_MAP";
    String DEFAULT_CHARSET = "UTF-8";

    int getSessionCheckSize();

    void setSessionCheckSize(int var1);

    boolean isSessionMutex();

    void setSessionMutex(boolean var1);

    String findSeed() throws EncryptException;

    String getSeedValue(String var1);

    Map<String, Object> getAllSessionAttrCache(String var1);

    void removeSessionCache(String var1);

    <T extends Serializable> void putSessionCacheAttribute(String var1, String var2, T var3);

    <T extends Serializable> void putSessionCacheAttribute(String var1, String var2, T var3, int var4);

    <T extends Serializable> void updateSessionCacheAttribute(String var1, String var2, T var3);

    Object getSessionCacheAttribute(String var1, String var2);

    void removeSessionCacheAttribute(String var1, String var2);

    void registOnlineSession(String var1, String var2, ITokenProcessor var3);

    boolean isSessionNeedDelete(String var1, String var2);

    boolean validateOnlineSession(String var1, String var2);

    void delOnlineSession(String var1, String var2);

    void deleteUserSession(String var1);

    int countOnlineUsers();

    long countOnlineSessions();

    void refreshSessionCache() throws Exception;
}
