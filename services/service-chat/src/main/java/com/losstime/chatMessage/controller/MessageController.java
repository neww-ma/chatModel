package com.losstime.chatMessage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.losstime.chatMessage.dao.ChatMessageMapper;
import com.losstime.chatMessage.pojo.ChatMessage;
import com.losstime.chatMessage.pojo.ResultFul;
import com.losstime.chatMessage.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author 马明
 */
@RestController
@CrossOrigin
@RequestMapping("/cm")
public class MessageController {
    @Autowired
    private ResultFul resultFul;
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private ChatMessageService chatMessageService;
    //进行数据信息插入
    @RequestMapping("/insertMessage")
    public ResultFul<String> insertMessage(@RequestBody Map<String,String> map){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(map.get("message"));
        //转换字符型到bigInteger
        chatMessage.setSId(BigInteger.valueOf(Long.parseLong(map.get("sId"))));
        chatMessage.setAId(BigInteger.valueOf(Long.parseLong(map.get("aId"))));
        chatMessage.setSName(map.get("sName"));
        chatMessage.setAName(map.get("aName"));
        int flag = chatMessageMapper.insert(chatMessage);
        if (flag>0){
            System.out.println("插入成功");
            return resultFul.success(true,"插入成功");
        }
        return resultFul.error(false,"插入失败");
    }
    //根据用户用户ID或者用户名查询
    @RequestMapping("/selectChatMessage")
    public ResultFul selectChatMessage(@RequestBody Map<String,String> map){
        String sId = map.get("sId");
        String aId = map.get("aId");
        String sName = map.get("sName");
        String aName = map.get("aName");
        QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
        //where （（sId = sid or sName =sName） and （aId = aId or aName = aName））||（（sId = aid or sName =aName） and （aId = sId or aName = sName））
        wrapper.and(wrapper1 -> wrapper1
                .and(w -> w.eq("s_id", sId).or().eq("s_name", sName)) // 第一个括号 (sId = sid OR sName = sName)
                .and(w -> w.eq("a_id", aId).or().eq("a_name", aName)) // 第二个括号 (aId = aId OR aName = aName)
        ).or(wrapper1 -> wrapper1
                .and(w -> w.eq("s_id", aId).or().eq("s_name", aName)) // 第三个括号 (sId = aid OR sName = aName)
                .and(w -> w.eq("a_id", sId).or().eq("a_name", sName)) // 第四个括号 (aId = sId OR aName = sName)
        );
        List<ChatMessage> chatMessages = chatMessageMapper.selectList(wrapper);
        System.out.println(chatMessages);
        return resultFul.success(true,"查询成功，总记录数："+chatMessages.size(),chatMessages);
    }
    @RequestMapping("/selectFriendMessage/{uid}/{friendId}")
    public List<ChatMessage> selectFriendMessage(@PathVariable("uid") String uid, @PathVariable("friendId") String friendId){
        return chatMessageService.selectMsgByUidAndFid(uid,friendId);
    }
}
