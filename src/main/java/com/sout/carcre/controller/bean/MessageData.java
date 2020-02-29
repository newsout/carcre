package com.sout.carcre.controller.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 返回消息列表
 */
@Data
public class MessageData {
    /*消息来源用户昵称*/
    private String nickname;

    /*消息内容*/
    private String megContent;

    /*消息产生时间*/
    private Timestamp createTime;
}
