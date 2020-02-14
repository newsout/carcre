package com.sout.carcre.controller;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.serivce.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lzw on 2020/2/14.
 */
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    MainService mainService;

    /*
    登录功能：
    先判断数据库中是否存在用户，如果不存在先从八维通获取用户数据保存至我们的数据库。
     */
    @PostMapping("api/login")
    public JSONObject login(HttpServletRequest httpServletRequest){
        //通过八维通获取数据
        JSONObject returnJson=mainService.getUserInfoByBWT(new Integer(httpServletRequest.getParameter("user_id")));;

        return returnJson;
    }
}
