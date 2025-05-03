package com.losstime.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.losstime.login.pojo.FriendsShip;
import com.losstime.login.pojo.ResultFul;

import java.util.HashMap;
import java.util.List;

/**
 * @author 马明
 */
public interface FriendsShipService extends IService<FriendsShip> {
    //查询好友，使用Map封装
    ResultFul<HashMap<String, List<Object>>> getAllFriends(String uid);
}
