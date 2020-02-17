package com.sout.carcre.controller.bean;

import lombok.Data;

/**
 * 返回每日任务数据
 */
@Data
public class DailyTask {

    /*用户是否签到*/
    private int userIsSign;

    /*用户是否分享*/
    private int userIsShare;

    /*用户是否出行*/
    private int userIsGo;

    /*用户是否浏览卡片界面*/
    private int userIsScan;

    /*分享次数*/
    private int shareNum;
}
