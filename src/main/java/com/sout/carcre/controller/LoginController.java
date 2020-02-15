package com.sout.carcre.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.serivce.MainService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    /*
    登录功能：
    先判断数据库中是否存在用户，如果不存在先从八维通获取用户数据保存至我们的数据库。
     */
    @PostMapping("api/login")
    public JSONObject login(HttpServletRequest httpServletRequest){
        JSONObject returnJson=new JSONObject();
        //通过userid查询是否存在用户
        int flag=userInfoMapper.userIsSave(1);
        //如果不存在
        if (flag==1){
            //通过八维通获取数据
            UserInfo userInfo=mainService.getUserInfoByBWT(new Integer(httpServletRequest.getParameter("user_id")));
            userInfoMapper.insertUserInfo(userInfo);
        }
        //获取用户数据
        UserInfo userInfo=userInfoMapper.selectUserInfoByUserId(new Integer(httpServletRequest.getParameter("user_id")));
        JSONObject userInfoJson = (JSONObject) JSONObject.toJSON(userInfo);
        returnJson.put("user_info",userInfoJson);
        return returnJson;
    }
}
