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
    private String userPiece;
    private Integer userGradeAll;
    private Integer userGrade;
    private Integer userIsGcert;
    private Timestamp crateTime;

    public UserInfo(Integer userId) {
        this.userId = userId;
        this.crateTime=new Timestamp(new Date().getTime());
    }

    public UserInfo(){}
}
