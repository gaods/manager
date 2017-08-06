////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.hesheng.auth.session.session;
//
//import com.hesheng.SerializUtil;
//import com.hesheng.auth.token.ITokenProcessor;
//import com.hesheng.auth.token.TokenFactory;
//import com.hesheng.security.utils.security.esapi.TokenGenerator;
//import com.hesheng.security.utils.security.esapi.EncryptException;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//
//import java.io.Serializable;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//public class SessionClusterManager implements ISessionManager {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static String localSeedValue = null;
//    private int sessionCheckSize = 20000;
//    private JedisCluster jedisCluster;
//    private boolean sessionMutex = false;
//
//    public SessionClusterManager() {
//    }
//
//    public int getSessionCheckSize() {
//        return this.sessionCheckSize;
//    }
//
//    public void setSessionCheckSize(int sessionCheckSize) {
//        this.sessionCheckSize = sessionCheckSize;
//    }
//
//    public JedisCluster getJedisCluster() {
//        return this.jedisCluster;
//    }
//
//    public void setJedisCluster(JedisCluster jedisCluster) {
//        this.jedisCluster = jedisCluster;
//    }
//
//    public boolean isSessionMutex() {
//        return this.sessionMutex;
//    }
//
//    public void setSessionMutex(boolean sessionMutex) {
//        this.sessionMutex = sessionMutex;
//    }
//
//    public String findSeed() throws EncryptException {
//        if(localSeedValue != null) {
//            return localSeedValue;
//        } else {
//            String seed = this.getSeedValue("token_seed");
//            if(StringUtils.isBlank(seed)) {
//                seed = TokenGenerator.genSeed();
//                localSeedValue = seed;
//                this.set("token_seed", seed);
//            }
//
//            return seed;
//        }
//    }
//
//    public String getSeedValue(String key) {
//        return this.get(key);
//    }
//
//    public Map<String, Object> getAllSessionAttrCache(String sid) {
//        HashMap hashMap = new HashMap();
//        Map redisMap = this.hgetAll(sid);
//        Iterator timeOut = redisMap.keySet().iterator();
//
//        while(timeOut.hasNext()) {
//            byte[] byteKey = (byte[])timeOut.next();
//            String key = new String(byteKey, Charset.forName("UTF-8"));
//            Object obj = SerializUtil.byteToObject((byte[])redisMap.get(byteKey));
//            hashMap.put(key, obj);
//        }
//
//        int timeOut1 = this.getTimeout(sid);
//        if(timeOut1 > 0) {
//            this.expire(sid, timeOut1);
//        }
//
//        return hashMap;
//    }
//
//    public void removeSessionCache(String sid) {
//        this.del(new String[]{sid});
//    }
//
//    public <T extends Serializable> void putSessionCacheAttribute(String sid, String key, T value) {
//        this.putSessionCacheAttribute(sid, key, value, this.getTimeout(sid));
//    }
//
//    public <T extends Serializable> void putSessionCacheAttribute(String sid, String key, T value, int timeout) {
//        this.hset(sid, key, value);
//        if(timeout > 0) {
//            this.expire(sid, timeout);
//        }
//
//    }
//
//    public <T extends Serializable> void updateSessionCacheAttribute(String sid, String key, T value) {
//        if(this.hexists(sid, key).booleanValue()) {
//            this.putSessionCacheAttribute(sid, key, value);
//        }
//
//    }
//
//    public Object getSessionCacheAttribute(String sid, String key) {
//        Serializable result = null;
//        boolean isExist = this.exists(sid).booleanValue();
//        if(isExist) {
//            int timeOut = this.getTimeout(sid);
//            if(timeOut > 0) {
//                this.expire(sid, timeOut);
//            }
//
//            result = this.hget(sid, key);
//        }
//
//        return result;
//    }
//
//    public void removeSessionCacheAttribute(String sid, String key) {
//        if(StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(key)) {
//            this.hdel(sid, new String[]{key});
//        }
//
//    }
//
//    private int getTimeout(String sid) {
//        return TokenFactory.getTokenInfo(sid).getIntegerExpr().intValue();
//    }
//
//    public void registOnlineSession(String userid, String token, ITokenProcessor processor) {
//        String key = "IUAP_SESSION_USER:" + userid;
//        this.logger.debug("token processor id is {},key is {} !", processor.getId(), key);
//        boolean mutex = this.isSessionMutex();
//        if(mutex) {
//            this.deleteUserSession(userid);
//        } else {
//            this.clearOnlineSession(key);
//        }
//
//        String currentTs = String.valueOf(System.currentTimeMillis());
//        this.sessionHset(key, token, currentTs);
//        this.afterRegisteOnlineSession(userid, token, currentTs);
//    }
//
//    private void clearOnlineSession(String key) {
//        Map userSessions = this.getMap(key);
//        if(userSessions != null) {
//            Iterator entries = userSessions.entrySet().iterator();
//
//            while(entries.hasNext()) {
//                Entry entry = (Entry)entries.next();
//                String t = (String)entry.getKey();
//                String v = (String)entry.getValue();
//                long exprMillis = (long)this.getTimeout(t) * 1000L;
//                if(exprMillis > 0L) {
//                    long cts = System.currentTimeMillis();
//                    if(Long.parseLong(v) + exprMillis < cts) {
//                        this.jedisCluster.del(t);
//                    }
//                }
//            }
//        }
//
//        this.afterClearOnlineSession(key, userSessions);
//    }
//
//    public boolean isSessionNeedDelete(String userid, String token) {
//        boolean result = false;
//        String key = "IUAP_SESSION_USER:" + userid;
//        String lastTsStr = this.sessionHget(key, token);
//        if(lastTsStr != null) {
//            int timeOut = this.getTimeout(token);
//            if(timeOut > 0) {
//                long exprMillis = (long)timeOut * 1000L;
//                if(exprMillis > 0L) {
//                    long cts = System.currentTimeMillis();
//                    if(Long.parseLong(lastTsStr) + exprMillis < cts) {
//                        result = true;
//                    }
//                }
//            }
//        } else {
//            result = true;
//        }
//
//        return result;
//    }
//
//    public boolean validateOnlineSession(String userid, String token) {
//        boolean result = false;
//        String key = "IUAP_SESSION_USER:" + userid;
//        String lastTsStr = this.sessionHget(key, token);
//        if(lastTsStr != null) {
//            int timeOut = this.getTimeout(token);
//            if(timeOut <= 0) {
//                return true;
//            }
//
//            ArrayList jedisResult = new ArrayList();
//            long exprMillis = (long)timeOut * 1000L;
//            long cts = System.currentTimeMillis();
//            if(Long.parseLong(lastTsStr) + exprMillis >= cts) {
//                this.jedisCluster.hset(key, token, String.valueOf(cts));
//                this.jedisCluster.expire(token, timeOut);
//                jedisResult.add(Boolean.valueOf(true));
//            } else {
//                this.jedisCluster.hdel(key, new String[]{token});
//            }
//
//            result = jedisResult.size() > 0;
//        }
//
//        return result;
//    }
//
//    public void delOnlineSession(String userid, String token) {
//        String key = "IUAP_SESSION_USER:" + userid;
//        this.jedisCluster.del(token);
//        this.jedisCluster.hdel(key, new String[]{token});
//        this.afterDeleteOnlineSession(userid, token);
//    }
//
//    public void deleteUserSession(String userid) {
//        String key = "IUAP_SESSION_USER:" + userid;
//        Map userSessions = this.getMap(key);
//        Iterator entries = userSessions.entrySet().iterator();
//
//        while(entries.hasNext()) {
//            Entry entry = (Entry)entries.next();
//            String t = (String)entry.getKey();
//            this.jedisCluster.del(t);
//        }
//
//        this.jedisCluster.del(key);
//    }
//
//    public int countOnlineUsers() {
//        return this.getOnlineUserSize().intValue();
//    }
//
//    public long countOnlineSessions() {
//        long count = this.hlength("ONLINE_SESSIONS_MAP").longValue();
//        this.logger.info("current online sessions size is: {} !", Long.valueOf(count));
//        return count;
//    }
//
//    public void refreshSessionCache() throws Exception {
//        long count = this.hlength("ONLINE_SESSIONS_MAP").longValue();
//        if(count < (long)this.getSessionCheckSize()) {
//            Map sessionMap = this.getMap("ONLINE_SESSIONS_MAP");
//            Iterator entries = sessionMap.entrySet().iterator();
//
//            while(entries.hasNext()) {
//                Entry entry = (Entry)entries.next();
//                String t = (String)entry.getKey();
//                String[] array = t.split("\\$\\$");
//                String userid = array[0];
//                String token = array[1];
//                String lastTsStr = array[2];
//                int timeOut = this.getTimeout(token);
//                if(timeOut > 0) {
//                    long exprMillis = (long)timeOut * 1000L;
//                    long cts = System.currentTimeMillis();
//                    if(Long.parseLong(lastTsStr) + exprMillis < cts && this.isSessionNeedDelete(userid, token)) {
//                        this.hdel("ONLINE_SESSIONS_MAP", new String[]{t});
//                        this.hdel("IUAP_SESSION_USER:" + userid, new String[]{token});
//                    }
//                }
//            }
//        }
//
//    }
//
//    private Integer getOnlineUserSize() {
//        int count = 0;
//        Map map = this.jedisCluster.getClusterNodes();
//        Iterator i$ = map.keySet().iterator();
//
//        while(i$.hasNext()) {
//            String k = (String)i$.next();
//            JedisPool jedisPool = (JedisPool)map.get(k);
//            Jedis connection = jedisPool.getResource();
//            if(this.isMaster(connection)) {
//                count += connection.keys("IUAP_SESSION_USER:*").size();
//            }
//        }
//
//        return Integer.valueOf(count);
//    }
//
//    private boolean isMaster(Jedis connection) {
//        String[] nodes = connection.clusterNodes().split("\n");
//        String[] arr$ = nodes;
//        int len$ = nodes.length;
//
//        for(int i$ = 0; i$ < len$; ++i$) {
//            String n = arr$[i$];
//            if(n.contains("myself")) {
//                if(n.contains("master")) {
//                    return true;
//                }
//
//                if(n.contains("slave")) {
//                    return false;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    private void afterRegisteOnlineSession(String userid, String token, String ts) {
//        this.hset("ONLINE_SESSIONS_MAP", userid + "$$" + token + "$$" + ts, userid);
//    }
//
//    private void afterDeleteOnlineSession(String userid, String token) {
//        this.hdel("ONLINE_SESSIONS_MAP", new String[]{userid + "$$" + token});
//    }
//
//    private void afterClearOnlineSession(String key, Map<String, String> userSessions) {
//        String userid = key.replace("IUAP_SESSION_USER:", "");
//        Iterator i$ = userSessions.entrySet().iterator();
//
//        while(i$.hasNext()) {
//            Entry entry = (Entry)i$.next();
//            this.hdel("ONLINE_SESSIONS_MAP", new String[]{userid + "$$" + (String)entry.getKey()});
//        }
//
//    }
//
//    private void set(String key, String value) {
//        this.jedisCluster.set(key, value);
//    }
//
//    private String get(String key) {
//        return this.jedisCluster.get(key);
//    }
//
//    private Boolean exists(String key) {
//        return this.jedisCluster.exists(key);
//    }
//
//    private Boolean del(String... keys) {
//        long num = this.jedisCluster.del(keys).longValue();
//        long keyNum = (long)keys.length;
//        return Boolean.valueOf(num == keyNum);
//    }
//
//    private <T extends Serializable> void hset(String key, String fieldName, T value) {
//        byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
//        byte[] fieldBytes = fieldName.getBytes(Charset.forName("UTF-8"));
//        byte[] valueBytes = SerializUtil.objectToByte(value);
//        this.jedisCluster.hset(keyBytes, fieldBytes, valueBytes);
//    }
//
//    private void sessionHset(String key, String fieldName, String value) {
//        this.jedisCluster.hset(key, fieldName, value);
//    }
//
//    private String sessionHget(String key, String fieldName) {
//        return this.jedisCluster.hget(key, fieldName);
//    }
//
//    private <T extends Serializable> T hget(String key, String fieldName) {
//        byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
//        byte[] fieldBytes = fieldName.getBytes(Charset.forName("UTF-8"));
//        byte[] attrBytes = this.jedisCluster.hget(keyBytes, fieldBytes);
//        return attrBytes == null?null:(T)SerializUtil.byteToObject(attrBytes);
//    }
//
//    private Long hlength(String key) {
//        return this.jedisCluster.hlen(key);
//    }
//
//    private Long hdel(String key, String... fieldsNames) {
//        return this.jedisCluster.hdel(key, fieldsNames);
//    }
//
//    private Boolean hexists(String key, String field) {
//        return this.jedisCluster.hexists(key, field);
//    }
//
//    private Map<byte[], byte[]> hgetAll(String key) {
//        return this.jedisCluster.hgetAll(key.getBytes(Charset.forName("UTF-8")));
//    }
//
//    private Map<String, String> getMap(String key) {
//        return this.jedisCluster.hgetAll(key);
//    }
//
//    private void expire(String key, int timeout) {
//        this.jedisCluster.expire(key, timeout);
//    }
//}
