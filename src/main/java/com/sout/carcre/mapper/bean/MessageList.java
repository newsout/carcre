package com.sout.carcre.mapper.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by lzw on 2020/2/29.
 */
@Data
public class MessageList {
    private Integer id;
    private Integer userId;
    private Integer userIdFrom;
    private String megContent;
    private Timestamp createTime;
}
