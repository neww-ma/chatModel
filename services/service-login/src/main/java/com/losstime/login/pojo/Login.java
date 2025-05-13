package com.losstime.login.pojo;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author 马明
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Login {
    @TableId(type = IdType.AUTO)
    private BigInteger uid;
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5,max = 64,message = "用户名长度应该在5~64个字符之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 5,max = 64,message = "密码长度应该在5~64个字符之间")
    private String password;
    //自动填充 -- mybatisPlus做
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    //自动填充 -- mybatisPlus做
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    //逻辑删除注解
    @TableLogic
    private int deleted;
    private String lastIp;
    private String loginToken;
}
