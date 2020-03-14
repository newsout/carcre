package com.sout.carcre.service;

import com.sout.carcre.controller.bean.ShopPage;
import com.sout.carcre.controller.bean.UserPur;
import com.sout.carcre.controller.bean.beanson.TradeData;
import com.sout.carcre.mapper.TradeInfoMapper;
import com.sout.carcre.mapper.TradeListMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.TradeInfo;
import com.sout.carcre.mapper.bean.TradeList;
import com.sout.carcre.mapstruct.Do2Vo.Tradeinfo2Data;
import com.sout.carcre.service.bean.TradeSell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    public ShopPage tradelistinfo(String userId){
        ShopPage shopPage=new ShopPage();
        List<TradeInfo> list=tradeInfoMapper.selectTradeInfo();
        List<TradeData> list1= new ArrayList<>();
        for(TradeInfo tradeInfo:list){
            //商品库存量大于0时返回前端
            if(tradeInfo.getTradeSto()>0){
                TradeData tradeData=Tradeinfo2Data.INSTANCE.tradeInfo2Data(tradeInfo);
                //判断是否再限时日期内
                String tradeDate=tradeData.getTradeLimit();
                if(!"0".equals(tradeDate)){//限时卡片
                   if(!compareTime(tradeDate)) continue;//在限定日期外不返回
                }
                String[] tradePicList=tradeInfo.getTradePic().split(",");
                tradeData.setTradePicList(Arrays.asList(tradePicList));
                list1.add(tradeData);
            }
        }
        shopPage.setTradeDataList(list1);
        //查询用户现有的碳积分数目
        int grade=userInfoMapper.selectExistGradebyUserId(Integer.parseInt(userId));
        shopPage.setUserGrade(grade);
        return shopPage;
    }

    /**
     *
     * 用户够买商品
     * @param userId 用户ID
     * @param tradeId 商品ID
     * @return 如果用户积分足够够买商品，返回true，否则返回false
     */
    public UserPur userPurTrade(String userId, String tradeId){
        UserPur userPur=new UserPur();
        //1、查询商品信息表获取商品所需积分
        int tradeGrade=tradeInfoMapper.selectGradeById(Integer.parseInt(tradeId));
        //2、更新用户现有碳积分
        int userprocess=userInfoMapper.selectExistGradebyUserId(Integer.parseInt(userId));
        int newgrade=userprocess-tradeGrade;

        //查询现阶段有的商品个数和销售量
        TradeSell tradeSell=tradeInfoMapper.selectTradeStoAndNum(Integer.parseInt(tradeId));
        if(newgrade>=0){
            userInfoMapper.updateGradeByUserId(Integer.parseInt(userId),newgrade);
            //用户商品列表增加
            TradeList tradeList=new TradeList();
            tradeList.setTradeId(Integer.parseInt(tradeId));
            tradeList.setTradeStatus(1);
            tradeList.setUserId(Integer.parseInt(userId));
            tradeListMapper.insertTradeBytradeList(tradeList);
            userPur.setUserIssuccess(true);
            userPur.setUserAllGrade(newgrade);

            /*修改商城内的信息表*/
            int newNum=tradeSell.getTradeNum()+1;
            int newSto=tradeSell.getTradeSto()-1;
            userPur.setTradeSto(newSto);
            //更新商品信息
            tradeInfoMapper.updateTradeStoAndNum(Integer.parseInt(tradeId),newNum,newSto);
        }else {
            userPur.setUserIssuccess(false);
            userPur.setUserAllGrade(userprocess);
            userPur.setTradeSto(tradeSell.getTradeSto());
        }
        return userPur;

    }

    /**
     * 判断时间是否超过当前时间
     * @param time
     * @return
     */
    public boolean compareTime(String time){
        //获取当前时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String presentTime=simpleDateFormat.format(new Date());
        String[] limitTime=time.split("-");
        String[] nowTime=presentTime.split("-");
        for(int i=0;i<limitTime.length;i++){
            int nowTimeData=Integer.parseInt(nowTime[i]);
            int limitTimeData=Integer.parseInt(limitTime[i]);
            if(nowTimeData<limitTimeData) return true;
        }
        return false;
    }
}
