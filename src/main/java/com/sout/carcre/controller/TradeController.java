package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.beanson.TradeData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 碳积分页面
 */
public class TradeController {

    /*返回碳积分商店请求*/
    @RequestMapping("/shoppage")
    @ResponseBody
    public Result shoppage(){
        List<TradeData> list=new ArrayList<>();

        return RetResponse.makeOKRsp(list);
    }

    /*用户是否下单成功*/
    @RequestMapping("/userpur")
    @ResponseBody
    public Result userpur(@RequestParam("trade_id") String tradeId){

        return RetResponse.makeOKRsp(true);
    }

}
