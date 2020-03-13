package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/13 21:34
 */
@Data
public class CardRoughInfo {

    /*卡片等级*/
    private String cardHeight;

    /*卡片描述*/
    private String cardDescribe;

    /*当前卡片收集个数*/
    private int cardNum;
}
