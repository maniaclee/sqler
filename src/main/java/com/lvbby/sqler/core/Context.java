package com.lvbby.sqler.core;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by lipeng on 16/7/28.
 */
public class Context {
    TableInfo tableInfo;
    Config config;
    Map<String, Object> resultMap = Maps.newHashMap();

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


    public void clear() {
        resultMap.clear();
    }

    public void store(String k, Object v) {
        resultMap.put(k, v);
    }

    public Object queryResult(String k) {
        return resultMap.get(k);
    }
}
