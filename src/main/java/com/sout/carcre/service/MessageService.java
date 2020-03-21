package com.sout.carcre.service;

import com.sout.carcre.controller.bean.beanson.SenderMessage;
import com.sout.carcre.mapper.CardInfoMapper;
import com.sout.carcre.mapper.MessageListMapper;
import com.sout.carcre.mapper.UserInfoMapper;
import com.sout.carcre.mapper.bean.MessageList;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.bean.CardFewInfo;
import com.sout.carcre.service.bean.CardHeightContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw on 2020/3/7.
 */
@Service
public class MessageService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    MessageListMapper messageListMapper;
    @Autowired
    CardInfoMapper cardInfoMapper;

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

    /**
     * 查询用户被赠送的碎片信息
     * @param userId
     * @return
     */
    public List<SenderMessage> querySendedChip(int userId){
        List<SenderMessage> list=new ArrayList<>();
        //从数据库中查询赠送信息
        String sendChipInfo=userInfoMapper.selectSendedChipByUserId(userId);
        if(sendChipInfo!=null&& !sendChipInfo.equals("")){//当用户被赠送时，不修改is_message字段，用户赠送卡片一直存在
            //除非被赠送碎片字段为空（用户同意一个删除一个）
            String[] chipArray=sendChipInfo.split(",");
            //根据碎片查询其他字段信息
            for(int i=0;i<chipArray.length;i++){
                SenderMessage senderMessage=new SenderMessage();
                String[] chipInfo=chipArray[i].split("&");
                //通过碎片ID获取碎片等级（由于碎片的前面是卡片的ID）
                String chipId=chipInfo[1];
                String cardId=chipId.split(":")[0];
                //根据卡片ID获取等级信息
                CardHeightContent cardHeightContent=cardInfoMapper.selectCardHeightByCardId(Integer.parseInt(cardId));
                int chipIndex=Integer.parseInt(chipId.split(":")[1]);
                //获取碎片图片路径
                String chipPathFront=cardHeightContent.getCardContent().split(",")[chipIndex];
                String chipPath=chipPathFront.split(":")[1];
                //获取用户昵称
                String nickname=userInfoMapper.selectNickNameByUserId(Integer.parseInt(chipInfo[0]));
                //获取被赠送的碎片数量
                int chipNum=Integer.parseInt(chipInfo[2]);
                senderMessage.setChipId(chipId);
                senderMessage.setChipHeight(cardHeightContent.getCardHeight());
                senderMessage.setChipNum(chipNum);
                senderMessage.setChipPath(chipPath);
                senderMessage.setSenderId(chipInfo[0]);
                senderMessage.setSenderName(nickname);
                list.add(senderMessage);
            }
        }
        return list;
    }
}
