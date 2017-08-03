//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


package com.hesh.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springside.modules.nosql.redis.JedisTemplate;

public class CacheManager {
    private JedisTemplate jedisTemplate;
    private int sessionTimeout = 3600;
    private static final ObjectMapper mapper = new ObjectMapper();

    public CacheManager() {
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

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
