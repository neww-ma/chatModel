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

/**
 * @author 马明
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ChatMessage {
    @TableId(type = IdType.AUTO)
    private BigInteger id;
    @NotBlank(message = "发送者的ID不能为空")
    private BigInteger sId;
    @NotBlank(message = "接收者的ID不能为空")
    private BigInteger aId;
    @NotBlank(message = "发送者的名字不能为空")
    private String sName;
    @NotBlank(message = "接收者的名字不能为空")
    private String aName;
    @NotBlank(message = "发送的信息不能为空")
    @Length(min = 1,max = 32,message = "数据长度应该在1~32之间")
    private String message;
    //自动填充 -- mybatisPlus做
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    //逻辑删除注解
    @TableLogic
    private Integer deleted;
}
