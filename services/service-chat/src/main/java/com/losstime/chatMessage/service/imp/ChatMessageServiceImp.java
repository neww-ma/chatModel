package com.losstime.chatMessage.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.losstime.chatMessage.dao.ChatMessageMapper;
import com.losstime.chatMessage.pojo.ChatMessage;
import com.losstime.chatMessage.service.ChatMessageService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 马明
 */
@Service
public class ChatMessageServiceImp extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {
    @Override
    public List<ChatMessage> selectMsgByUidAndFid(String uid, String friendId) {
        return this.list(new Page<ChatMessage>(1, 10), new QueryWrapper<ChatMessage>()
                .eq("s_id", BigInteger.valueOf(Long.parseLong(uid)))
                .eq("a_id", BigInteger.valueOf(Long.parseLong(friendId)))
                .or()
                .eq("s_id", BigInteger.valueOf(Long.parseLong(friendId)))
                .eq("a_id", BigInteger.valueOf(Long.parseLong(uid)))
                .orderByAsc("create_time")
        );
    }
}
