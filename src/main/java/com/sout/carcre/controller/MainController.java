package com.sout.carcre.controller;


import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.redis.RedisUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lzw on 2020/2/13.
 */
@Controller
public class MainController {

    @Autowired
    RedisUser redisUser;
    @RequestMapping("/result")
    @ResponseBody
    public Result testresult(){
        System.out.println("result");
        return  RetResponse.makeOKRsp("success测试成功");
    }

    /*测试redis*/
    @RequestMapping("/redistest")
    @ResponseBody
    public Result redistest(){
        String data=redisUser.cache();
        return  RetResponse.makeOKRsp(data);
    }

    /*删库后获取值*/
    @RequestMapping("/delredis")
    @ResponseBody
    public Result delredis(){
        redisUser.cachedelete();
        return RetResponse.makeOKRsp(redisUser.cache("meaasge"));
    }

}
