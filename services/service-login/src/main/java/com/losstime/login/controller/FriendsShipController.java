package com.losstime.login.controller;

import com.losstime.login.pojo.FriendsShip;
import com.losstime.login.pojo.ResultFul;
import com.losstime.login.service.FriendsShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 马明
 */
@RestController
public class FriendsShipController {
    @Autowired
    private FriendsShipService friendsShipService;
    @RequestMapping("/getAllFriends")
    public ResultFul<HashMap<String, List<Object>>> getAllFriends(@RequestBody Map<String,String> map){
         return friendsShipService.getAllFriends(map.get("uid"));
    }
}
