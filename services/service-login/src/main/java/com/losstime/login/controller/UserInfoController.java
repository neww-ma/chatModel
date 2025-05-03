package com.losstime.login.controller;

import com.losstime.login.pojo.ResultFul;
import com.losstime.login.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 马明
 */
@RestController
public class UserInfoController {
    @Autowired
    private ResultFul<String> resultFulString;
    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping("/register")
    public ResultFul<String> register(@RequestBody Map<String,String> map, HttpServletRequest req){
        return userInfoService.createUser(map.get("username"), map.get("password"), map.get("pwd"), req.getRemoteAddr());
    }

}
