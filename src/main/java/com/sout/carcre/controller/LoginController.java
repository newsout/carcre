package com.sout.carcre.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sout.carcre.integration.redis.RedisMethod;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.serivce.MainService;
import org.apache.catalina.User;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    @PostMapping("api/login")
    public JSONObject login(HttpServletRequest httpServletRequest) {
        JSONObject returnJson = new JSONObject();
        //通过userid查询是否存在用户
        int flag = userInfoMapper.userIsSave(1);
        if (flag == 1) {
            //通过八维通获取数据
            UserInfo userInfo = mainService.getUserInfoByBWT(new Integer(httpServletRequest.getParameter("user_id")));
            userInfoMapper.insertUserInfo(userInfo);
            //获取好友列表
        }
        //获取用户数据
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(new Integer(httpServletRequest.getParameter("user_id")));
        JSONObject userInfoJson = (JSONObject) JSONObject.toJSON(userInfo);
        returnJson.put("user_info", userInfoJson);


        //查询每日任务情况 先切换到1号库
        redisMethod.selectDB(1);
        String userId = String.valueOf(userInfo.getUserId());
        Map<String, Integer> map = new HashMap<>();
        if (!redisTemplate.hasKey(userId)) {  //创建键值对
            map.put("isSign", 0);
            map.put("signNum", 0);
            map.put("shareNum", 0);
            map.put("isTravel",0);
            redisTemplate.opsForHash().putAll(userId, map);
        }
        if (Objects.equals(redisTemplate.opsForHash().get(userId, "isSign"), 0))
            redisTemplate.opsForHash().increment(userId, "signNum", 1);
        redisTemplate.opsForHash().put(userId, "isSign", 1);
        returnJson.put("signInfo", redisTemplate.opsForHash().entries(userId));


        //获取好友排行榜数据

        return returnJson;
    }
}
