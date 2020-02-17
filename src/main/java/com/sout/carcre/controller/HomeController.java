package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.controller.bean.HomePage;
import com.sout.carcre.controller.bean.MessageData;
import com.sout.carcre.controller.bean.QueryChip;
import com.sout.carcre.controller.bean.beanson.RankTen;
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
    public HomePage homepage(@RequestParam("user_id") String userId){
        HomePage homePage=new HomePage();

        return homePage;
    }

    /*请求排行榜所有数据*/
    @RequestMapping("/rankdata")
    @ResponseBody
    public List<RankTen> rankdata(){
        List<RankTen> list=new ArrayList<>();

        return list;
    }

    /*请求每日任务数据*/
    @RequestMapping("/dailytask")
    @ResponseBody
    public DailyTask dailytask(){
        DailyTask dailyTask=new DailyTask();

        return dailyTask;
    }


    /*用户查看消息列表*/
    @RequestMapping("/message")
    @ResponseBody
    public MessageData message(){
        MessageData messageData=new MessageData();

        return messageData;
    }
}
