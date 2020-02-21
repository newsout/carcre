package com.sout.carcre.service.bean;

import lombok.Data;

/**
 * 用户现在拥有发的碳积分数
 */
@Data
public class UserGrade {
    /*用户拥有的碳积分总数*/
    private int userGradeAll;

    /*用户现阶段剩余的碳积分数*/
    private int userGrade;
}
