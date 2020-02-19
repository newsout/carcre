package com.sout.carcre.service;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.integration.redis.RedisManager;
import com.sout.carcre.integration.redis.RedisMethod;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisMethod redisMethod;
    @Autowired
    RedisManager redisManager;

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
        redisMethod.selectDB(0);
        List<RankData> rankDataList = new ArrayList<>();
        RankData rankData = JSONObject.parseObject(JSONObject.toJSONString(userInfo), RankData.class);
        rankData.setAllValue(rankData.getHighNum() * 3 + rankData.getMediumNum() * 2 + rankData.getLowNum());
        rankDataList.add(rankData); //先把自己加进去
        String key = userInfo.getMobilePhone().substring(0, userInfo.getMobilePhone().length() - 4);
        String item = userInfo.getMobilePhone().substring(userInfo.getMobilePhone().length() - 4);
        redisTemplate.opsForHash().put(key, item, JSONObject.toJSONString(rankData));
        //在查询好友列表
        String[] friendList = userInfo.getUserFriend().split(",");
        for (int i = 0; i < friendList.length; i++) {
            key = friendList[i].substring(0, friendList[i].length() - 4);
            item = friendList[i].substring(friendList[i].length() - 4);
            if (redisTemplate.opsForHash().get(key, item) == null) {
                if (userInfoMapper.userIsSaveByMobilPhone(friendList[i]) == 1) continue;//好友未注册
                UserInfo friendInfo = userInfoMapper.selectUserInfoByMobilPhone(friendList[i]);
                rankData = JSONObject.parseObject(JSONObject.toJSONString(friendInfo), RankData.class);
                redisTemplate.opsForHash().put(key, item, JSONObject.toJSONString(rankData));
                rankData.setAllValue(rankData.getHighNum() * 3 + rankData.getMediumNum() * 2 + rankData.getLowNum());
                rankDataList.add(rankData);
            } else {
                rankData = JSONObject.parseObject((String) redisTemplate.opsForHash().get(key, item), RankData.class);
                rankData.setAllValue(rankData.getHighNum() * 3 + rankData.getMediumNum() * 2 + rankData.getLowNum());
                rankDataList.add(rankData);
            }
        }

        //排序
        Collections.sort(rankDataList, new Comparator<RankData>() {
            @Override
            public int compare(RankData r1, RankData r2) {
                if (r1.getAllValue() > r2.getAllValue()) {
                    //return -1:即为正序排序
                    return -1;
                } else if (r1.getAllValue() == r2.getAllValue()) {
                    return 0;
                } else {
                    //return 1: 即为倒序排序
                    return 1;
                }
            }
        });
        for (int i = 0; i < rankDataList.size(); i++) {
            rankDataList.get(i).setUserRank(i + 1);
        }
        return rankDataList;
    }


}
