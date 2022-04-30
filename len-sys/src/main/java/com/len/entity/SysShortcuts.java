package com.len.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_shortcuts")
public class SysShortcuts extends BaseEntity {

    /**
     * 用户id
     */
    private String shortcutsUserid;

    /**
     * 菜单id
     */
    private String shortcutsMenuid;

}
