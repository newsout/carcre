package com.sout.carcre.controller;

import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.CardInfoMapper;
import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.RankWeeklyMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.MessageList;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.RankService;
import com.sout.carcre.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lzw on 2020/3/15.
 */
@Controller
@CrossOrigin //允许跨域请求注解
public class MessageController {
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


    /*用户查看消息列表*/
    @RequestMapping("/simple_message")
    @ResponseBody
    public Result<List<MessageList>> simpleMessage(HttpServletResponse response, HttpServletRequest request) {
        int userId=Integer.parseInt(sessionHandler.getSession(request, response, "userId"));
        userInfoMapper.updateIsMessageById(userId,0);
        return RetResponse.makeOKRsp(messageListMapper.selectSimpleMessageByUserId(userId));
    }

    @RequestMapping("/message")
    @ResponseBody
    public Result<MessageList> message(@RequestParam("message_id") String messageId, HttpServletResponse response, HttpServletRequest request) {
        return RetResponse.makeOKRsp(messageListMapper.selectMessageById(Integer.parseInt(messageId)));
    }
}
