package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.controller.bean.HomePage;
import com.sout.carcre.controller.bean.MessageData;
import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.controller.bean.beanson.UserData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.mapstruct.Do2Vo.UserInfor2Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页页面
 */
@Controller
public class HomeController {

    /*首页请求数据*/
    @RequestMapping("/homepage")
    @ResponseBody
    public Result<HomePage> homepage(@RequestParam("user_id") String userId){
        HomePage homePage=new HomePage();

        return RetResponse.makeOKRsp(homePage);
    }

    /*请求排行榜所有数据*/
    @RequestMapping("/rankdata")
    @ResponseBody
    public Result<List<RankData>> rankdata(){
        List<RankData> list=new ArrayList<>();

        return  RetResponse.makeOKRsp(list);
    }

    /*请求每日任务数据*/
    @RequestMapping("/dailytask")
    @ResponseBody
    public Result<DailyTask> dailytask(){
        DailyTask dailyTask=new DailyTask();

        return  RetResponse.makeOKRsp(dailyTask);
    }


    /*用户查看消息列表*/
    @RequestMapping("/message")
    @ResponseBody
    public Result<MessageData> message(){
        MessageData messageData=new MessageData();

        return RetResponse.makeOKRsp(messageData);
    }

    /*测试mapstruct*/
    @RequestMapping("/mapstruct")
    @ResponseBody
    public Result mapstruct(){
        UserInfo userInfo=new UserInfo();
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
        UserData userData= UserInfor2Data.INSTANCE.userinfor2data(userInfo);
        userData.setMediumNum(200);
        userData.setHighNum(400);
        return RetResponse.makeOKRsp(userData);
    }
}
