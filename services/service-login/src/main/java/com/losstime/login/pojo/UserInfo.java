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
@TableName("user_info")
public class UserInfo {
    @TableId(type = IdType.AUTO)
    private BigInteger uid;
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5,max = 64,message = "用户名长度应该在5~64个字符之间")
    private String username;
    @NotBlank(message = "昵称不能为空")
    private String nikeName;
    @NotBlank(message = "昵称")
    private String sex;
    @NotBlank(message = "座右铭不能为空")
    private String motto;
    @NotBlank(message = "日期不能为空")
    private String birthday;
    //自动填充 -- mybatisPlus做
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    private int activity;
    //逻辑删除注解
    @TableLogic
    private int deleted;
    @TableField(fill = FieldFill.INSERT)
    private String userRole;
}
