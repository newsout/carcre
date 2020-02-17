package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * 用户个人信息
 */
@Data
public class UserData {
    /*用户ID*/
    private String userId;

    /*用户图像路径*/
    private String userImagePath;

    /*用户昵称*/
    private String nickname;

    /*用户总碳积分*/
    private int userGrade;

    /*用户排名*/
    private int userRank;

    /*是否获得证书*/
    private boolean userIsGcert;

    /*高等级卡片收集个数*/
    private int highNum;

    /*中等级收集卡片个数*/
    private int mediumNum;

    /*低等级卡片收集个数*/
    private int lowNum;
}
