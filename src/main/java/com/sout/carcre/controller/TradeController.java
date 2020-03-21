package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.QueryChip;
import com.sout.carcre.controller.bean.ShopPage;
import com.sout.carcre.controller.bean.TradeChip;
import com.sout.carcre.controller.bean.UserPur;
import com.sout.carcre.controller.bean.beanson.TradeData;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.mapper.GradeListMapper;
import com.sout.carcre.mapper.TradeInfoMapper;
import com.sout.carcre.mapper.TradeListMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.TradeList;
import com.sout.carcre.service.CardService;
import com.sout.carcre.service.TradeService;
import com.sout.carcre.service.bean.GradeListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin //允许跨域请求注解
public class TradeController {

    @Autowired
    TradeService tradeService;
    @Autowired
    CardService cardService;
    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    GradeListMapper gradeListMapper;
    @Autowired
    TradeListMapper tradeListMapper;

    static int tradeChipPrice=50;//模拟在碳商城购买稀有碎片所需的碳积分数，需删除
    /*返回碳积分商店请求*/
    @RequestMapping("/shoppage")
    @ResponseBody
    public Result shoppage(HttpServletRequest request,HttpServletResponse response){
        String userID=sessionHandler.getSession(request,response,"userId");
        ShopPage shopPage=tradeService.tradelistinfo(userID);
        return RetResponse.makeOKRsp(shopPage);
    }

    /*用户是否下单成功*/
    @RequestMapping("/userpur")
    @ResponseBody
    public Result userpur(@RequestParam("trade_id") String tradeId, HttpServletRequest request, HttpServletResponse response){
        //从session中取出对应用户ID
        String userId=sessionHandler.getSession(request,response,"userId");
//        tradeService.userPurTrade(userId,tradeId);
        UserPur userPur=tradeService.userPurTrade(userId,tradeId);
        return RetResponse.makeOKRsp(userPur);
    }

    /*碳积分商城获取限定碎片*/
    @RequestMapping("/tradechip")
    @ResponseBody
    public Result tradeGetChip(HttpServletRequest request, HttpServletResponse response){
        String userId=sessionHandler.getSession(request,response,"userId");
        //获取用户剩余碳积分数
        int userGrade=userInfoMapper.selectExistGradebyUserId(Integer.parseInt(userId));
        //判断用户积分数是否满足当前数
        int userNowGrade=userGrade-tradeChipPrice;
        if(userGrade<0) return RetResponse.makeErrRsp("您的积分数不足");
        //更新用户现有积分数
        userInfoMapper.updateGradeByUserId(Integer.parseInt(userId),userNowGrade);
        //获取稀有卡片的随机碎片
        QueryChip queryChip=cardService.querychip(userId,1);//假设等级一为限定卡片
        TradeChip tradeChip=new TradeChip();
        tradeChip.setCardIsSyn(queryChip.isCardIsSyn());
        tradeChip.setChipPath(queryChip.getChipInfo().getChipPath());
        tradeChip.setUserGrade(userNowGrade);
        //存储碳积分交易信息（记录）
        GradeListInfo gradeListInfo=new GradeListInfo();
        gradeListInfo.setUserId(Integer.parseInt(userId));
        gradeListInfo.setGrade(tradeChipPrice);
        gradeListInfo.setGradeRemark("rare card");
        gradeListMapper.insertGradeListByRemark(gradeListInfo);
        //存储商品交易信息
        TradeList tradeList=new TradeList();
        tradeList.setUserId(Integer.parseInt(userId));
        tradeList.setTradeId(Integer.parseInt("100"));
        tradeList.setTradeStatus(2);
        tradeListMapper.insertTradeBytradeList(tradeList);
        return RetResponse.makeOKRsp(tradeChip);
    }
}
