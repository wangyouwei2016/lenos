package com.len.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurrentMenu implements Serializable {
    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String pId;

    private String url;

    private Integer orderNum;

    private String icon;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String permission;

    private Byte menuType;
    /** 菜单排序id 填充菜单展示id */
    private String code;

    public CurrentMenu(String id, String name, String pId, String url, Integer orderNum, String icon, String permission,
        Byte menuType, String code) {
        this.id = id;
        this.name = name;
        this.pId = pId;
        this.url = url;
        this.orderNum = orderNum;
        this.icon = icon;
        this.permission = permission;
        this.menuType = menuType;
        this.code = code;
    }

    public CurrentMenu() {}
}
