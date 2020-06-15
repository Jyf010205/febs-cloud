package com.sgzs.febs.common.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/12 18:21
 */
@Data
@TableName("t_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 4730348067229388833L;

    @TableField(value = "USER_ID")
    private long userId;

    @TableField(value = "ROLE_ID")
    private long roleId;
}
