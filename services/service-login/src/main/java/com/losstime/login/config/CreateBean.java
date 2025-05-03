package com.losstime.login.config;

import com.losstime.login.pojo.Login;
import com.losstime.login.pojo.ResultFul;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author 马明
 */
@Configuration
public class CreateBean {
    @Bean
    @Primary
    public ResultFul<String> resultFulString() {
        return new ResultFul<String>();
    }
    @Bean
    public ResultFul<Login> resultFulLogin() {
        return new ResultFul<Login>();
    }
}
