//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.session;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hesheng.SerializUtil;
import com.hesheng.auth.token.ITokenProcessor;
import com.hesheng.auth.token.TokenFactory;
import com.hesheng.security.utils.security.esapi.TokenGenerator;
import com.hesheng.security.utils.security.esapi.EncryptException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.nosql.redis.JedisUtils;
import org.springside.modules.nosql.redis.JedisTemplate.JedisAction;
import org.springside.modules.nosql.redis.JedisTemplate.JedisActionNoResult;
import org.springside.modules.nosql.redis.JedisTemplate.PipelineAction;
import org.springside.modules.nosql.redis.JedisTemplate.PipelineActionNoResult;
import org.springside.modules.nosql.redis.pool.JedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

public class SessionManager implements ISessionManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String localSeedValue = null;
    private int sessionCheckSize = 20000;
    private JedisPool sessionJedisPool;
    private boolean sessionMutex = false;

    public SessionManager() {
    }

    public int getSessionCheckSize() {
        return this.sessionCheckSize;
    }

    public void setSessionCheckSize(int sessionCheckSize) {
        this.sessionCheckSize = sessionCheckSize;
    }

    public JedisPool getSessionJedisPool() {
        return this.sessionJedisPool;
    }

    public void setSessionJedisPool(JedisPool sessionJedisPool) {
        this.sessionJedisPool = sessionJedisPool;
    }

    public boolean isSessionMutex() {
        return this.sessionMutex;
    }

    public void setSessionMutex(boolean sessionMutex) {
        this.sessionMutex = sessionMutex;
    }

    public String findSeed() throws EncryptException {
        if(localSeedValue != null) {
            return localSeedValue;
        } else {
            String seed = this.getSeedValue("token_seed");
            if(StringUtils.isBlank(seed)) {
                seed = TokenGenerator.genSeed();
                localSeedValue = seed;
                this.set("token_seed", seed);
            }

            return seed;
        }
    }

    public String getSeedValue(String key) {
        return this.get(key);
    }

    public Map<String, Object> getAllSessionAttrCache(String sid) {
        HashMap hashMap = new HashMap();
        Map redisMap = this.hgetAll(sid);
        Iterator timeOut = redisMap.keySet().iterator();

        while(timeOut.hasNext()) {
            byte[] byteKey = (byte[])timeOut.next();
            String key = new String(byteKey, Charset.forName("UTF-8"));
            Object obj = SerializUtil.byteToObject((byte[])redisMap.get(byteKey));
            hashMap.put(key, obj);
        }

        int timeOut1 = this.getTimeout(sid);
        if(timeOut1 > 0) {
            this.expire(sid, timeOut1);
        }

        return hashMap;
    }

    public void removeSessionCache(String sid) {
        this.del(new String[]{sid});
    }

    public <T extends Serializable> void putSessionCacheAttribute(String sid, String key, T value) {
        this.putSessionCacheAttribute(sid, key, value, this.getTimeout(sid));
    }

    public <T extends Serializable> void putSessionCacheAttribute(String sid, String key, T value, int timeout) {
        this.hset(sid, key, value);
        if(timeout > 0) {
            this.expire(sid, timeout);
        }

    }

    public <T extends Serializable> void updateSessionCacheAttribute(String sid, String key, T value) {
        if(this.hexists(sid, key).booleanValue()) {
            this.putSessionCacheAttribute(sid, key, value);
        }

    }

    public Object getSessionCacheAttribute(String sid, String key) {
        Serializable result = null;
        boolean isExist = this.exists(sid).booleanValue();
        if(isExist) {
            int timeOut = this.getTimeout(sid);
            if(timeOut > 0) {
                this.expire(sid, timeOut);
            }

            result = this.hget(sid, key);
        }

        return result;
    }

    public void removeSessionCacheAttribute(String sid, String key) {
        if(StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(key)) {
            this.hdel(sid, new String[]{key});
        }

    }

    private int getTimeout(String sid) {
        return TokenFactory.getTokenInfo(sid).getIntegerExpr().intValue();
    }

    public void registOnlineSession(String userid, String token, ITokenProcessor processor) {
        String key = "IUAP_SESSION_USER:" + userid;
        this.logger.debug("token processor id is {},key is {} !", processor.getId(), key);
        boolean mutex = this.isSessionMutex();
        if(mutex) {
            this.deleteUserSession(userid);
        } else {
            this.clearOnlineSession(key);
        }

        String currentTs = String.valueOf(System.currentTimeMillis());
        this.sessionHset(key, token, currentTs);
        this.afterRegisteOnlineSession(userid, token, currentTs);
    }

    private void clearOnlineSession(final String key) {
        final ArrayList deleteKeys = new ArrayList();
        Map userSessions = this.getMap(key);
        if(userSessions != null) {
            Iterator action = userSessions.entrySet().iterator();

            while(action.hasNext()) {
                Entry entry = (Entry)action.next();
                String t = (String)entry.getKey();
                String v = (String)entry.getValue();
                long exprMillis = (long)this.getTimeout(t) * 1000L;
                if(exprMillis > 0L) {
                    long cts = System.currentTimeMillis();
                    if(Long.parseLong(v) + exprMillis < cts) {
                        deleteKeys.add(t);
                    }
                }
            }
        }

        PipelineActionNoResult action1 = new PipelineActionNoResult() {
            public void action(Pipeline pipeline) {
                for(int i = 0; i < deleteKeys.size(); ++i) {
                    pipeline.hdel(key, new String[]{(String)deleteKeys.get(i)});
                }

            }
        };
        this.execute(action1);
        this.afterClearOnlineSession(key, userSessions);
    }

    public boolean isSessionNeedDelete(String userid, String token) {
        boolean result = false;
        String key = "IUAP_SESSION_USER:" + userid;
        String lastTsStr = this.sessionHget(key, token);
        if(lastTsStr != null) {
            int timeOut = this.getTimeout(token);
            if(timeOut > 0) {
                long exprMillis = (long)timeOut * 1000L;
                if(exprMillis > 0L) {
                    long cts = System.currentTimeMillis();
                    if(Long.parseLong(lastTsStr) + exprMillis < cts) {
                        result = true;
                    }
                }
            }
        } else {
            result = true;
        }

        return result;
    }

    public boolean validateOnlineSession(String userid, final String token) {
        boolean result = false;
        final String key = "IUAP_SESSION_USER:" + userid;
        final String lastTsStr = this.sessionHget(key, token);
        if(lastTsStr != null) {
            final int timeOut = this.getTimeout(token);
            if(timeOut <= 0) {
                return true;
            }

            final ArrayList pipelineResult = new ArrayList();
            PipelineActionNoResult action = new PipelineActionNoResult() {
                public void action(Pipeline pipeline) {
                    long exprMillis = (long)timeOut * 1000L;
                    if(exprMillis > 0L) {
                        long cts = System.currentTimeMillis();
                        if(Long.parseLong(lastTsStr) + exprMillis >= cts) {
                            pipeline.hset(key, token, String.valueOf(cts));
                            pipeline.expire(token, timeOut);
                            pipelineResult.add(Boolean.valueOf(true));
                        } else {
                            pipeline.hdel(key, new String[]{token});
                        }
                    }

                }
            };
            this.execute(action);
            result = pipelineResult.size() > 0;
        }

        return result;
    }

    public void delOnlineSession(String userid, final String token) {
        final String key = "IUAP_SESSION_USER:" + userid;
        PipelineActionNoResult action = new PipelineActionNoResult() {
            public void action(Pipeline pipeline) {
                pipeline.del(token);
                pipeline.hdel(key, new String[]{token});
            }
        };
        this.execute(action);
        this.afterDeleteOnlineSession(userid, token);
    }

    public void deleteUserSession(String userid) {
        final String key = "IUAP_SESSION_USER:" + userid;
        final Map userSessions = this.getMap(key);
        if(userSessions != null) {
            PipelineActionNoResult action = new PipelineActionNoResult() {
                public void action(Pipeline pipeline) {
                    Iterator entries = userSessions.entrySet().iterator();

                    while(entries.hasNext()) {
                        Entry entry = (Entry)entries.next();
                        String t = (String)entry.getKey();
                        pipeline.del(t);
                    }

                    pipeline.del(key);
                }
            };
            this.execute(action);
        }

    }

    public int countOnlineUsers() {
        return this.getOnlineUserSize().intValue();
    }

    public long countOnlineSessions() {
        long count = this.hlength("ONLINE_SESSIONS_MAP").longValue();
        this.logger.info("current online sessions size is: {} !", Long.valueOf(count));
        return count;
    }

    public void refreshSessionCache() throws Exception {
        long count = this.hlength("ONLINE_SESSIONS_MAP").longValue();
        if(count < (long)this.getSessionCheckSize()) {
            Map sessionMap = this.getMap("ONLINE_SESSIONS_MAP");
            Iterator entries = sessionMap.entrySet().iterator();

            while(entries.hasNext()) {
                Entry entry = (Entry)entries.next();
                String t = (String)entry.getKey();
                String[] array = t.split("\\$\\$");
                String userid = array[0];
                String token = array[1];
                String lastTsStr = array[2];
                int timeOut = this.getTimeout(token);
                if(timeOut > 0) {
                    long exprMillis = (long)timeOut * 1000L;
                    long cts = System.currentTimeMillis();
                    if(Long.parseLong(lastTsStr) + exprMillis < cts && this.isSessionNeedDelete(userid, token)) {
                        this.hdel("ONLINE_SESSIONS_MAP", new String[]{t});
                        this.hdel("IUAP_SESSION_USER:" + userid, new String[]{token});
                    }
                }
            }
        }

    }

    private Integer getOnlineUserSize() {
        return (Integer)this.execute(new JedisAction() {
            public Integer action(Jedis jedis) {
                int count = jedis.keys("IUAP_SESSION_USER:*").size();
                SessionManager.this.logger.info("current online users size is: {} !", Integer.valueOf(count));
                return Integer.valueOf(count);
            }
        });
    }

    private void afterRegisteOnlineSession(String userid, String token, String ts) {
        this.hset("ONLINE_SESSIONS_MAP", userid + "$$" + token + "$$" + ts, userid);
    }

    private void afterDeleteOnlineSession(String userid, String token) {
        this.hdel("ONLINE_SESSIONS_MAP", new String[]{userid + "$$" + token});
    }

    private void afterClearOnlineSession(String key, Map<String, String> userSessions) {
        String userid = key.replace("IUAP_SESSION_USER:", "");
        Iterator i$ = userSessions.entrySet().iterator();

        while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            this.hdel("ONLINE_SESSIONS_MAP", new String[]{userid + "$$" + (String)entry.getKey()});
        }

    }

    private void set(final String key, final String value) {
        this.execute(new JedisActionNoResult() {
            public void action(Jedis jedis) {
                jedis.set(key, value);
            }
        });
    }

    private String get(final String key) {
        return (String)this.execute(new JedisAction() {
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    private Boolean exists(final String key) {
        return (Boolean)this.execute(new JedisAction() {
            public Boolean action(Jedis jedis) {
                return jedis.exists(key);
            }
        });
    }

    private void execute(JedisActionNoResult jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        try {
            jedis = (Jedis)this.getSessionJedisPool().getResource();
            jedisAction.action(jedis);
        } catch (JedisException var8) {
            broken = this.handleJedisException(var8);
            throw var8;
        } finally {
            this.closeResource(jedis, broken);
        }

    }

    private <T> T execute(JedisAction<T> jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        T e;
        try {
            jedis = (Jedis)this.getSessionJedisPool().getResource();
            e = jedisAction.action(jedis);
        } catch (JedisException var8) {
            broken = this.handleJedisException(var8);
            throw var8;
        } finally {
            this.closeResource(jedis, broken);
        }

        return e;
    }

    private Boolean del(final String... keys) {
        return (Boolean)this.execute(new JedisAction() {
            public Boolean action(Jedis jedis) {
                return Boolean.valueOf(jedis.del(keys).longValue() == (long)keys.length);
            }
        });
    }

    private <T extends Serializable> void hset(final String key, final String fieldName, final T value) {
        this.execute(new JedisActionNoResult() {
            public void action(Jedis jedis) {
                byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
                byte[] fieldBytes = fieldName.getBytes(Charset.forName("UTF-8"));
                byte[] valueBytes = SerializUtil.objectToByte(value);
                jedis.hset(keyBytes, fieldBytes, valueBytes);
            }
        });
    }

    private void sessionHset(final String key, final String fieldName, final String value) {
        this.execute(new JedisActionNoResult() {
            public void action(Jedis jedis) {
                jedis.hset(key, fieldName, value);
            }
        });
    }

    private String sessionHget(final String key, final String fieldName) {
        return (String)this.execute(new JedisAction() {
            public String action(Jedis jedis) {
                return jedis.hget(key, fieldName);
            }
        });
    }

    private <T extends Serializable> T hget(final String key, final String fieldName) {
        return (T)this.execute(new JedisAction() {
            public T action(Jedis jedis) {
                byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
                byte[] fieldBytes = fieldName.getBytes(Charset.forName("UTF-8"));
                byte[] attrBytes = jedis.hget(keyBytes, fieldBytes);
                return attrBytes == null?null:(T)SerializUtil.byteToObject(attrBytes);
            }
        });
    }

    private Long hlength(final String key) {
        return (Long)this.execute(new JedisAction() {
            public Long action(Jedis jedis) {
                return jedis.hlen(key);
            }
        });
    }

    private Long hdel(final String key, final String... fieldsNames) {
        return (Long)this.execute(new JedisAction() {
            public Long action(Jedis jedis) {
                return jedis.hdel(key, fieldsNames);
            }
        });
    }

    private Boolean hexists(final String key, final String field) {
        return (Boolean)this.execute(new JedisAction() {
            public Boolean action(Jedis jedis) {
                return jedis.hexists(key, field);
            }
        });
    }

    private Map<byte[], byte[]> hgetAll(final String key) {
        return (Map)this.execute(new JedisAction() {
            public Map<byte[], byte[]> action(Jedis jedis) {
                return jedis.hgetAll(key.getBytes(Charset.forName("UTF-8")));
            }
        });
    }

    private Map<String, String> getMap(final String key) {
        return (Map)this.execute(new JedisAction() {
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    private void expire(final String key, final int timeout) {
        this.execute(new JedisActionNoResult() {
            public void action(Jedis jedis) {
                jedis.expire(key, timeout);
            }
        });
    }

    private boolean handleJedisException(JedisException jedisException) {
        if(jedisException instanceof JedisConnectionException) {
            this.logger.error("Redis connection " + this.getSessionJedisPool().getAddress() + " lost.", jedisException);
        } else if(jedisException instanceof JedisDataException) {
            if(jedisException.getMessage() == null || jedisException.getMessage().indexOf("READONLY") == -1) {
                return false;
            }

            this.logger.error("Redis connection " + this.getSessionJedisPool().getAddress() + " are read-only slave.", jedisException);
        } else {
            this.logger.error("Jedis exception happen.", jedisException);
        }

        return true;
    }

    private void closeResource(Jedis jedis, boolean conectionBroken) {
        try {
            if(conectionBroken) {
                this.getSessionJedisPool().returnBrokenResource(jedis);
            } else {
                this.getSessionJedisPool().returnResource(jedis);
            }
        } catch (Exception var4) {
            this.logger.error("return back jedis failed, will fore close the jedis.", var4);
            JedisUtils.destroyJedis(jedis);
        }

    }

    private void execute(PipelineActionNoResult pipelineAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        try {
            jedis = (Jedis)this.getSessionJedisPool().getResource();
            Pipeline e = jedis.pipelined();
            pipelineAction.action(e);
            e.sync();
        } catch (JedisException var8) {
            broken = this.handleJedisException(var8);
            throw var8;
        } finally {
            this.closeResource(jedis, broken);
        }

    }

    private List<Object> execute(PipelineAction pipelineAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        List var5;
        try {
            jedis = (Jedis)this.getSessionJedisPool().getResource();
            Pipeline e = jedis.pipelined();
            pipelineAction.action(e);
            var5 = e.syncAndReturnAll();
        } catch (JedisException var9) {
            broken = this.handleJedisException(var9);
            throw var9;
        } finally {
            this.closeResource(jedis, broken);
        }

        return var5;
    }
}
