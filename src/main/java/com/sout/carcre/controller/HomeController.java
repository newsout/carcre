package com.sout.carcre.controller;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.controller.bean.HomePage;
import com.sout.carcre.controller.bean.MessageData;
import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.redis.RedisMethod;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 首页页面
 */
@Controller
public class HomeController {

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

    /*首页请求数据*/
    @RequestMapping("/homepage")
    @ResponseBody
    public Result<HomePage> homepage(@RequestParam("user_id") String userId) {
        JSONObject returnJson = new JSONObject();
        //通过userid查询是否存在用户
        int flag = userInfoMapper.userIsSave(1);
        if (flag == 1) {
            //通过八维通获取数据
            UserInfo userInfo = mainService.getUserInfoByBWT(Integer.parseInt(userId));
            userInfoMapper.insertUserInfo(userInfo);
            //获取好友列表
        }
        //获取用户数据
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(Integer.parseInt(userId));
        returnJson.put("userData", (JSONObject) JSONObject.toJSON(userInfo));
        //查询每日任务情况 先切换到1号库
        redisMethod.selectDB(1);
        String userid = String.valueOf(userInfo.getUserId());
        Map<String, Integer> map = new HashMap<>();
        if (!redisTemplate.hasKey(userid)) {  //创建键值对
            map.put("isSign", 0);
            map.put("signNum", 0);
            map.put("shareNum", 0);
            map.put("isTravel", 0);
            redisTemplate.opsForHash().putAll(userid, map);
        }
        if (Objects.equals(redisTemplate.opsForHash().get(userid, "isSign"), 0))
            redisTemplate.opsForHash().increment(userid, "signNum", 1);
        redisTemplate.opsForHash().put(userid, "isSign", 1);
        returnJson.put("signData", redisTemplate.opsForHash().entries(userid));

        //获取好友排行榜数据




        /*jsonobject转javabean*/
        HomePage homePage = JSONObject.parseObject(String.valueOf(returnJson), HomePage.class);
        return RetResponse.makeOKRsp(homePage);
    }

    /*请求排行榜所有数据*/
    @RequestMapping("/rankdata")
    @ResponseBody
    public List<RankData> rankdata() {
        List<RankData> list = new ArrayList<>();

        return list;
    }

    /*请求每日任务数据*/
    @RequestMapping("/dailytask")
    @ResponseBody
    public DailyTask dailytask() {
        DailyTask dailyTask = new DailyTask();

        return dailyTask;
    }


    /*用户查看消息列表*/
    @RequestMapping("/message")
    @ResponseBody
    public MessageData message() {
        MessageData messageData = new MessageData();

        return messageData;
    }
}
