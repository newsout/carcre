package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * 排行榜信息
 */
@Data
public class RankData {
    /*排行榜排名*/
    private int userRank;

    /*好友昵称*/
    private String nickName;

    /*总碳积分数*/
    private int userGradeAll;

    /*高等级卡片收集个数*/
    private int highNum;

    /*中等级卡片收集个数*/
    private int mediumNum;

    /*低等级卡片收集个数*/
    private int lowNum;

    //卡片权值 便于排序
    private int allValue;
}
