package com.sout.carcre.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.controller.bean.HomePage;
import com.sout.carcre.controller.bean.MessageData;
import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.RankWeeklyMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.MessageList;
import com.sout.carcre.mapper.bean.RankWeekly;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.RankService;
import com.sout.carcre.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import com.sout.carcre.controller.bean.beanson.UserData;
import com.sout.carcre.mapstruct.Do2Vo.UserInfor2Data;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 首页页面
 */
@Controller
@CrossOrigin //允许跨域请求注解
public class HomeController {
    @Autowired
    MainService mainService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    RankService rankService;
    @Autowired
    RankWeeklyMapper rankWeeklyMapper;
    @Autowired
    MessageListMapper messageListMapper;
    @Autowired
    TestService testService;
    @Autowired
    RedisConfig redisConfig;

    /*首页请求数据*/
    @RequestMapping("/homepage")
    @ResponseBody
    public Result<HomePage> homepage(@RequestParam("user_id") String userId,  HttpServletRequest request,HttpServletResponse response, HttpSession session) {
        sessionHandler.setSession(request, response, "userId", userId);
        JSONObject returnJson = new JSONObject();
        //获取用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(Integer.parseInt(userId));
        if (userInfo == null) {
            //通过八维通获取数据
            userInfo = mainService.getUserInfoByBWT(Integer.parseInt(userId));
            userInfoMapper.insertUserInfo(userInfo);
            rankWeeklyMapper.insertRankWeeklyData(userInfo);
        }
        //查询每日任务情况 先切换到1号库
        RedisTemplate<String, Object> template1=redisConfig.getRedisTemplateByDb(1);
        Map<String, String> map = new HashMap<>();
        if (!template1.hasKey(userId)) {  //创建键值对
            map.put("isSign", "0");
            map.put("signNum", "0");
            map.put("shareNum", "0");
            map.put("isTravel", "0");
            template1.opsForHash().putAll(userId, map);
        }
        if (Objects.equals(template1.opsForHash().get(userId, "isSign"), 0))
            template1.opsForHash().increment(userId, "signNum", 1);
        //数据转为json
        returnJson.put("userData", JSONObject.toJSON(userInfo));
        returnJson.put("signData", template1.opsForHash().entries(userId));
        //获取到全部信息后再将是否签到设置为1
        template1.opsForHash().put(userId,"isSign","1");
        /*jsonobject转javabean*/
        HomePage homePage = JSONObject.parseObject(String.valueOf(returnJson), HomePage.class);
        return RetResponse.makeOKRsp(homePage);
    }

    /*显示主页事件更新部分数据接口*/
    @RequestMapping("/update")
    @ResponseBody
    public Result<JSONObject> update(HttpServletRequest request,HttpServletResponse response){
        JSONObject reJson=new JSONObject();
        int userId = Integer.parseInt(sessionHandler.getSession(request, response, "userId"));
        UserInfo userInfo=userInfoMapper.selectUserInfoByUserId(userId);
        reJson.put("userGrade",userInfo.getUserGrade());
        reJson.put("isMessage",userInfo.getIsMessage());
        reJson.put("isNewcard",userInfo.getIsNewcard());
        return RetResponse.makeOKRsp(reJson);
    }

    /*请求总排行榜数据*/
    @RequestMapping("/rankdata")
    @ResponseBody
    public Result<List<RankData>> rankdata(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = Integer.parseInt(sessionHandler.getSession(request, response, "userId"));
        List<RankData> list = new ArrayList<>(rankService.getRankData(userInfoMapper.selectUserInfoByUserId(userId)));
        return RetResponse.makeOKRsp(list);
    }

    /*请求每周排行榜数据*/
    @RequestMapping("/rankweeklydata")
    @ResponseBody
    public Result<List<RankWeekly>> rankweeklydata(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(sessionHandler.getSession(request, response, "userId"));
        return RetResponse.makeOKRsp(rankService.getRankWeekly(userInfoMapper.selectUserInfoByUserId(userId)));
    }

    /*请求每日任务数据*/
    @RequestMapping("/dailytask")
    @ResponseBody
    public Result<DailyTask> dailytask() {
        DailyTask dailyTask = new DailyTask();
        return RetResponse.makeOKRsp(dailyTask);
    }

    /*用户查看消息列表*/
    @RequestMapping("/simple_message")
    @ResponseBody
    public Result<List<MessageList>> simpleMessage(HttpServletResponse response, HttpServletRequest request) {
        int userId=Integer.parseInt(sessionHandler.getSession(request, response, "userId"));
        return RetResponse.makeOKRsp(messageListMapper.selectSimpleMessageByUserId(userId));
    }

    @RequestMapping("/message")
    @ResponseBody
    public Result<MessageList> message(@RequestParam("message_id") String messageId, HttpServletResponse response, HttpServletRequest request) {
        return RetResponse.makeOKRsp(messageListMapper.selectMessageById(Integer.parseInt(messageId)));
    }



}
