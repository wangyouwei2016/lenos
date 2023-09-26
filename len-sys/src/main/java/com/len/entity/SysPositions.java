package com.len.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    public static String getColumn(String columnData) {
        if (StringUtils.isEmpty(columnData)) {
            return "";
        }
        switch (columnData) {
            case "departmentId":
                return "department_id";
            case "createDate":
                return "create_date";
            case "updateDate":
                return "update_date";
        }
        return columnData;
    }
}