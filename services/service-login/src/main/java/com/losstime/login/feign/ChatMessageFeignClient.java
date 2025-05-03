package com.losstime.login.feign;

import com.losstime.login.pojo.ChatMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 马明
 */
@FeignClient(value = "server-chat")
public interface ChatMessageFeignClient {
    @RequestMapping("/cm/selectFriendMessage/{uid}/{friendId}")
    List<ChatMessage> selectFriendMessage(@PathVariable("uid") String uid, @PathVariable("friendId") String friendId);
}
