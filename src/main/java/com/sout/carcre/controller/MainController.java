package com.sout.carcre.controller;


import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 2020/2/13.
 */
@Controller
public class MainController {

    @Autowired
    RedisManager redisManager;
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
        List<String> list=new ArrayList<>();
        list.add("v11");
        list.add("v12");
        list.add("v13");
        list.add("v14");
        redisManager.set("m1","v1",3);
        redisManager.lSet("k1",list,3);
        redisManager.hset("hash01","k1","v1",4);
        redisManager.hset("hash01","k2","v2",4);
        String data= (String) redisManager.get("m1",3);
        return  RetResponse.makeOKRsp(data);
    }

//    /*删库后获取值*/
//    @RequestMapping("/delredis")
//    @ResponseBody
//    public Result delredis(){
//        redisUser.cachedelete();
//        return RetResponse.makeOKRsp(redisUser.cache("meaasge"));
//    }

}
