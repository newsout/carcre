package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.SignData;
import lombok.Data;

/**
 * 返回每日任务数据
 */
@Data
public class DailyTask {
    /*用户签到次数*/
    private int signNum;

    private int isSign;


    /*用户是否出行*/
    private int userIsGo;

    /*用户是否浏览卡片界面*/
    private int userIsScan;

    /*分享次数*/
    private int shareNum;

}
