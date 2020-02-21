package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.beanson.TradeData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.mapper.TradeInfoMapper;
import com.sout.carcre.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 碳积分页面
 */
@Controller
public class TradeController {

    @Autowired
    TradeService tradeService;
    @Autowired
    SessionHandler sessionHandler;

    /*返回碳积分商店请求*/
    @RequestMapping("/shoppage")
    @ResponseBody
    public Result shoppage(){
        List<TradeData> list=tradeService.tradelistinfo();
        return RetResponse.makeOKRsp(list);
    }

    /*用户是否下单成功*/
    @RequestMapping("/userpur")
    @ResponseBody
    public Result userpur(@RequestParam("trade_id") String tradeId, HttpServletRequest request, HttpServletResponse response){
        //从session中取出对应用户ID
        String userId=sessionHandler.getSession(request,response,"userId");
        tradeService.userPurTrade(userId,tradeId);
        return RetResponse.makeOKRsp(tradeService.userPurTrade(userId,tradeId));
    }

}
