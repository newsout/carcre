package com.sout.carcre.controller;

import com.sout.carcre.integration.redis.RedisMethod;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lzw on 2020/2/14.
 */
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    MainService mainService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisMethod redisMethod;

    /*
    登录功能：
    先判断数据库中是否存在用户，如果不存在先从八维通获取用户数据保存至我们的数据库。
    再查询用户每日任务情况，若redis库中没有，则新建。
    再查询好友数据
     */

}
