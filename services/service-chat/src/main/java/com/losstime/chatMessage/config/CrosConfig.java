package com.losstime.chatMessage.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//解决前后端跨域配置
//@Configuration
public class CrosConfig implements WebMvcConfigurer {
    // 重写addCorsMappings方法，用于配置跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加映射，允许所有路径
        registry.addMapping("/**")
                // 允许所有来源
                .allowedOriginPatterns("http://localhost:8080","http://27.0.0.1:8080")
                // 允许所有请求方法
                .allowedMethods("GET", "POST", "PUT")
                // 允许发送Cookie
                .allowCredentials(true)
                // 预检请求的缓存时间
                .maxAge(3600)
                // 允许所有请求头
                .allowedHeaders("*");
    }
}
