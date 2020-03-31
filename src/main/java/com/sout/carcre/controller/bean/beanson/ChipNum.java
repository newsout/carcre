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

    /*待合成的卡片数*/
    private int cardPreNum;

    /*卡片收集总数*/
    private int cardTotalNum;

    /*卡片描述信息*/
    private String cardDescribe;

    /*卡片的限定终止日期*/
    private String cardLimit;
}
