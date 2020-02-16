package com.sout.carcre.integration.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 实际操作redis CRUD类
 */
@Service
public class RedisUser {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /*向redis不同库中存值*/
    public String cache(){
        redisTemplate.opsForValue().set("manager","ndd");
        redisTemplate.opsForList().leftPush("k1",1);
        redisTemplate.opsForList().leftPush("k1",2);

        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory)redisTemplate.getConnectionFactory();
        //切换数据库
        lettuceConnectionFactory.setDatabase(2);
        //如果不加以下一行代码，connectprovider中的connect配置一直都会是原来的database为0（默认数据库状态）
        lettuceConnectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        //如果不加以下一行代码,connect不会为null，就不会执行connectprovider中的配置代码
        lettuceConnectionFactory.resetConnection();

        redisTemplate.opsForList().leftPush("k2",1);
        redisTemplate.opsForSet().add("set01",7,5,5,5);
        redisTemplate.opsForHash().put("hash01","子元素","value");
        redisTemplate.opsForHash().put("hash01","k2","value02");
        redisTemplate.opsForValue().set("1","ndds");
        redisTemplate.opsForValue().set("2","ndd");
        redisTemplate.opsForValue().set("3","ndd");
        String data=stringRedisTemplate.opsForValue().get("1");
        return data;
    }

    /*删库*/
    public void cachedelete(){
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    /*获取cache值*/
    public String cache(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

}
