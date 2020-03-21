package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.SenderMessage;
import com.sout.carcre.mapper.bean.MessageList;
import lombok.Data;

import java.util.List;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/21 19:37
 */
@Data
public class SimpleMessage {
    private List<SenderMessage> senderMessageList;
    private List<MessageList> messageListList;
}
