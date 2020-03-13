package com.sout.carcre.mapper.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created by lzw on 2020/2/14.
 */

public class TradeInfo {
    private long id;
    private Integer tradeId;
    private String tradeName;
    private double tradePrice;
    private Integer tradeNum;
    private Integer tradeSto;
    private Integer tradeGrade;
    private Integer tradeIsPost;
    private Integer tradeIsSell;
    private String tradePic;
    private String tradeContent;//商品描述信息
    private Timestamp createTime;

    public TradeInfo(){}

    public TradeInfo(long id, Integer tradeId, String tradeName, double tradePrice, Integer tradeNum, Integer tradeSto, Integer tradeGrade, Integer tradeIsPost, Integer tradeIsSell, String tradePic, String tradeContent, Timestamp createTime) {
        this.id = id;
        this.tradeId = tradeId;
        this.tradeName = tradeName;
        this.tradePrice = tradePrice;
        this.tradeNum = tradeNum;
        this.tradeSto = tradeSto;
        this.tradeGrade = tradeGrade;
        this.tradeIsPost = tradeIsPost;
        this.tradeIsSell = tradeIsSell;
        this.tradePic = tradePic;
        this.tradeContent = tradeContent;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public Integer getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Integer tradeNum) {
        this.tradeNum = tradeNum;
    }

    public Integer getTradeSto() {
        return tradeSto;
    }

    public void setTradeSto(Integer tradeSto) {
        this.tradeSto = tradeSto;
    }

    public Integer getTradeGrade() {
        return tradeGrade;
    }

    public void setTradeGrade(Integer tradeGrade) {
        this.tradeGrade = tradeGrade;
    }

    public Integer getTradeIsPost() {
        return tradeIsPost;
    }

    public void setTradeIsPost(Integer tradeIsPost) {
        this.tradeIsPost = tradeIsPost;
    }

    public Integer getTradeIsSell() {
        return tradeIsSell;
    }

    public void setTradeIsSell(Integer tradeIsSell) {
        this.tradeIsSell = tradeIsSell;
    }

    public String getTradePic() {
        return tradePic;
    }

    public void setTradePic(String tradePic) {
        this.tradePic = tradePic;
    }

    public String getTradeContent() {
        return tradeContent;
    }

    public void setTradeContent(String tradeContent) {
        this.tradeContent = tradeContent;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
