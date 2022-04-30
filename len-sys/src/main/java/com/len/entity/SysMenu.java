package com.len.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@Data
@ToString
public class SysMenu extends BaseEntity implements Serializable {

    /**
     * 编码
     */
    private String code;

    private String name;

    private String pId;

    private String url;

    /**
     * 路由
     */
    private String router;

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
    private List<SysRole> roleList;

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public void addChild(SysMenu sysMenu) {
        children.add(sysMenu);
    }

}