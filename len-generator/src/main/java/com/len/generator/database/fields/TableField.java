package com.len.generator.database.fields;

import lombok.Data;

/**
 * 表字段对象
 */
@Data
public class TableField {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否允许为空
     */
    private boolean isNull;

    /**
     * 长度
     */
    private String length;

    /**
     * 是否为主键
     */
    private boolean isPrimaryKey;
}
