package com.lvbby.sqler.core;

/**
 * Created by lipeng on 16/7/28.
 */
public class Context {
    TableInfo tableInfo;
    Config config;

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
