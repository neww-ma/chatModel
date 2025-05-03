package com.losstime.login.service.imp;
import java.time.LocalDateTime;
import java.math.BigInteger;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.losstime.login.dao.LoginMapper;
import com.losstime.login.dao.UserInfoMapper;
import com.losstime.login.pojo.Login;
import com.losstime.login.pojo.ResultFul;
import com.losstime.login.pojo.UserInfo;
import com.losstime.login.service.LoginService;
import com.losstime.login.service.UserInfoService;
import com.losstime.login.utils.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author 马明
 */
@Service
public class UserInfoServiceImp extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    //注入Login表的操作
    @Autowired
    private LoginService loginService;
    //注入Login表的源生SQL操作
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    @Qualifier("resultFulString")
    private ResultFul<String> resultFul;
    @Autowired
    @Qualifier("resultFulLogin")
    private ResultFul<Login> resultFulLogin;

    //注入编程式事务
    @Autowired
    private TransactionTemplate transactionTemplate;
    //注入sha-256算法
    @Autowired
    private Sha256Util sha256;
    @Override
    public ResultFul<String> createUser(String username, String password, String pwd,String address) {
        //先检查用户是否已经存在
        Login login = loginService.selectUserByName(username);
        if (login!=null){
            //用户已经存在了，注册失败
            return resultFul.error(false,"用户已经存在，不能完成注册");
        }
        //再次校验两次密码是否一致
        if (!password.equals(pwd)){
            //密码不一致
            return resultFul.error(false,"两次密码不匹配，请修改");
        }
        //可以开始注册了
        //try语句块将异常捕获掉，避免程序异常
        //插入Login表
        //插入UserInfo表
        //事务回滚
        return transactionTemplate.execute((status) -> {
            //try语句块将异常捕获掉，避免程序异常
            try {
                //插入Login表
                Login loginUser = new Login();
                loginUser.setUsername(username);
                loginUser.setPassword(sha256.addSha(password));
                loginUser.setLastIp(address);
                loginMapper.insert(loginUser);
                //插入UserInfo表
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername(username);
                save(userInfo);
            } catch (Exception e) {
                //事务回滚
                status.setRollbackOnly();
                System.out.println("发生：" + e.getMessage() + "异常，程序被迫终止");
                return resultFul.success(false,"插入数据失败，不能完成用户注册");
            }
            return resultFul.success(true,"创建用户成功");
        });
    }

}
