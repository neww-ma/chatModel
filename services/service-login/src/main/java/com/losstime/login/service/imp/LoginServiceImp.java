package com.losstime.login.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.losstime.login.dao.LoginMapper;
import com.losstime.login.dao.UserInfoMapper;
import com.losstime.login.pojo.Login;
import com.losstime.login.pojo.ResultFul;
import com.losstime.login.pojo.UserInfo;
import com.losstime.login.service.LoginService;
import com.losstime.login.utils.JwtUtil;
import com.losstime.login.utils.Sha256Util;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author 马明
 */
@Service
public class LoginServiceImp extends ServiceImpl<LoginMapper, Login> implements LoginService {
    //注入userInfo的Mapper
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    @Qualifier("resultFulString")
    private ResultFul<String> resultFul;
    @Autowired
    private Sha256Util sha256;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResultFul<HashMap<String, String>> checkUserAndPwd(String username, String password, Boolean rememberMe) {
        Login login = this.selectUserByName(username);
        if (login==null){
            return new ResultFul<HashMap<String, String>>().error(false,"用户已经存在不能完成创建");
        }
        //加密密码，校对密码是否一直
        String encryptPwd = sha256.addSha(password);
        if (!encryptPwd.equals(login.getPassword())){
            //打包登录数据返回
            return new ResultFul<HashMap<String, String>>().error(false,"密码校验失败");
        }
        //生成Jwt
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("uid", login.getUid()));
        String jwt = jwtUtil.getJwt(username, encryptPwd, login.getUid().toString(),userInfo.getUserRole());
        HashMap<String, String> map = new HashMap<>();
        map.put("toke",jwt);
        map.put("uid",login.getUid().toString());
        map.put("username",username);
        map.put("password",encryptPwd);
        map.put("userRole",userInfo.getUserRole());

        return new ResultFul<HashMap<String, String>>().success(true,"校验成功",map);
    }

    //TODO 引入Redis缓存
    @Override
    public Login selectUserByName(String username) {
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        return this.getOne(wrapper.eq("username", username));
    }

    @Override
    public ResultFul<HashMap<String, String>> cookieCheckUserAndPwd(String uid,String username, String password) {
        Login login = this.selectUserByUid(BigInteger.valueOf(Long.parseLong(uid)));
        if (username.equals(login.getUsername()) && password.equals(login.getPassword())){
            //刷新JWT
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("uid", login.getUid()));
            String jwt = jwtUtil.getJwt(username, password, login.getUid().toString(),userInfo.getUserRole());
            HashMap<String, String> map = new HashMap<>();
            map.put("token",jwt);
            map.put("username",username);
            map.put("password",password);
            map.put("uid",login.getUid().toString());
            map.put("userRole",userInfo.getUserRole());
            return new ResultFul<HashMap<String, String>>().success(true,"登录成功",map);
        }
        return new ResultFul<HashMap<String, String>>().error(false,"cookie登录失败");
    }
    //TODO 引入Redis缓存
    @Override
    public Login selectUserByUid(BigInteger uid) {
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        return this.getOne(wrapper);
    }

    @Override
    public ResultFul<String> checkToken(String token, String username) {
        Claims claims = jwtUtil.tokenGetClaims(token, username);
        if (claims==null){
            System.out.println("token解析为null");
            return new ResultFul<String>().success(false,"Token校验失败");
        }
        String uName = (String) claims.get("username");
        String pwd = (String) claims.get("password");
        //后面会引入Redis，校验token会依赖于缓存
        Login login = this.selectUserByName(username);
        if (login.getUsername().equals(uName) && login.getPassword().equals(pwd)){
            return new ResultFul<String>().success(true,"Token校验成功");
        }
        return new ResultFul<String>().success(false,"Token校验失败");
    }

    @Override
    public ResultFul<String> forgetPassword(String uid, String username, String password, String pwd) {
        Login login = this.selectUserByUid(BigInteger.valueOf(Long.parseLong(uid)));
        if (login==null){
            return new ResultFul<String>().error(false,"用户名不存在，无法修改密码");
        }
        //校验密码
        if (!password.equals(pwd)){
            return new ResultFul<String>().error(false,"密码不匹配，不能完成修改");
        }
        //修改密码
        login.setPassword(sha256.addSha(password));
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        wrapper.eq("uid",uid);
        boolean result = this.update(login, wrapper);
        if (result){
            return new ResultFul<String>().success(true,"修改成功，需要重新登录",null);
        }
        return new ResultFul<String>().error(false,"修改失败，数据库更新异常");
    }
}
