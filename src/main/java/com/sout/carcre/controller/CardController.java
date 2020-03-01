package com.sout.carcre.controller;

import com.sout.carcre.controller.bean.CardPage;
import com.sout.carcre.controller.bean.ChipCollCase;
import com.sout.carcre.controller.bean.QueryChip;
import com.sout.carcre.controller.bean.SynCard;
import com.sout.carcre.controller.bean.beanson.ChipCase;
import com.sout.carcre.controller.bean.beanson.ChipNum;
import com.sout.carcre.integration.component.result.Result;
import com.sout.carcre.integration.component.result.RetResponse;
import com.sout.carcre.integration.handler.SessionHandler;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.service.CardService;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.bean.interfacebean.BaseTripResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 卡片操作
 */
@Controller
@CrossOrigin //允许跨域请求注解
public class CardController {

    @Autowired
    SessionHandler sessionHandler;
    @Autowired
    CardService cardService;
    /*八维通接口数据*/
    @Autowired
    MainService mainService;
    @Autowired
    UserInfoMapper userInfoMapper;

    /*用户获取随机卡片*/
    @RequestMapping("/querychip")
    @ResponseBody
    public Result querychip( HttpServletRequest request, HttpServletResponse response){
        //返回码
        int code=200;
        //从session中取出对应用户ID
        String userId=sessionHandler.getSession(request,response,"userId");
        //1、判断是否完成里程
        BaseTripResult basetrip=mainService.baseTriplist(Integer.parseInt(userId));
        //当用户行程状态为已经付款时
        if(basetrip.getStatus().equals("3")){
            //判断用户是否已经领取过碎片
            int chipStatus=userInfoMapper.selectChipStatusByUserId(Integer.parseInt(userId));
            if(chipStatus==0){
                QueryChip queryChip= cardService.querychip(userId,basetrip.getMileage());
                //更新用户领取碎片信息
//                userInfoMapper.updateChipStatusByUserId(Integer.parseInt(userId));

                //设置数据库用户获取碎片标识为0（方便前端测试,实际没有）
//                userInfoMapper.updateChipStatus0ByuserId(Integer.parseInt(userId));

                //返回成功标识
                return RetResponse.makeRspCode(code,queryChip,"");
            }else{
                //已经领取过碎片
                code=0;
                return RetResponse.makeRspCode(code,null,"您已领取过碎片");
            }

        }else{ //用户未完成本次行程
            code=0;
            return RetResponse.makeRspCode(code,null,"");
        }

    }

    /*用户合成卡片*/
    @RequestMapping("/syncard")
    @ResponseBody
    public Result syncard(@RequestParam("card_id") String cardId, HttpServletRequest request, HttpServletResponse response){
        //从session中取出对应用户ID
        String userId=sessionHandler.getSession(request,response,"userId");
        boolean synCard=cardService.syncard(cardId,userId);
        //如果有卡片合成。返回true，如果无卡片合成返回false(或合成失败)
        return RetResponse.makeOKRsp(synCard);
    }

    /*用户跳转卡片界面请求*/
    @RequestMapping("/cardpage")
    @ResponseBody
    public Result cardpage(HttpServletRequest request, HttpServletResponse response){

        //从session中取出对应用户ID
        String userId=sessionHandler.getSession(request,response,"userId");
        CardPage cardPage=cardService.cardpage(userId);
        return RetResponse.makeOKRsp(cardPage);
    }

    /*用户查看卡片收集情况*/
    @RequestMapping("/shipcollcase")
    @ResponseBody
    public Result shipcollcase(@RequestParam("card_id") String cardId,HttpServletRequest request, HttpServletResponse response){

        //从session中取出对应用户ID
        String userId=sessionHandler.getSession(request,response,"userId");
        ChipCollCase chipCollCase=cardService.chipcollcase(userId,cardId);
        return RetResponse.makeOKRsp(chipCollCase);
    }
}
