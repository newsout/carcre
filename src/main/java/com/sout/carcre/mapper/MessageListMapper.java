package com.sout.carcre.mapper;

import com.sout.carcre.controller.bean.MessageData;
import com.sout.carcre.mapper.bean.MessageList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lzw on 2020/2/29.
 */
@Repository
@Mapper
public interface MessageListMapper {

    //查看消息列表
    public List<MessageList> selectSimpleMessageByUserId(Integer userId);

    //查看消息详情
    public MessageList selectMessageById(Integer id);

    //创建新消息
    public int insertMessage (MessageList messageList);

}
