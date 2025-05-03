package com.losstime.login.service.imp;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.losstime.login.dao.FriendsShipMapper;
import com.losstime.login.feign.ChatMessageFeignClient;
import com.losstime.login.pojo.ChatMessage;
import com.losstime.login.pojo.FriendsShip;
import com.losstime.login.pojo.Login;
import com.losstime.login.pojo.ResultFul;
import com.losstime.login.service.FriendsShipService;
import com.losstime.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author 马明
 */
@Service
public class FriendsShipServiceImp extends ServiceImpl<FriendsShipMapper, FriendsShip> implements FriendsShipService {
    @Autowired
    private LoginService loginService;
    //远程调用查询信息
    @Autowired
    private ChatMessageFeignClient chatMessageFeignClient;
    @Override
    public ResultFul<HashMap<String, List<Object>>> getAllFriends(String uid) {
        //返回三个List
        //List 装载好友的ID，好友的名字，以及第一个好友的前十条聊天信息
        HashMap<String, List<Object>> map = new HashMap<>();
        //所有朋友的的关系信息
        List<FriendsShip> friendsShips = this.selectAll(uid);
        //所有朋友的Id
        ArrayList<BigInteger> friendIdList = new ArrayList<>();
        //所有的朋友名字
        ArrayList<String> friendNameList = new ArrayList<>();
        friendsShips.forEach(friend->{
            BigInteger friendId = friend.getFriendId();
            Login login = loginService.selectUserByUid(friendId);
            if (login!=null){
                friendIdList.add(login.getUid());
                friendNameList.add(login.getUsername());
            }
        });
        ArrayList<initMessage> msgList = new ArrayList<>();
        if (!friendIdList.isEmpty()){
            List<ChatMessage> chatMessages = chatMessageFeignClient.selectFriendMessage(uid, friendIdList.get(0).toString());
            chatMessages.forEach(message->{
                msgList.add(new initMessage(message.getSId(), message.getMessage(), message.getCreateTime(), message.getSName()));
            });
        }
        map.put("allId", Collections.singletonList(friendIdList));
        map.put("allName", Collections.singletonList(friendNameList));
        map.put("allMessage", Collections.singletonList(msgList));
        return new ResultFul<HashMap<String, List<Object>>>().success(true,"查询成功",map);
    }
    //根据Uid查询所有的好友
    //TODO 引入redis
    public List<FriendsShip> selectAll(String uid){
        return this.list(new QueryWrapper<FriendsShip>().eq("user_id", BigInteger.valueOf(Long.parseLong(uid))));
    }
    class initMessage{
        private BigInteger id;
        private String content;
        private String time;
        private String sender;

        public initMessage(BigInteger id, String content, String time, String sender) {
            this.id = id;
            this.content = content;
            this.time = time;
            this.sender = sender;
        }

        public BigInteger getId() {
            return id;
        }

        public void setId(BigInteger id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        @Override
        public String toString() {
            return "initMessage{" +
                    "id=" + id +
                    ", content='" + content + '\'' +
                    ", time='" + time + '\'' +
                    ", sender='" + sender + '\'' +
                    '}';
        }
    }
}
