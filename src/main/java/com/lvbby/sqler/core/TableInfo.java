package com.lvbby.sqler.core;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Objects;

/**
 * Created by peng on 16/7/27.
 */
public class TableInfo {
    /**
     * Capital camel name like UserDetail
     */
    String name;
    String nameInDb;
    List<TableField> fields = Lists.newLinkedList();
    String primaryKeyColumn;
    private TableField primaryKeyField;//TODO

    public static TableInfo instance(String table) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setNameInDb(table);
        return tableInfo;
    }

    public TableField findPrimaryKey(){
        return fields.stream().filter(f -> Objects.equals(f.getNameInDb(), primaryKeyColumn)).findFirst().orElse(null);
    }
    public String getPrimaryKeyColumn() {
        return primaryKeyColumn;
    }

    public void setPrimaryKeyColumn(String primaryKeyColumn) {
        this.primaryKeyColumn = primaryKeyColumn;
    }

    public String getNameInDb() {
        return nameInDb;
    }

    public void setNameInDb(String nameInDb) {
        this.nameInDb = nameInDb;
    }

    public String getName() {
        return name;
    }

    public void setName(String tableName) {
        this.name = tableName;
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
