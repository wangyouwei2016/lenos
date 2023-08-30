package com.len.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 岗位
 */
@TableName(value = "sys_positions")
@Data
public class SysPositions extends BaseEntity {

    @TableField
    @NotBlank(message = "编码不能为空！")
    private String code;

    @TableField
    @NotBlank(message = "岗位名称不能为空！")
    private String name;

    @TableField
    @NotBlank(message = "部门不能为空！")
    private String departmentId;

    @TableField
    private String description;

    @TableField
    private Integer level;
}