package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * 商品信息数据（单条）
 */
@Data
public class TradeData {
    /*商品ID*/
    private Integer tradeId;

    /*商品图片路径*/
    private String tradePic;

    /*商品名称*/
    private String tradeName;

    /*商品价格*/
    private double tradePrice;

    /*商品所需碳积分*/
    private Integer tradeHeight;

    /*商品销售量*/
    private int tradeNum;

    /*商品库存量*/
    private int tradeSto;
}
