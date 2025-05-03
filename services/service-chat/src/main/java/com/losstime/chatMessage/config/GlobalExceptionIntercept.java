package com.losstime.chatMessage.config;

import com.losstime.chatMessage.pojo.ResultFul;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 马明
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionIntercept {
    @Autowired
    private ResultFul resultFul;
    // 处理绑定异常
    @ExceptionHandler(BindException.class)
    public ResultFul bindExceptionHandler(BindException e){
        log.error("出现异常"+e);
        return resultFul.error(false,e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
