//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hesh.common.utils.YCCookieUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionCacheManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionCacheManager.class);
    private int sessionTimeout = 3600;
    private JedisTemplate jedisTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    public SessionCacheManager() {
    }

    public <T extends Serializable> Map<String, T> getAllSessionAttrCache(String sid, Class<T> t) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        try {
            HashMap hashMap = new HashMap();
            Map redisMap = jedis.hgetAll(sid.getBytes(Charset.forName("UTF-8")));
            Iterator iterator = redisMap.keySet().iterator();

            while(iterator.hasNext()) {
                byte[] byteKey = (byte[])iterator.next();
                String key = new String(byteKey, Charset.forName("UTF-8"));
                Serializable obj = this.toType(t, (byte[])redisMap.get(byteKey));
                if(obj == null) {
                    hashMap.put(key, obj);
                }
            }

            jedis.expire(sid, this.sessionTimeout);
            HashMap var11 = hashMap;
            return var11;
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }
    }

    public void removeSessionCache(String sid) {
        this.jedisTemplate.del(new String[]{sid});
    }

    public <T extends Serializable> void putSessionCacheAttribute(String sid, String key, T value) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        try {
            byte[] valueBytes = this.toBytes(value);
            jedis.hset(sid.getBytes(Charset.forName("UTF-8")), key.getBytes(Charset.forName("UTF-8")), valueBytes);
            jedis.expire(sid, this.sessionTimeout);
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }

    }

    public <T extends Serializable> void putSessionCacheAttribute(String key, T value) {
        this.putAttribute(key, value, this.sessionTimeout);
    }

    public <T extends Serializable> void putAttribute(String key, T value, int expiredTs) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        try {
            byte[] valueBytes = this.toBytes(value);
            byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
            jedis.set(keyBytes, valueBytes);
            if(expiredTs > 0) {
                jedis.expire(keyBytes, expiredTs);
            }
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }

    }

    public <T extends Serializable> void updateSessionCacheAttribute(String sid, String key, T value) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        try {
            if(jedis.hexists(sid.getBytes(Charset.forName("UTF-8")), key.getBytes(Charset.forName("UTF-8"))).booleanValue()) {
                this.putSessionCacheAttribute(sid, key, value);
            }
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }

    }

    public <T extends Serializable> void updateSessionCacheAttribute(String key, T value) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        try {
            if(jedis.exists(key).booleanValue()) {
                this.putSessionCacheAttribute(key, value);
            }
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }

    }

    public <T extends Serializable> T getAttribute(String sid, String key, Class<T> t) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        Serializable var7;
        try {
            byte[] attrBytes = jedis.hget(sid.getBytes(Charset.forName("UTF-8")), key.getBytes(Charset.forName("UTF-8")));
            if(attrBytes == null) {
                return null;
            }

            var7 = this.toType(t, attrBytes);
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }

        return (T)var7;
    }

    public <T extends Serializable> T getAttribute(String key, Class<T> t) {
        return this.getAttribute(key, 0, t);
    }

    public <T extends Serializable> T getAttribute(String key, int ttl, Class<T> t) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        Serializable var7;
        try {
            byte[] attrBytes = jedis.get(key.getBytes(Charset.forName("UTF-8")));
            if(attrBytes == null) {
                return null;
            }

            if(ttl > 0) {
                jedis.expire(key, ttl);
            }

            var7 = this.toType(t, attrBytes);
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }

        return (T)var7;
    }

    public <T extends Serializable> T getSessionCacheAttribute(String sid, String key, Class<T> t) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        Serializable var9;
        try {
            boolean isExist = jedis.exists(sid).booleanValue();
            Serializable result = null;
            if(isExist) {
                jedis.expire(sid, this.sessionTimeout);
                byte[] attrBytes = jedis.hget(sid.getBytes(Charset.forName("UTF-8")), key.getBytes(Charset.forName("UTF-8")));
                if(attrBytes == null) {
                    this.jedisTemplate.getJedisPool().returnResource(jedis);
                    return null;
                }

                result = this.toType(t, attrBytes);
            }

            var9 = (Serializable)result;
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }

        return (T)var9;
    }

    public <T extends Serializable> T getSessionCacheAttribute(String key, Class<T> t) {
        Jedis jedis = (Jedis)this.jedisTemplate.getJedisPool().getResource();

        try {
            boolean isExist = jedis.exists(key).booleanValue();
            Serializable result = null;
            if(isExist) {
                jedis.expire(key, this.sessionTimeout);
                byte[] attrBytes = jedis.get(key.getBytes(Charset.forName("UTF-8")));
                if(attrBytes == null) {
                    this.jedisTemplate.getJedisPool().returnResource(jedis);
                    return null;
                }

                result = this.toType(t, attrBytes);
            }

            Serializable var8 = (Serializable)result;
            return (T)var8;
        } finally {
            this.jedisTemplate.getJedisPool().returnResource(jedis);
        }
    }

    public long removeSessionCacheAttribute(String sid, String key) {
        return StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(key)?this.jedisTemplate.hdel(sid, new String[]{key}).longValue():0L;
    }

    public boolean removeSessionCacheAttribute(String key) {
        return StringUtils.isNotBlank(key)?this.jedisTemplate.del(new String[]{key}).booleanValue():false;
    }

    public <T extends Serializable> T getCurUser(String uname, Class<T> clz) {
        T result = this.getSessionCacheAttribute(this.createUserCacheKey(uname), clz);
        return result;
    }

    public <T extends Serializable> T getCurUser(HttpServletRequest request, Class<T> clz) {
        String uname = YCCookieUtil.get(request.getCookies(), "username");
        return StringUtils.isNotBlank(uname)?this.getCurUser(uname, clz):null;
    }

    public <T extends Serializable> void cacheUser(String uname, T user) {
        try {
            this.putSessionCacheAttribute(this.createUserCacheKey(uname), mapper.writeValueAsString(user));
        } catch (JsonProcessingException var4) {
            throw new IllegalArgumentException(var4);
        }
    }

    public <T extends Serializable> void refreshUser(String uname, T user) {
        try {
            this.updateSessionCacheAttribute(this.createUserCacheKey(uname), mapper.writeValueAsString(user));
        } catch (JsonProcessingException var4) {
            throw new IllegalArgumentException(var4);
        }
    }

    public <T extends Serializable> void disCacheUser(String uname) {
        this.removeSessionCacheAttribute(this.createUserCacheKey(uname));
    }

    private <T extends Serializable> T toType(Class<T> t, byte[] data) {
        Object obj = null;
        if(t.isAssignableFrom(String.class)) {
            obj = new String(data);
        } else {
            try {
                JavaType e = mapper.getTypeFactory().constructType(t);
                obj = (Serializable)mapper.readValue(new String(data, "UTF-8"), e);
            } catch (Exception var5) {
                logger.error("Format failed", var5);
            }
        }

        return (T)obj;
    }

    private <T extends Serializable> byte[] toBytes(T value) {
        byte[] data = null;
        if(value instanceof String) {
            data = ((String)value).getBytes();
        } else {
            try {
                data = mapper.writeValueAsString(value).getBytes("UTF-8");
            } catch (Exception var4) {
                logger.error("JsonProcessingException", var4);
            }
        }

        return data;
    }

    public String generateKey(String key) {
        return String.valueOf(this.jedisTemplate.incr(key));
    }

    private String createUserCacheKey(String uname) {
        return "user.info.login.ipu" + ":" + uname;
    }

    public JedisTemplate getJedisTemplate() {
        return this.jedisTemplate;
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    public int getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
