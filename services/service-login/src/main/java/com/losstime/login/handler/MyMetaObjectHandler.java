package com.losstime.login.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 马明
 * use scope mybatis-plus 自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("执行插入填充------");
        // 创建日期格式化器对象
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //获取当前系统时间
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("createTime",pattern.format(now),metaObject);
        this.setFieldValByName("userRole","consumer",metaObject);
        this.setFieldValByName("updateTime",pattern.format(now),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("执行修改填充------");
        // 创建日期格式化器对象
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //获取当前系统时间
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("updateTime",pattern.format(now),metaObject);
    }
}
