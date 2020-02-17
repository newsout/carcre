package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.QueryChip;
import com.sout.carcre.controller.bean.SynCard;
import com.sout.carcre.controller.bean.beanson.ChipCase;
import com.sout.carcre.controller.bean.beanson.ChipNum;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片操作
 */
@Controller
public class CardController {

    /*用户获取随机卡片*/
    @RequestMapping("/querychip")
    @ResponseBody
    public Result querychip(){
        QueryChip queryChip=new QueryChip();
        //返回码
        int code=200;

        //返回指定code信息
        return RetResponse.makeRspCode(code,queryChip,"");
    }

    /*用户合成卡片*/
    @RequestMapping("/syncard")
    @ResponseBody
    public Result<SynCard> syncard(@RequestParam("card_id") String cardId){
        SynCard synCard=new SynCard();

        return RetResponse.makeOKRsp(synCard);
    }

    /*用户跳转卡片界面请求*/
    @RequestMapping("/cardpage")
    @ResponseBody
    public List<ChipNum> cardpage(){
        List<ChipNum> list=new ArrayList<>();

        return list;
    }

    /*用户查看卡片收集情况*/
    @RequestMapping("/shipcollcase")
    @ResponseBody
    public Result shipcollcase(@RequestParam("card_id") String cardId){
        List<ChipCase> list=new ArrayList<>();

        return RetResponse.makeOKRsp(list);
    }
}
