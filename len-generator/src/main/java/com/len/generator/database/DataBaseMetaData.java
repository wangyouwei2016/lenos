package com.len.generator.database;

import java.util.Map;

import com.len.generator.metadata.StandardMetaData;

/**
 * 数据库元数据实现 入参->定义到config 出参-> 数据表字段集合信息 组成的元数据
 */
public class DataBaseMetaData extends StandardMetaData {

    private DataBaseConfig dataBaseConfig;
    private TableStructureConfig tableStructure;

    /**
     * jdbc配置方向
     * 
     * @param dataBaseConfig
     */
    public DataBaseMetaData(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    /**
     * 表结构字符串方向
     * 
     * @param tableStructure 表结构
     */
    public DataBaseMetaData(TableStructureConfig tableStructure) {
        this.tableStructure = tableStructure;

    }

    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> allVar = super.getAll();
        // allVar.put("database..",);
        return allVar;
    }
}
