package com.hesh.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gaods on 2017/8/2.
 */

@Service
public class UserService {

    @Resource
    RedisTemplate  redisTemplate;



    public boolean add() {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("1");
                byte[] name = serializer.serialize("2");
                return connection.setNX(key, name);
            }
        });
        return result;
    }

}
