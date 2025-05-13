package com.losstime.login.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 马明
 * use scope 加密密码
 */
@Component
public class Sha256Util {
    public String addSha(String password){
        StringBuilder hexString=null;
        try {
            // 获取 SHA-256 实例
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 更新输入数据并计算哈希值
            md.update(password.getBytes());
            byte[] digest = md.digest();

            // 将字节数组转换为十六进制字符串
            hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            // 输出 SHA-256 哈希值
            System.out.println("SHA-256 Hash: " + hexString);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Method：Sha256Util：function：addSHA--"+e.getMessage());
        }
        return hexString.toString();
    }
}
