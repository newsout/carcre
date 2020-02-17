package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.RankData;
import com.sout.carcre.controller.bean.beanson.SignData;
import com.sout.carcre.controller.bean.beanson.UserData;
import lombok.Data;

/**
 * 返回首页请求数据
 */
@Data
public class HomePage {
    /*用户个人数据*/
    private UserData userData;

    /*签到信息*/
    private SignData signData;

    /*排行榜前十名信息*/
    private RankData rankData;

    /*判断是否有消息*/
    private boolean message;
}
