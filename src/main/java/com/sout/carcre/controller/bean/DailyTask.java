package com.sout.carcre.controller.bean;

import lombok.Data;

/**
 * 返回每日任务数据
 */
@Data
public class DailyTask {
    /*用户是否分享*/
    private boolean userIsShare;

    /*用户是否出行*/
    private boolean userIsGo;

    /*用户是否浏览卡片界面*/
    private boolean userIsScan;
}
