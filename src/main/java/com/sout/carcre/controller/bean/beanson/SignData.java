package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * 签到数据
 */
@Data
public class SignData {
    /*是否签到*/
    private boolean isSign;

    /*连续签到天数*/
    private int signNum;
}
