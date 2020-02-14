package com.sout.carcre.integration.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 实际操作redis CRUD类
 */
public class RedisUser {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public void redisDealData(){
    }
}
