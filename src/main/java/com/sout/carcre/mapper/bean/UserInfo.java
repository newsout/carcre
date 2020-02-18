package com.sout.carcre.mapper.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lzw on 2020/2/13.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private long id;
    private Integer userId;
    private String userName;
    private String nickname;
    private String mobilePhone;
    private Integer userIsHeight;
    private String userImagePath;
    private Integer cityId;
    private String BWT_CPL_DVM_OS;
    private String BWT_TH_SUB_ATCW;
    private String userCard;
    private int highNum;
    private int mediumNum;
    private int lowNum;
    private String userPiece;
    private Integer userGradeAll;
    private Integer userGrade;
    private Integer userIsGcert;
    private String userFriend;
    private Timestamp createTime;

    public UserInfo(){
        this.createTime=new Timestamp(new Date().getTime());
        this.userIsHeight=0;
        this.userCard="";
        this.highNum=0;
        this.mediumNum=0;
        this.lowNum=0;
        this.userPiece="";
        this.userGradeAll=0;
        this.userGrade=0;
        this.userIsGcert=0;
        this.userFriend="";
    }

}
