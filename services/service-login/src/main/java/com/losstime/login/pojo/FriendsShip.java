package com.losstime.login.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigInteger;


/**
 * @author 马明
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class FriendsShip {
    private BigInteger userId;
    private BigInteger friendId;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    private FriendsShip status;
}
