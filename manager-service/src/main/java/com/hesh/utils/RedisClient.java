//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

public class RedisClient extends CacheManager {
    private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

    public RedisClient() {
    }


    public String get(final String key) {
        if(StringUtils.isEmpty(key)) {
            return null;
        } else {

            String result = (String)getJedisTemplate().execute(new RedisCallback<String>() {
                final String keyid=key;
                public String doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] key = serializer.serialize(keyid);
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    String name = serializer.deserialize(value);
                    return name;
                }
            });
            return result;
        }

    }


    public boolean add(final String key,final String value) {
        boolean result = (boolean) getJedisTemplate().execute(new RedisCallback<Boolean>() {
            final String keyid=key;
            final String values=value;
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getJedisTemplate().getStringSerializer();
                byte[] key = serializer.serialize(keyid);
                byte[] name = serializer.serialize(values);
                return connection.setNX(key, name);
            }
        });
        return result;

    }


}
