package com.len.generator.database;

/**
 * 表结构
 */
public class TableStructureConfig {

    private String tableStructure;
    private DatabaseType databaseType;

    public String getTableStructure() {
        return tableStructure;
    }

    public void setTableStructure(String tableStructure) {
        this.tableStructure = tableStructure;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }
}
