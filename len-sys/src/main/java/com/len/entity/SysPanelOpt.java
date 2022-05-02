package com.len.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.len.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 面板配置
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_panelopt")
public class SysPanelOpt extends BaseEntity {

    /**
     * 列
     */
    private Integer paneloptColumn;

    /**
     * 列顺序
     */
    private Integer paneloptIndex;

    /**
     * 编号
     */
    private String paneloptCode;

    /**
     * 用户id
     */
    private String paneloptUserid;

}
