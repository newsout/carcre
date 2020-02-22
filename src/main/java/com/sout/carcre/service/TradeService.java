package com.sout.carcre.service;

import com.sout.carcre.controller.bean.beanson.TradeData;
import com.sout.carcre.mapper.TradeInfoMapper;
import com.sout.carcre.mapper.TradeListMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.TradeInfo;
import com.sout.carcre.mapper.bean.TradeList;
import com.sout.carcre.mapstruct.Do2Vo.Tradeinfo2Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 碳积分商城页面
 */
@Service
public class TradeService {

    @Autowired
    TradeInfoMapper tradeInfoMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    TradeListMapper tradeListMapper;

    //转化商品信息-数据库->controller
    public List<TradeData> tradelistinfo(){
        List<TradeInfo> list=tradeInfoMapper.selectTradeInfo();
        List<TradeData> list1= new ArrayList<>();
        for(TradeInfo tradeInfo:list){
            list1.add(Tradeinfo2Data.INSTANCE.tradeInfo2Data(tradeInfo));
        }
        return list1;
    }

    /**
     *
     * 用户够买商品
     * @param userId 用户ID
     * @param tradeId 商品ID
     * @return 如果用户积分足够够买商品，返回true，否则返回false
     */
    public boolean userPurTrade(String userId,String tradeId){
        //1、查询商品信息表获取商品所需积分
        int tradeGrade=tradeInfoMapper.selectGradeById(Integer.parseInt(tradeId));
        //2、更新用户现有碳积分
        int userprocess=userInfoMapper.selectGradebyUserId(Integer.parseInt(userId));
        int newgrade=userprocess-tradeGrade;
        if(newgrade>=0){
            userInfoMapper.updateGradeByUserId(Integer.parseInt(userId),newgrade);
            //用户商品列表增加
            TradeList tradeList=new TradeList();
            tradeList.setTradeId(Integer.parseInt(tradeId));
            tradeList.setTradeStatus(1);
            tradeList.setUserId(Integer.parseInt(userId));
            int data=tradeListMapper.insertTradeBytradeList(tradeList);
            System.out.println(data);
            return true;
        }else return false;

    }
}
