package com.losstime.login.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.losstime.login.pojo.Login;
import com.losstime.login.pojo.ResultFul;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author 马明
 */

public interface LoginService extends IService<Login> {
    //用户名和密码登录
    ResultFul<HashMap<String, String>> checkUserAndPwd(String username, String password,Boolean rememberMe);
    //使用username查询Login表
    Login selectUserByName(String username);
    //cookie登录
    ResultFul<HashMap<String, String>> cookieCheckUserAndPwd(String uid,String username, String password);
    //使用Uid查询Login表
    Login selectUserByUid(BigInteger uid);
    //校验Token
    ResultFul<String> checkToken(String token,String username);
    //忘记密码--修改密码
    ResultFul<String> forgetPassword(String uid,String username,String password,String pwd);
}
