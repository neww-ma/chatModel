package com.losstime.chatMessage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.losstime.chatMessage.pojo.ChatMessage;

import java.util.List;

/**
 * @author 马明
 */
public interface ChatMessageService extends IService<ChatMessage> {
    List<ChatMessage> selectMsgByUidAndFid(String uid,String friendId);
}
