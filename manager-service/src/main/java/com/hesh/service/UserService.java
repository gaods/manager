package com.hesh.service;

import com.hesh.utils.RedisClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gaods on 2017/8/2.
 */

@Service
public class UserService {

    @Resource
    RedisClient redisClient;


    public boolean add() {
       return redisClient.add("2","3");
    }

}
