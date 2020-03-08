package com.sout.carcre.integration.handler;

import cn.hutool.core.util.IdUtil;
import com.sout.carcre.integration.redis.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

/**
 * Created by lzw on 2020/2/17.
 */

@Service
public class SessionHandler {

    @Autowired
    RedisConfig redisConfig;

    private Integer sessionDBIndex=2;
    private long sessionAge=60*60*24*2;


    /*
    添加session
     */
    public void setSession(HttpServletRequest request,HttpServletResponse response,String key,String value){
        RedisTemplate<String, Object> template2=redisConfig.getRedisTemplateByDb(sessionDBIndex);
        Cookie[] cookies = request.getCookies();
        String sessionID=null;
        if (cookies!=null) for (Cookie cookie:cookies)if (cookie.getName().equals("sessionID"))sessionID=cookie.getValue();
        if (sessionID!=null&&template2.hasKey(sessionID)){ //重新设置session过期时间
            template2.expire(sessionID,sessionAge, TimeUnit.SECONDS);
            return;
        }
        String uuid= IdUtil.simpleUUID();
        Cookie cookie=new Cookie("sessionID",uuid);
        response.addCookie(cookie);
        template2.opsForHash().put(uuid,key,value);
        template2.expire(uuid,sessionAge, TimeUnit.SECONDS);
    }


    /*
    获取session中的key
     */
    public String getSession(HttpServletRequest request, HttpServletResponse response,String key){
        RedisTemplate<String, Object> template2=redisConfig.getRedisTemplateByDb(sessionDBIndex);
        Cookie[] cookies = request.getCookies();
        String sessionID=null;
        if(cookies!=null)for (Cookie cookie:cookies )if (cookie.getName().equals("sessionID"))sessionID=cookie.getValue();
        if (sessionID == null)  return "1";//cookie中获取不到sessionID  暂时返回1
        if (!template2.hasKey(sessionID))  return "-1"; //sessionID无效 前端应当返回登陆页面
        template2.expire(sessionID,sessionAge,TimeUnit.SECONDS);//延长session时间
        return String.valueOf(template2.opsForHash().get(sessionID,key));
    }
}
