package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/21 16:45
 */
@Data
public class SenderMessage {
    /*发送者ID*/
    private String senderId;

    /*碎片ID*/
    private String chipId;

    /*碎片所属等级*/
    private String chipHeight;

    /*碎片个数*/
    private int chipNum;

    /*发送者名称*/
    private String senderName;

    /*碎片头像路径*/
    private String chipPath;
}

