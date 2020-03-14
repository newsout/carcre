package com.sout.carcre.controller.bean;

import lombok.Data;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/2/27 22:55
 */
@Data
public class UserPur {

    /*用户是否下单成功*/
    private boolean userIssuccess;

    /*用户现阶段拥有的积分总数*/
    private int userAllGrade;

    /*商品现有的库存量*/
    private int tradeSto;
}
