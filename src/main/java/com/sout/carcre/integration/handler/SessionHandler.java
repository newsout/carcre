package com.sout.carcre.integration.handler;

import com.sout.carcre.integration.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lzw on 2020/2/17.
 */


public class SessionHandler {

    @Autowired
    RedisManager redisManager;

    Integer sessionDBIndex=2;
    Integer sessionAge=60*60*24*2;

    public int setSession(){

    }

    public String getSession(HttpServletRequest request, HttpServletResponse response,String key){
        Cookie cookies[]=request.getCookies();
        String sessionID=null;
        for (Cookie cookie:cookies ){
            if (cookie.getName().equals("sessionID"))sessionID=cookie.getValue();
        }
        if (sessionID == null)  return "-1";//cookie中获取不到sessionID
        if (!redisManager.hasKey(sessionID,sessionDBIndex)){ //session无效 删除cookie
            Cookie cookie=new Cookie("sessionID","-1");
            cookie.setMaxAge(1);
            response.addCookie(cookie);
            return "-1";
        }
        redisManager.expire(sessionID,sessionAge,sessionDBIndex);//延长session时间
        return String.valueOf(redisManager.hget(sessionID,key,sessionDBIndex));

    }

}
