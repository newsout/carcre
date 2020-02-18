package com.sout.carcre.service;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lzw on 2020/2/18.
 */
@Service
public class RankService {

    @Autowired
    UserInfoMapper userInfoMapper;


    public List<RankData> getRankData(UserInfo userInfo){
        List<RankData> rankDataList=new ArrayList<>();
        String[] friendList=userInfo.getUserFriend().split(",");
        for(int i=0;i<friendList.length;i++){
            if (userInfoMapper.userIsSaveByMobilPhone(friendList[i])==1)continue;
            UserInfo friendInfo=userInfoMapper.selectUserInfoByMobilPhone(friendList[i]);
            RankData rankData= JSONObject.parseObject(JSONObject.toJSONString(friendInfo), RankData.class);
            rankData.setAllValue(rankData.getHighNum()*3+rankData.getMediumNum()*2+rankData.getLowNum());
            rankDataList.add(rankData);
        }

        Collections.sort(rankDataList, new Comparator<RankData>() {
            @Override
            public int compare(RankData r1, RankData r2) {
                if(r1.getAllValue() > r2.getAllValue()) {
                    //return -1:即为正序排序
                    return -1;
                }else if (r1.getAllValue() == r2.getAllValue()) {
                    return 0;
                }else {
                    //return 1: 即为倒序排序
                    return 1;
                }
            }
        });
        return rankDataList;
    }


}
