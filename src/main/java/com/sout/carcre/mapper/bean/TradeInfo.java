package com.sout.carcre.mapper.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by lzw on 2020/2/14.
 */
@Data
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
    private Timestamp createTime;
}
