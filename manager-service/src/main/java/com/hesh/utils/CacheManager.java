//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesh.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class CacheManager {
    private RedisTemplate jedisTemplate;
    private int sessionTimeout = 3600;
    private static final ObjectMapper mapper = new ObjectMapper();

    public CacheManager() {
    }

    public RedisTemplate getJedisTemplate() {
        return this.jedisTemplate;
    }

    public void setJedisTemplate(RedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    public int getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    protected RedisSerializer<String> getRedisSerializer() {
        return jedisTemplate.getStringSerializer();
    }
}
