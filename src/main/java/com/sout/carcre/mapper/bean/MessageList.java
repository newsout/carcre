package com.sout.carcre.mapper.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lzw on 2020/2/29.
 */
@Data
public class MessageList {
    private Integer id;
    private Integer userId;
    private String nickname;
    private Integer userIdFrom;
    private String megContent;
    private String simpleContent;
    private Timestamp createTime;

    public MessageList(){}

    public MessageList(Integer userId, Integer userIdFrom, String megContent, String simpleContent) {
        this.userId = userId;
        this.userIdFrom = userIdFrom;
        this.megContent = megContent;
        this.simpleContent = simpleContent;
        this.createTime=new Timestamp(new Date().getTime());
    }

}
