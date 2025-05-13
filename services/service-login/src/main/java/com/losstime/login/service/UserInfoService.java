package com.losstime.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.losstime.login.pojo.ResultFul;
import com.losstime.login.pojo.UserInfo;

/**
 * @author 马明
 */
public interface UserInfoService extends IService<UserInfo> {
    //注册用户
    ResultFul<String> createUser(String username,String password,String pwd,String address);
}
