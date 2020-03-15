package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.CardInfoMapper;
import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.RankWeeklyMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.RankWeekly;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.RankService;
import com.sout.carcre.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 2020/3/15.
 */
@Controller
@CrossOrigin //允许跨域请求注解
public class RankController {
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
    /*请求总排行榜数据*/
    @RequestMapping("/rankdata")
    @ResponseBody
    public Result<List<RankData>> rankdata(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(sessionHandler.getSession(request, response, "userId"));
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


}
