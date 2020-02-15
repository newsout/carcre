package com.sout.carcre;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sout.carcre.controller.LoginController;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.serivce.MainService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
class CarcreApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    MainService mainService;

    @Autowired
    LoginController loginController;

    @Test
    void contextLoads() throws IOException {
//        UserInfo userInfo=mainService.getUserInfoByBWT(1);
//        System.out.println(userInfo.toString());
//        userInfoMapper.insertUserInfo(userInfo);
        System.out.println(userInfoMapper.userIsSave(1));
    }

}
