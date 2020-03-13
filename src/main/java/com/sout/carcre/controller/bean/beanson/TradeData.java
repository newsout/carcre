package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

import java.util.List;

/**
 * 商品信息数据（单条）
 */

public class TradeData {
    /*商品ID*/
    private Integer tradeId;

    /*商品图片路径*/
    private List<String> tradePicList;

    /*商品名称*/
    private String tradeName;

    /*商品所需碳积分*/
    private Integer tradeHeight;

    /*商品销售量*/
    private int tradeNum;

    /*商品库存量*/
    private int tradeSto;

    /*商品描述信息*/
    private String tradeContent;

    public TradeData(){}

    public TradeData(Integer tradeId, List<String> tradePicList, String tradeName, Integer tradeHeight, int tradeNum, int tradeSto, String tradeContent) {
        this.tradeId = tradeId;
        this.tradePicList = tradePicList;
        this.tradeName = tradeName;
        this.tradeHeight = tradeHeight;
        this.tradeNum = tradeNum;
        this.tradeSto = tradeSto;
        this.tradeContent = tradeContent;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public List<String> getTradePicList() {
        return tradePicList;
    }

    public void setTradePicList(List<String> tradePicList) {
        this.tradePicList = tradePicList;
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

    public String getTradeContent() {
        return tradeContent;
    }

    public void setTradeContent(String tradeContent) {
        this.tradeContent = tradeContent;
    }
}
