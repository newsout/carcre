package com.sout.carcre;

import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.CardInfoMapper;
import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.RankWeeklyMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CarcreApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    MainService mainService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RankService rankService;
    @Autowired
    RankWeeklyMapper rankWeeklyMapper;
    @Autowired
    MessageListMapper messageListMapper;
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    MessageService messageService;
    @Autowired
    RedisConfig redisConfig;
    @Autowired
    CardInfoMapper cardInfoMapper;
    @Autowired
    CardService cardService;
    @Autowired
    DailyTaskService dailyTaskService;

    @Test
    void contextLoads() throws IOException {
        dailyTaskService.increaseShareNum(1);
    }

}
