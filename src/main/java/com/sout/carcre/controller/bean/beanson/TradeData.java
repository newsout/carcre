package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * 商品信息数据（单条）
 */

public class TradeData {
    /*商品ID*/
    private Integer tradeId;

    /*商品图片路径*/
    private String tradePic;

    /*商品名称*/
    private String tradeName;

    /*商品所需碳积分*/
    private Integer tradeHeight;

    /*商品销售量*/
    private int tradeNum;

    /*商品库存量*/
    private int tradeSto;

    public TradeData(){}

    public TradeData(Integer tradeId, String tradePic, String tradeName, double tradePrice, Integer tradeHeight, int tradeNum, int tradeSto) {
        this.tradeId = tradeId;
        this.tradePic = tradePic;
        this.tradeName = tradeName;
        this.tradeHeight = tradeHeight;
        this.tradeNum = tradeNum;
        this.tradeSto = tradeSto;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradePic() {
        return tradePic;
    }

    public void setTradePic(String tradePic) {
        this.tradePic = tradePic;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Integer getTradeHeight() {
        return tradeHeight;
    }

    public void setTradeHeight(Integer tradeHeight) {
        this.tradeHeight = tradeHeight;
    }

    public int getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(int tradeNum) {
        this.tradeNum = tradeNum;
    }

    public int getTradeSto() {
        return tradeSto;
    }

    public void setTradeSto(int tradeSto) {
        this.tradeSto = tradeSto;
    }
}
