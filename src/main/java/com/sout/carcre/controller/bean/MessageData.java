package com.sout.carcre.controller.bean;

import lombok.Data;

import java.util.List;

/**
 * 返回消息列表
 */
@Data
public class MessageData {
    /*消息内容*/
    private List<String> megContent;

    /*消息产生时间*/
    private String createTime;
}
