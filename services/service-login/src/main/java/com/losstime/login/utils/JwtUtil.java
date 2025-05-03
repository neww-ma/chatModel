package com.losstime.login.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
/**
 * @author 马明
 */
@Component
public class JwtUtil {
    public String getJwt(String username, String password,String uid,String userRole){
        JwtBuilder builder = Jwts.builder();
        //设置Header
        return  builder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("username", username)
                .claim("role", userRole)
                .claim("password", password)
                //十分钟过期
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60*10))
                .setId(uid)
                //签名signature
                //加密算法和密钥
                .signWith(SignatureAlgorithm.HS256, username)
                //启动连接三部分
                .compact();

    }
    public Claims tokenGetClaims(String token,String username){
        Claims body =null;
        try {
            //不抛出异常说明token存在，则token校验成功
            //反之，抛出异常说明token有问题，校验失败
            JwtParser parser = Jwts.parser();
            Jws<Claims> claimsJws = parser.setSigningKey(username).parseClaimsJws(token);
           return claimsJws.getBody();
        }catch (Exception e){
            System.out.println("tokenGetClaims异常："+e.getMessage());
            return body;
        }
    }
}