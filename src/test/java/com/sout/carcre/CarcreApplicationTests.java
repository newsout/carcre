package com.sout.carcre;

import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.serivce.MainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
class CarcreApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    MainService mainService;

    @Test
    void contextLoads() {
        //userInfoMapper.insertUserInfo(new UserInfo(2));
        //System.out.println(new Timestamp(new Date().getTime()).toString());
        mainService.getUserInfoByBWT(1);
    }

}
