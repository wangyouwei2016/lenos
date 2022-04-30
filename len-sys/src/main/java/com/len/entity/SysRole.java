package com.len.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;
import com.len.validator.group.AddGroup;
import com.len.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@TableName(value = "sys_role")
@Data
@ToString
@EqualsAndHashCode
public class SysRole extends BaseEntity {

    @NotEmpty(message = "角色名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String roleName;

    private String remark;
}