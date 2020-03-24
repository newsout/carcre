package com.sout.carcre.controller;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.controller.bean.HomePage;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.BeanAndMap;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.CardInfoMapper;
import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.RankWeeklyMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.DailyTaskService;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.RankService;
import com.sout.carcre.service.TestService;
import com.sout.carcre.service.bean.interfacebean.BaseTripResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Autowired
    CardInfoMapper cardInfoMapper;

    @Value("${redisDB.dailyTaskDB}")
    private Integer dailyTaskDB;



    /*首页请求数据*/
    @RequestMapping("/homepage")
    @ResponseBody
    public Result<HomePage> homepage(@RequestParam("user_id") String userId,  HttpServletRequest request,HttpServletResponse response, HttpSession session) {
        sessionHandler.setSession(request, response, "userId", userId);
        JSONObject returnJson = new JSONObject();
        //获取用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(Integer.parseInt(userId));
        if (userInfo == null) { //注册新用户
            userInfo = mainService.getUserInfoByBWT(Integer.parseInt(userId));
            userInfoMapper.insertUserInfo(userInfo);
            rankWeeklyMapper.insertRankWeeklyData(userInfo);
        }
        //查询每日任务情况 先切换到1号库
        RedisTemplate<String, Object> template1=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        Map<String, String> map = new HashMap<>();
        if (!template1.hasKey(userId)) {
            DailyTask dailyTask=new DailyTask();
            map= BeanAndMap.Bean2Map(dailyTask);
            template1.opsForHash().putAll(userId, map);
        }
        System.out.println(template1.opsForHash().get(userId, "isSign"));
        if (Objects.equals(template1.opsForHash().get(userId, "isSign"), "0"))
            template1.opsForHash().increment(userId, "signNum", 1);
        //数据转为json
        returnJson.put("userData", JSONObject.toJSON(userInfo));
        returnJson.put("signData", template1.opsForHash().entries(userId));
        //获取到全部信息后再将是否签到设置为1
        template1.opsForHash().put(userId,"isSign","1");

        //返回卡片信息
        List<Integer> cardIds=cardInfoMapper.cardUsableId();
        returnJson.put("cardAllNum",cardIds.size());
        String[] cardCollInfos= userInfo.getUserCard().split(",");
        int count=0;
        for (String cardCollInfo : cardCollInfos)
            if (cardIds.contains(Integer.parseInt(cardCollInfo.split(":")[0]))) count++;
        returnJson.put("cardCollNum",count);
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


    @Autowired
    DailyTaskService dailyTaskService;

    @RequestMapping("/dailytask")
    @ResponseBody
    public Result<Map> dailyTask (HttpServletResponse response, HttpServletRequest request) {
        Integer userId = new Integer(sessionHandler.getSession(request, response, "userId"));
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        String userStatus= (String) template.opsForHash().get(String.valueOf(userId),"userIsGo");
        if(userStatus==null||userStatus.equals("0")){//没有存储或者存储了但是没有完成里程
            BaseTripResult baseTripResult = mainService.baseTriplist(userId);
            String status = baseTripResult.getStatus();//获取行程信息的状态
            if(status.equals("3")){//已经完成行程
                template.opsForHash().put(String.valueOf(userId),"userIsGo","1");
                template.opsForHash().put(String.valueOf(userId),"userGoNum",String.valueOf(baseTripResult.getMileage()));
            }
        }
        return RetResponse.makeOKRsp(template.opsForHash().entries(userId.toString()));
    }

}
