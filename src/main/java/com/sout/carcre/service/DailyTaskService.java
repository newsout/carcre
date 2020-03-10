package com.sout.carcre.service;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

/**
 * Created by lzw on 2020/3/10.
 */
@Service
public class DailyTaskService {
    @Autowired
    RedisConfig redisConfig;

    //根据userinfo获取每日任务信息
    public DailyTask getDailyTaskInfo(UserInfo userInfo){
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(1);
        DailyTask dailyTask=new DailyTask();
        // rankData = JSONObject.parseObject((String) template.opsForHash().get(key, item), RankData.class);
        dailyTask= JSONObject.parseObject((String) template.opsForValue().get(userInfo.getUserId()), DailyTask.class);
        return dailyTask;
    }


    //修改每日任务信息



}
