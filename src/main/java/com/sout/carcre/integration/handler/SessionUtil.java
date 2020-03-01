package com.sout.carcre.integration.handler;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/1 23:12
 */
public class SessionUtil {
    public static String getSession(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession httpSession = request.getSession();
        String data= (String) httpSession.getAttribute("userId");
        return  data;
    }
}
