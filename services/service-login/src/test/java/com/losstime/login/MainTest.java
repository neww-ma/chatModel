package com.losstime.login;

import com.losstime.login.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class MainTest {
    @Test
    void testSHA256(){
        String input = "123456";
        try {
            // 获取 SHA-256 实例
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 更新输入数据并计算哈希值
            md.update(input.getBytes());
            byte[] digest = md.digest();

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            // 输出 SHA-256 哈希值
            System.out.println("Input: " + input);
            System.out.println("SHA-256 Hash: " + hexString.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testJwt(){
        JwtUtil util = new JwtUtil();
        Claims claims = util.tokenGetClaims("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicm9sZSI6ImNvbnN1bWVyIiwicGFzc3dvcmQiOiI4ZDk2OWVlZjZlY2FkM2MyOWEzYTYyOTI4MGU2ODZjZjBjM2Y1ZDVhODZhZmYzY2ExMjAyMGM5MjNhZGM2YzkyIiwiZXhwIjoxNzQ1NjgzNDE5LCJqdGkiOiIxIn0.XcNYudtLn2trMbo6utauvUm1_FDvUWm8d2hLwp3o8y4", "admin");
        System.out.println(claims.get("username"));
    }

}
