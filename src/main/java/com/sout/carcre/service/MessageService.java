package com.sout.carcre.service;

import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.MessageList;
import com.sout.carcre.mapper.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lzw on 2020/3/7.
 */
@Service
public class MessageService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    MessageListMapper messageListMapper;

    private String[] messageContent = {"你的好友XXX的消息测试测试测试测试测试测试测试测试测试", "你的好友XXX的消息测试2测试2测试2测试2测试2测试2测试2测试2测试2"};

    //推送消息
    public void pushPersonalMessage(UserInfo userInfo, Integer contentIndex) {
        String[] friendList = userInfo.getUserFriend().split(",");
        for (String friend : friendList) {
            MessageList messageList = new MessageList(userInfoMapper.selectUserInfoByMobilPhone(friend).getUserId(), userInfo.getUserId(),
                    messageContent[contentIndex].replace("XXX",userInfo.getNickname()), messageContent[contentIndex].replace("XXX",userInfo.getNickname()).substring(0, 10));
            messageListMapper.insertMessage(messageList);
            userInfoMapper.updateIsMessageByMobilPhone(friend,1);//更新好友message状态
        }
    }
}
