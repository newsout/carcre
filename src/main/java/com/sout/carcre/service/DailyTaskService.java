package com.sout.carcre.service;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.controller.bean.DailyTask;
import com.sout.carcre.integration.handler.BeanAndMap;
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Objects;

/**
 * Created by lzw on 2020/3/10.
 */
@Service
public class DailyTaskService {
    @Autowired
    RedisConfig redisConfig;

    private Integer dailyTaskDB=2;
    //定义每日任务最大次数
    private Integer shareNumMax=3;


    //根据userinfo获取每日任务信息
    public DailyTask getDailyTaskInfo(Integer userId) {
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        DailyTask dailyTask=new DailyTask();
        return dailyTask;
    }


    //修改每日任务信息
    public void updateUserIsGo(Integer userId){
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        template.opsForHash().put(userId.toString(),"userIsGo",1);
    }

    public void updateUserIsScan(Integer userId){
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        template.opsForHash().put(userId.toString(),"userIsScan",1);
    }

    public void increaseShareNum(Integer userId){
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        Object value = template.opsForHash().get(userId.toString(),"shareNum");
        int num=Integer.parseInt(Objects.requireNonNull(value).toString());
        if (num<shareNumMax)template.opsForHash().increment(userId.toString(),"shareNum",1);
    }

    public void increaseSignNum(Integer userId){
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        template.opsForHash().increment(userId.toString(),"signNum",1);
    }

}
