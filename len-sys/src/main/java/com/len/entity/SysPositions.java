package com.len.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;
import lombok.Data;

/**
 * 岗位
 */
@TableName(value = "sys_positions")
@Data
public class SysPositions extends BaseEntity {

    @TableField
    private String code;

    @TableField
    private String name;

    @TableField
    private String departmentId;

    @TableField
    private String description;

    @TableField
    private String level;
}