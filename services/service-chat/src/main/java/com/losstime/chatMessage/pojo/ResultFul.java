package com.losstime.chatMessage.pojo;

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
@Component
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

    public ResultFul success(Boolean sign){
        return new ResultFul(sign,null,null,1200);
    }
    public ResultFul success(Boolean sign,String message){
        return new ResultFul(sign,message,null,2200);
    }
    public ResultFul success(Boolean sign,String message,T data){
        return new ResultFul(sign,message,data,3200);
    }
    public ResultFul success(Boolean sign,String message,T data,Integer code){
        return new ResultFul(sign,message,data,code);
    }
    public ResultFul error(Boolean sign){
        return new ResultFul(sign,null,null,1400);
    }
    public ResultFul error(Boolean sign,String message){
        return new ResultFul(sign,message,null,2400);
    }
    public ResultFul error(Boolean sign,String message,T data){
        return new ResultFul(sign,message,data,3400);
    }
    public ResultFul error(Boolean sign,String message,T data,Integer code){
        return new ResultFul(sign,message,data,code);
    }
}
