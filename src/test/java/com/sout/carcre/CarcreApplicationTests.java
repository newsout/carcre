package com.sout.carcre;

import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
class CarcreApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Test
    void contextLoads() {
        userInfoMapper.insertUserInfo(new UserInfo(2));
        System.out.println(new Timestamp(new Date().getTime()).toString());
    }

}
