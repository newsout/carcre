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
import com.sout.carcre.integration.redis.RedisConfig;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.service.CardService;
import com.sout.carcre.service.MainService;
import com.sout.carcre.service.bean.interfacebean.BaseTripResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    RedisConfig redisConfig;

    @Value("${redisDB.rankdataDB}")
    private Integer rankdataDB;
    @Value("${redisDB.rankweeklyDB}")
    private Integer rankweeklyDB;
    @Value("${redisDB.dailyTaskDB}")
    private Integer dailyTaskDB;
    @Value("${redisDB.sessionDB}")
    private Integer sessionDB;
    /*用户获取随机卡片*/
    @RequestMapping("/querychip")
    @ResponseBody
    public Result querychip( HttpServletRequest request, HttpServletResponse response){
        //返回码
        int code=200;
        //从session中取出对应用户ID
        String userId=sessionHandler.getSession(request,response,"userId");
        //1、判断是否完成里程
        RedisTemplate<String, Object> template=redisConfig.getRedisTemplateByDb(dailyTaskDB);
        String status= (String) template.opsForHash().get(String.valueOf(userId),"userIsGo");
        //当用户行程状态为已经付款时
        if(status.equals("1")){//说明已经完成出行但是没有抽取卡片
            //判断用户是否已经领取过碎片
                String mileage=String.valueOf(template.opsForHash().get(String.valueOf(userId),"userGoNum"));
                //获得里程数对应的卡片等级
                int height=cardHeightByMile(Integer.parseInt(mileage));
                QueryChip queryChip= cardService.querychip(userId,height);
                template.opsForHash().put(String.valueOf(userId),"userIsGo","-1");
                //返回成功标识
                return RetResponse.makeRspCode(code,queryChip,"");
        }else if(status.equals("0")){ //用户未完成本次行程
            code=0;
            return RetResponse.makeRspCode(code,null,"");
        }else {
            code=0;
            return RetResponse.makeRspCode(code,null,"您已领取过碎片");
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

    /*用户赠送碎片*/
    @RequestMapping("/sendchip")
    @ResponseBody
    public Result sendChip(@RequestParam("chip_id") String chipId,@RequestParam("friend_id") String friendId,HttpServletRequest request, HttpServletResponse response){
        String userId=sessionHandler.getSession(request,response,"userId");
        boolean b = cardService.sendChip(userId, friendId, chipId);
        return RetResponse.makeOKRsp(b);
    }

    /*用户接受碎片*/
    @RequestMapping("/recvchip")
    @ResponseBody
    public Result recvchip(@RequestParam("chip_id")String chipId,@RequestParam("sender_id")String senderId,HttpServletRequest request, HttpServletResponse response){
        String userId=sessionHandler.getSession(request,response,"userId");
        boolean b = cardService.recvChip(userId, senderId, chipId);
        return RetResponse.makeOKRsp(b);
    }

    /**
     * 里程数获得卡片等级，里程数越大越大概率获得等级大的卡片
     * @param mileage 里程数
     * @return 卡片等级
     */
    public int cardHeightByMile(int mileage){
        if(mileage<10){
            return 1;
        }else if(mileage<20){
            return 2;
        }else return 3;
    }
}
