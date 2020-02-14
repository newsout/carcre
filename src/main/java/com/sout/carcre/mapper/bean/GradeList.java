package com.sout.carcre.mapper.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by lzw on 2020/2/14.
 */
@Data
/*
记录用户积分变更情况
 */
public class GradeList {
    private long id;
    private Integer userId;
    private Integer gradeNum;
    private String gradeRemark;
    private Timestamp crateTime;
}
