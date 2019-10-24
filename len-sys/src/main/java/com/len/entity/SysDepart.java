package com.len.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sys_depart")
@Data
public class SysDepart {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 部门名称
     */
    @Column(name = "depart_name")
    private String departName;
}