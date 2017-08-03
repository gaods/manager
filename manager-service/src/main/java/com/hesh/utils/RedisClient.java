package com.hesh.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.hesh.utils.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.io.Serializable;
import java.nio.charset.Charset;

public class RedisClient extends CacheManager {
    private static final Logger logger = LoggerFactory
            .getLogger(RedisClient.class);

    public boolean existKey(final String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return ((Boolean) getJedisTemplate().execute(
                new JedisTemplate.JedisAction() {
                    public Boolean action(Jedis jedis) throws JedisException {
                        return jedis.exists(key);
                    }
                })).booleanValue();
    }

    public String get(final String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String result = (String) getJedisTemplate().execute(
                new JedisTemplate.JedisAction() {
                    public String action(Jedis jedis) throws JedisException {
                        return jedis.get(key);
                    }
                });
        return result;
    }

    public String set(final String key, final String value) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String result = (String) getJedisTemplate().execute(
                new JedisTemplate.JedisAction() {
                    public String action(Jedis jedis) throws JedisException {
                        return jedis.set(key, value);
                    }
                });
        return result;
    }

    public String rename(final String oldkey, final String newkey) {
        if ((StringUtils.isEmpty(oldkey)) || (StringUtils.isEmpty(newkey))) {
            return null;
        }
        String result = (String) getJedisTemplate().execute(
                new JedisTemplate.JedisAction() {
                    public String action(Jedis jedis) throws JedisException {
                        return jedis.rename(oldkey, newkey);
                    }
                });
        return result;
    }

    public boolean putExpireTime(final String key, final String value,
                                 final int seconds) {
        if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(value))
                || (seconds <= 0)) {
            return false;
        }
        return ((Boolean) getJedisTemplate().execute(new JedisTemplate.JedisAction() {
            public Boolean action(Jedis jedis) throws JedisException {
                String result = jedis.setex(key, seconds, value);
                return Boolean.valueOf(result.equalsIgnoreCase("ok"));
            }
        })).booleanValue();
    }

    public boolean delete(final String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return ((Boolean) getJedisTemplate().execute(
                new JedisTemplate.JedisAction() {
                    public Boolean action(Jedis jedis) throws JedisException {
                        if (jedis.del(key).longValue() > 0L) {
                            return Boolean.valueOf(true);
                        }
                        return Boolean.valueOf(false);
                    }
                })).booleanValue();
    }

    public boolean deleteKeys(String... keys) {
        if (StringUtils.isEmpty(keys)) {
            return false;
        }
        return getJedisTemplate().del(keys).booleanValue();
    }

    public String getValueFromMap(final String key, final String field) {
        if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field))) {
            return null;
        }
        String result = (String) getJedisTemplate().execute(
                new JedisTemplate.JedisAction() {
                    public String action(Jedis jedis) throws JedisException {
                        return jedis.hget(key, field);
                    }
                });
        return result;
    }

    public boolean storeToMap(final String key, final String field,
                              final String value) {
        if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field))
                || (StringUtils.isEmpty(value))) {
            return false;
        }
        return ((Boolean) getJedisTemplate().execute(new JedisTemplate.JedisAction() {
            public Boolean action(Jedis jedis) throws JedisException {
                long result = jedis.hset(key, field, value).longValue();
                boolean isSuccess = (result == 0L) || (result == 1L);
                if (!isSuccess) {
                    jedis.del(key);
                }
                return Boolean.valueOf(isSuccess);
            }
        })).booleanValue();
    }

    public boolean storeToMapIfNotExists(final String key, final String field,
                                         final String value) {
        if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field))
                || (StringUtils.isEmpty(value))) {
            return false;
        }
        return ((Boolean) getJedisTemplate().execute(new JedisTemplate.JedisAction() {
            public Boolean action(Jedis jedis) throws JedisException {
                if (jedis.hsetnx(key, field, value).longValue() > 0L) {
                    return Boolean.valueOf(true);
                }
                return Boolean.valueOf(false);
            }
        })).booleanValue();
    }

    public boolean removeFromMap(final String key, final String field) {
        if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(field))) {
            return false;
        }
        return ((Boolean) getJedisTemplate().execute(new JedisTemplate.JedisAction() {
            public Boolean action(Jedis jedis) throws JedisException {
                if (jedis.hdel(key, new String[] { field }).longValue() == 1L) {
                    return Boolean.valueOf(true);
                }
                return Boolean.valueOf(false);
            }
        })).booleanValue();
    }

    public boolean storeValueIfNotExists(final String key, final String value,
                                         final int seconds) {
        if ((StringUtils.isEmpty(key)) || (StringUtils.isEmpty(value))) {
            return false;
        }
        return ((Boolean) getJedisTemplate().execute(new JedisTemplate.JedisAction() {
            public Boolean action(Jedis jedis) throws JedisException {
                boolean success = jedis.setnx(key, value).longValue() > 0L;
                if (success) {
                    jedis.expire(key, seconds);
                }
                return Boolean.valueOf(success);
            }
        })).booleanValue();
    }

    /* Error */
//    public <T extends Serializable> void putCacheForClass(String sid,
//                                                          String key, T value) {
//
//    }

//    public <T extends Serializable> void putSessionCacheAttribute(String key,
//                                                                  T value) {
//        putAttribute(key, value, getSessionTimeout());
//    }
//
//
//    public <T extends Serializable> void putAttribute(String key, T value,
//                                                      int expiredTs) {
//
//    }
//
//    /* Error */
//    public <T extends Serializable> void updateCacheAttribute(String sid,
//                                                              String key, T value) {
//
//    }
//
//    /* Error */
//    public <T extends Serializable> void updateSessionCacheAttribute(
//            String key, T value) {
//
//    }

    public <T extends Serializable> T getAttribute(String sid, String key,
                                                   Class<T> t) {
        Jedis jedis = (Jedis) getJedisTemplate().getJedisPool().getResource();
        try {
            byte[] attrBytes = jedis.hget(
                    sid.getBytes(Charset.forName("UTF-8")),
                    key.getBytes(Charset.forName("UTF-8")));
            if (attrBytes == null) {
                return null;
            }
            return toType(t, attrBytes);
        } finally {
            getJedisTemplate().getJedisPool().returnResource(jedis);
        }
    }

    public <T extends Serializable> T getAttribute(String key, Class<T> t) {
        return getAttribute(key, 0, t);
    }

    public <T extends Serializable> T getAttribute(String key, int ttl,
                                                   Class<T> t) {
        Jedis jedis = (Jedis) getJedisTemplate().getJedisPool().getResource();
        try {
            byte[] attrBytes = jedis
                    .get(key.getBytes(Charset.forName("UTF-8")));
            if (attrBytes == null) {
                return null;
            }
            if (ttl > 0) {
                jedis.expire(key, ttl);
            }
            return toType(t, attrBytes);
        } finally {
            getJedisTemplate().getJedisPool().returnResource(jedis);
        }
    }

    public <T extends Serializable> T getSessionCacheAttribute(String sid,
                                                               String key, Class<T> t) {
        Jedis jedis = (Jedis) getJedisTemplate().getJedisPool().getResource();
        try {
            boolean isExist = jedis.exists(sid).booleanValue();
            Object result = null;
            if (isExist) {
                jedis.expire(sid, getSessionTimeout());
                byte[] attrBytes = jedis.hget(
                        sid.getBytes(Charset.forName("UTF-8")),
                        key.getBytes(Charset.forName("UTF-8")));
                if (attrBytes == null) {
                    getJedisTemplate().getJedisPool().returnResource(jedis);
                    return null;
                }
                result = toType(t, attrBytes);
            }
            return (T) result;
        } finally {
            getJedisTemplate().getJedisPool().returnResource(jedis);
        }
    }

    public <T extends Serializable> T getSessionCacheAttribute(String key,
                                                               Class<T> t) {
        Jedis jedis = (Jedis) getJedisTemplate().getJedisPool().getResource();
        try {
            boolean isExist = jedis.exists(key).booleanValue();
            Object result = null;
            if (isExist) {
                jedis.expire(key, getSessionTimeout());
                byte[] attrBytes = jedis.get(key.getBytes(Charset
                        .forName("UTF-8")));
                if (attrBytes == null) {
                    getJedisTemplate().getJedisPool().returnResource(jedis);
                    return null;
                }
                result = toType(t, attrBytes);
            }
            return (T) result;
        } finally {
            getJedisTemplate().getJedisPool().returnResource(jedis);
        }
    }

    private <T extends Serializable> T toType(Class<T> t, byte[] data) {
        T obj = null;
        if (t.isAssignableFrom(String.class)) {
            obj = (T) new String(data);
        } else {
            try {
                JavaType type = getMapper().getTypeFactory().constructType(t);
                obj = (T) getMapper().readValue(
                        new String(data, "UTF-8"), type);
            } catch (Exception e) {
                logger.error("Format failed", e);
            }
        }
        return obj;
    }

    private <T extends Serializable> byte[] toBytes(T value) {
        byte[] data = null;
        if ((value instanceof String)) {
            data = ((String) value).getBytes();
        } else {
            try {
                data = getMapper().writeValueAsString(value).getBytes("UTF-8");
            } catch (Exception e) {
                logger.error("JsonProcessingException", e);
            }
        }
        return data;
    }

    public String generateKey(String key) {
        return String.valueOf(getJedisTemplate().incr(key));
    }
}
