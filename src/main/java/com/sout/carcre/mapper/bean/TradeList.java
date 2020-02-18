package com.sout.carcre.mapper.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by lzw on 2020/2/14.
 */
@Data
/*
记录用户交易情况
 */
public class TradeList {
    private long id;
    private Integer userId;
    private Integer tradeId;
    private Integer tradeStatus;
    private Timestamp createTime;
}
