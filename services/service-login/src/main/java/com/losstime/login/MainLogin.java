package com.losstime.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 马明
 * use scope login server
 */
@EnableFeignClients
@SpringBootApplication
public class MainLogin {
    public static void main(String[] args) {
        SpringApplication.run(MainLogin.class,args);
    }
}
