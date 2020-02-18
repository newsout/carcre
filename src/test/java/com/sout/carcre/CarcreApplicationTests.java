package com.sout.carcre;

import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.RankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

@SpringBootTest
class CarcreApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    MainService mainService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RankService rankService;
    @Test
    void contextLoads() throws IOException {
        System.out.println(rankService.getRankData(userInfoMapper.selectUserInfoByUserId(1)));
    }

}
