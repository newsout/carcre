package com.sout.carcre.mapper.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by lzw on 2020/2/21.
 */
@Data
/*
用于实现周排行
 */
public class RankWeekly {
    private Integer id;
    private Integer userId;
    private Integer rankNum;
    private String mobilePhone;
    private String nickname;
    private Integer gradeNum;
    private Integer collNum;
    private Timestamp createTime;
}
