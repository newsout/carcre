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

    public List<MessageList> selectSimpleMessageByUserId(Integer userId);

    public MessageList selectMessageById(Integer id);

    public int insertMessage (MessageList messageList);

}
