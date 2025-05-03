package com.losstime.login.controller;

import com.losstime.login.pojo.ResultFul;
import com.losstime.login.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 马明
 * use scope login
 */
@RestController
public class LoginController {
    //注入Login表CRUD
    @Autowired
    private LoginService loginService;
    @Autowired
    @Qualifier("resultFulString")
    private ResultFul<String> resultFul;

    @RequestMapping("/login")
    public ResultFul<HashMap<String, String>> loginIn(@RequestBody Map<String,String> map, HttpServletResponse resp){
        //是否回存cookie
        boolean rememberMe = Boolean.parseBoolean(map.get("rememberMe"));
        //第一次校验用户
        ResultFul<HashMap<String, String>> hashMapResultFul = loginService.checkUserAndPwd(map.get("username"), map.get("password"), rememberMe);
        //需要回存cookie并且此次登录成功才回写
        if (rememberMe && hashMapResultFul.getSign()){
            Cookie c1 = createCookies("username", map.get("username"));
            c1.setHttpOnly(false);
            resp.addCookie(c1);
            Cookie c2 = createCookies("uid", hashMapResultFul.getData().get("uid"));
            resp.addCookie(c2);
            Cookie c3 = createCookies("password", hashMapResultFul.getData().get("password"));
            resp.addCookie(c3);
            Cookie c4 = createCookies("userRole", hashMapResultFul.getData().get("userRole"));
            resp.addCookie(c4);
        }
        return hashMapResultFul;
    }
    //cookie登录
    //当用户有cookie的时候使用cookie登录
    @RequestMapping("/cookieLogin")
    public ResultFul<HashMap<String, String>> cookieLogin(HttpServletRequest req){
        String uid=null;
        String username=null;
        String password =null;
        Cookie[] cookies = req.getCookies();
        if (cookies==null){
            System.out.println("没有cookie，请登录");
            return new ResultFul<HashMap<String,String>>().error(false,"没有cookie，不能cookie登录");
        }
        for (Cookie cookie:cookies){
            if ("username".equals(cookie.getName())){
                username=cookie.getValue();
            }
            if ("password".equals(cookie.getName())){
                password=cookie.getValue();
            }
            if ("uid".equals(cookie.getName())){
                uid=cookie.getValue();
            }
        }
        return loginService.cookieCheckUserAndPwd(uid,username,password);
    }
    @RequestMapping("/checkToken")
    public ResultFul<String> checkToken(HttpServletRequest req){
        return loginService.checkToken(req.getHeader("token"),req.getHeader("username"));
    }
    @RequestMapping("/forgetPwd")
    public ResultFul<String> forgetPwd(@RequestBody Map<String,String> map,HttpServletRequest req,HttpServletResponse resp){
        ResultFul<String> resultFul1 = loginService.forgetPassword(map.get("uid"), map.get("username"), map.get("password"), map.get("pwd"));
        Boolean sign = resultFul1.getSign();
        if (sign){
            //如果修改成功需要做额外的操作
            //删除cookie，因为密码修改了，cookie登录失败
            for (Cookie cookie : req.getCookies()) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }
        return resultFul1;
    }
    //添加cookie
    public static Cookie createCookies(String name,String value){
        //设置cookie
        Cookie cookie = new Cookie(name, value);
        // 设置 Cookie 的作用路径（"/" 表示全局有效）
        cookie.setPath("/");
        // 有效期3天
        cookie.setMaxAge(60 * 60 * 24 * 3);
        //允许JavaScript访问
        cookie.setHttpOnly(true);
        // 设置是否仅通过 HTTPS 传输（false 表示支持 HTTP）
        cookie.setSecure(false);
        return cookie;
    }
}
