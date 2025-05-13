package com.losstime.login.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * @author 马明
 * use scope 全局返回模板
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ResultFul<T> {
    private Boolean sign;
    private String message;
    private T data;
    private Integer code;

    public ResultFul<T> success(Boolean sign){
        return new ResultFul<T>(sign,null,null,1200);
    }
    public ResultFul<T> success(Boolean sign,String message){
        return new ResultFul<T>(sign,message,null,2200);
    }
    public ResultFul<T> success(Boolean sign,String message,T data){
        return new ResultFul<T>(sign,message,data,3200);
    }
    public ResultFul<T> success(Boolean sign,String message,T data,Integer code){
        return new ResultFul<T>(sign,message,data,code);
    }
    public ResultFul<T> error(Boolean sign){
        return new ResultFul<T>(sign,null,null,1400);
    }
    public ResultFul<T> error(Boolean sign,String message){
        return new ResultFul<T>(sign,message,null,2400);
    }
    public ResultFul<T> error(Boolean sign,String message,T data){
        return new ResultFul<T>(sign,message,data,3400);
    }
    public ResultFul<T> error(Boolean sign,String message,T data,Integer code){
        return new ResultFul<T>(sign,message,data,code);
    }
}
