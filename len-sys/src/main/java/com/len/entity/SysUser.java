package com.len.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;
import com.len.validator.group.AddGroup;
import com.len.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@TableName(value = "sys_user")
@Data
public class SysUser extends BaseEntity {


    @NotEmpty(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @TableField
    private String username;

    @NotEmpty(message = "密码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @TableField
    private String password;

    @TableField
    private Integer age;

    @TableField
    private String email;

    @TableField
    private String photo;


//    private String realName;

    /**
     * 员工id
     */
    private String userEmpid;

    /**
     * 0可用1封禁
     */
    private Byte delFlag;
}