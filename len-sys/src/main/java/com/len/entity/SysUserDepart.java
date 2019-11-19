package com.len.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "sys_user_depart")
public class SysUserDepart {
    /**
     * 用户id
     */
    @TableId(type = IdType.UUID, value = "user_id")
    private String userId;

    /**
     * 部门id
     */
    @TableId(type = IdType.UUID, value = "depart_id")
    private String departId;

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取部门id
     *
     * @return depart_id - 部门id
     */
    public String getDepartId() {
        return departId;
    }

    /**
     * 设置部门id
     *
     * @param departId 部门id
     */
    public void setDepartId(String departId) {
        this.departId = departId == null ? null : departId.trim();
    }
}