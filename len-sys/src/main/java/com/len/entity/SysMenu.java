package com.len.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TableName("sys_menu")
@Data
@ToString
@EqualsAndHashCode
public class SysMenu extends AbstractEntity {


    private String name;

    private String pId;

    private String url;

    /**
     * 排序字段
     */
    private Integer orderNum;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限
     */
    private String permission;

    /**
     * 1栏目2菜单
     */
    private Byte menuType;

    @TableField(exist = false)
    private int num;

    @TableField(exist = false)
    private List<SysRole> roleList;

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public void addChild(SysMenu sysMenu) {
        children.add(sysMenu);
    }
}