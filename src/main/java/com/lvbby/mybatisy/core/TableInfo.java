package com.lvbby.mybatisy.core;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by peng on 16/7/27.
 */
public class TableInfo {
    String tableName;
    List<TableField> fields;

    public static TableInfo instance(String table) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(table);
        return tableInfo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableField> getFields() {
        return fields;
    }

    public void setFields(List<TableField> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
