package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * 用户收集卡片情况信息（单个卡片）
 */
@Data
public class ChipNum {
    /*卡片ID*/
    private String cardId;

    /*碎片个数*/
    private int chipNum;
}
