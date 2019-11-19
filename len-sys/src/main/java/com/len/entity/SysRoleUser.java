package com.len.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_role_user")
public class SysRoleUser {

    @TableId(type = IdType.UUID)
    private String id;

    private String userId;


    private String roleId;
}