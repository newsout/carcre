package com.sout.carcre.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.controller.bean.HomePage;
import com.sout.carcre.controller.bean.MessageData;
import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.integration.redis.RedisLock;
import com.sout.carcre.integration.redis.RedisManager;
import com.sout.carcre.integration.redis.RedisMethod;
import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.RankWeeklyMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.RankWeekly;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.RankService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.sout.carcre.controller.bean.beanson.UserData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.mapstruct.Do2Vo.UserInfor2Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.zip.DeflaterOutputStream;

/**
 * 首页页面
 */
@Controller
@CrossOrigin //允许跨域请求注解
public class HomeController{

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
    @Autowired
    RedisManager redisManager;
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    RankService rankService;
    @Autowired
    RankWeeklyMapper rankWeeklyMapper;
    @Autowired
    MessageListMapper messageListMapper;

    /*首页请求数据*/
    @RequestMapping("/homepage")
    @ResponseBody
    public Result<HomePage> homepage(@RequestParam("user_id") String userId,  HttpServletRequest request,HttpServletResponse response, HttpSession session) {
        RedisLock redisLock = new RedisLock(redisTemplate, "userId", false, userId);
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
        redisMethod.selectDB(1);
        Map<String, Integer> map = new HashMap<>();
        if (!redisTemplate.hasKey(userId)) {  //创建键值对
            map.put("isSign", 0);
            map.put("signNum", 0);
            map.put("shareNum", 0);
            map.put("isTravel", 0);
            redisTemplate.opsForHash().putAll(userId, map);
        }
        if (Objects.equals(redisTemplate.opsForHash().get(userId, "isSign"), 0))
            redisTemplate.opsForHash().increment(userId, "signNum", 1);
        //数据转为json
        returnJson.put("userData", JSONObject.toJSON(userInfo));
        returnJson.put("signData", redisTemplate.opsForHash().entries(userId));
        //获取到全部信息后再将是否签到设置为1
        redisTemplate.opsForHash().put(userId,"isSign",1);
        /*jsonobject转javabean*/
        HomePage homePage = JSONObject.parseObject(String.valueOf(returnJson), HomePage.class);
        redisLock.unlock();//解锁
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
    @RequestMapping("/message")
    @ResponseBody
    public Result<List<MessageData>> message(HttpServletResponse response,HttpServletRequest request) {
        int userId=Integer.parseInt(sessionHandler.getSession(request, response, "userId"));
        return RetResponse.makeOKRsp(messageListMapper.selectMessageByUserId(userId));
    }

    /*测试mapstruct*/
    @RequestMapping("/mapstruct")
    @ResponseBody
    public Result mapstruct() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(11);
        userInfo.setUserId(170);
        userInfo.setUserName("ndd");
        userInfo.setNickname("ndds");
        userInfo.setMobilePhone("1234567890");
        userInfo.setBWT_CPL_DVM_OS("k1");
        userInfo.setBWT_TH_SUB_ATCW("k2");
        userInfo.setCityId(56);
        userInfo.setUserGrade(12);
        userInfo.setUserImagePath("picture/path");
        userInfo.setUserIsGcert(0);
        UserData userData = UserInfor2Data.INSTANCE.userinfor2data(userInfo);
        userData.setMediumNum(200);
        userData.setHighNum(400);
        return RetResponse.makeOKRsp(userData);
    }

    /*测试test*/
    @RequestMapping("/test")
    @ResponseBody
    public Result test() {
        redisMethod.selectDB(0);
        for (int i = 0; i < 100; i++) {
            userInfoMapper.selectUserInfoByUserId(1);
        }
        return RetResponse.makeOKRsp("测试");
    }
}
