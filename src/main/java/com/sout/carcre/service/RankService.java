package com.sout.carcre.service;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.beanson.RankData;

import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.RankWeeklyMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.RankWeekly;
import com.sout.carcre.mapper.bean.UserInfo;
import org.apache.ibatis.ognl.Ognl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by lzw on 2020/2/18.
 */
@Service
public class RankService {


    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RankWeeklyMapper rankWeeklyMapper;

    @Autowired
    RedisConfig redisConfig;


    public List<RankData> getRankTenData(UserInfo userInfo) {
        List<RankData> rankDataList = getRankData(userInfo);
        RankData remove = null;
        for (int i = 0; i < rankDataList.size(); i++) {
            if (rankDataList.get(i).getNickName().equals(userInfo.getNickname())) {
                remove = rankDataList.remove(i);
            }
        }
        userInfo.setUserRank(Objects.requireNonNull(remove).getUserRank());
        return rankDataList.size() > 10 ? rankDataList.subList(0, 10) : rankDataList;
    }

    public List<RankData> getRankData(UserInfo userInfo) {
        RedisTemplate<String, Object> template = redisConfig.getRedisTemplateByDb(0);
        List<RankData> rankDataList = new ArrayList<>();
        RankData rankData = JSONObject.parseObject(JSONObject.toJSONString(userInfo), RankData.class);
        rankData.setAllValue(rankData.getHighNum() * 3 + rankData.getMediumNum() * 2 + rankData.getLowNum());
        rankDataList.add(rankData); //先把自己加进去
        String key = userInfo.getMobilePhone().substring(0, userInfo.getMobilePhone().length() - 4);
        String item = userInfo.getMobilePhone().substring(userInfo.getMobilePhone().length() - 4);
        template.opsForHash().put(key, item, JSONObject.toJSONString(rankData));
        //在查询好友列表
        String[] friendList = userInfo.getUserFriend().split(",");
        for (int i = 0; i < friendList.length; i++) {
            key = friendList[i].substring(0, friendList[i].length() - 4);
            item = friendList[i].substring(friendList[i].length() - 4);
            if (template.opsForHash().get(key, item) == null) {
                if (userInfoMapper.userIsSaveByMobilPhone(friendList[i]) == 1) continue;//好友未注册
                UserInfo friendInfo = userInfoMapper.selectUserInfoByMobilPhone(friendList[i]);
                rankData = JSONObject.parseObject(JSONObject.toJSONString(friendInfo), RankData.class);
                template.opsForHash().put(key, item, JSONObject.toJSONString(rankData));
                rankDataList.add(rankData);
            } else {
                rankData = JSONObject.parseObject((String) template.opsForHash().get(key, item), RankData.class);
                rankDataList.add(rankData);
            }
        }

        //排序
        rankDataList.sort((r1, r2) -> Integer.compare(r2.getUserGradeAll(), r1.getUserGradeAll()));
        for (int i = 0; i < rankDataList.size(); i++) rankDataList.get(i).setUserRank(i + 1);
        return rankDataList;
    }

    public List<RankWeekly> getRankWeekly(UserInfo userInfo) {
        RedisTemplate<String, Object> template = redisConfig.getRedisTemplateByDb(1);
        List<RankWeekly> rankWeeklyList = new ArrayList<>();
        RankWeekly rankWeekly = rankWeeklyMapper.selectDataByMobilPhone(userInfo.getMobilePhone());
        rankWeeklyList.add(rankWeekly);
        String key = userInfo.getMobilePhone().substring(0, userInfo.getMobilePhone().length() - 4);
        String item = userInfo.getMobilePhone().substring(userInfo.getMobilePhone().length() - 4);
        template.opsForHash().put(key, item, JSONObject.toJSONString(rankWeekly));
        //在查询好友列表
        String[] friendList = userInfo.getUserFriend().split(",");
        for (int i = 0; i < friendList.length; i++) {
            key = friendList[i].substring(0, friendList[i].length() - 4);
            item = friendList[i].substring(friendList[i].length() - 4);
            if (template.opsForHash().get(key, item) == null) {
                rankWeekly = rankWeeklyMapper.selectDataByMobilPhone(friendList[i]);
                if (rankWeekly != null) {
                    rankWeeklyList.add(rankWeekly);
                    template.opsForHash().put(key, item, JSONObject.toJSONString(rankWeekly));
                }
            } else {
                rankWeekly = JSONObject.parseObject((String) template.opsForHash().get(key, item), RankWeekly.class);
                rankWeeklyList.add(rankWeekly);
            }
        }
        //排序
        rankWeeklyList.sort((r1, r2) -> Integer.compare(r2.getGradeNum(), r1.getCollNum()));
        //给rankNum赋值
        for (int i = 0; i < rankWeeklyList.size(); i++) rankWeeklyList.get(i).setRankNum(i + 1);
        for (int i = 0; i < rankWeeklyList.size(); i++)
            if (rankWeeklyList.get(i).getMobilePhone().equals(userInfo.getMobilePhone())) {
                rankWeekly = rankWeeklyList.remove(i);
                rankWeeklyList.add(0, rankWeekly);
            }
        return rankWeeklyList;
    }

}
