package com.sout.carcre.controller;


import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lzw on 2020/2/13.
 */
@Controller
public class MainController {

    @RequestMapping("/result")
    @ResponseBody
    public Result testresult(){
        System.out.println("result");
        return  RetResponse.makeOKRsp("success测试成功");
    }
}
