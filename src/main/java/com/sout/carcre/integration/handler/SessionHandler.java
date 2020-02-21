package com.sout.carcre.integration.handler;

import cn.hutool.core.util.IdUtil;
import com.sout.carcre.integration.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * Created by lzw on 2020/2/17.
 */

@Service
public class SessionHandler {

    @Autowired
    RedisManager redisManager;

    private Integer sessionDBIndex=2;
    private Integer sessionAge=60*60*24*2;

    /*
    添加session
     */
    public void setSession(HttpServletRequest request,HttpServletResponse response,String key, String value){
        Cookie cookies[]= request.getCookies();
        String sessionID=null;
        if (cookies!=null) for (Cookie cookie:cookies)if (cookie.getName().equals("sessionID"))sessionID=cookie.getValue();
        if (sessionID!=null&&redisManager.hasKey(sessionID,sessionDBIndex)){
            redisManager.hset(sessionID,key,value,sessionDBIndex,sessionAge);
            return;
        }
        String uuid= IdUtil.simpleUUID();
        Cookie cookie=new Cookie("sessionID",uuid);
        response.addCookie(cookie);
        redisManager.hset(uuid,key,value,sessionDBIndex,sessionAge);
    }


    /*
    获取session中的key
     */
    public String getSession(HttpServletRequest request, HttpServletResponse response,String key){
        Cookie cookies[]=request.getCookies();
        String sessionID=null;
        if(cookies!=null)for (Cookie cookie:cookies )if (cookie.getName().equals("sessionID"))sessionID=cookie.getValue();
        if (sessionID == null)  return "-1";//cookie中获取不到sessionID
        if (!redisManager.hasKey(sessionID,sessionDBIndex))  return "-1"; //session无效 删除cookie
        redisManager.expire(sessionID,sessionAge,sessionDBIndex);//延长session时间
        return String.valueOf(redisManager.hget(sessionID,key,sessionDBIndex));
    }

}
