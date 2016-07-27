package com.lvbby.mybatisy.core;

/**
 * Created by peng on 16/7/27.
 */
public class TableField {
    private String name;
    private String type;
    private String dbType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public String toString() {
        return "TableField{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dbType='" + dbType + '\'' +
                '}';
    }
}
