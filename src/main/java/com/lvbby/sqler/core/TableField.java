package com.lvbby.sqler.core;

/**
 * Created by peng on 16/7/27.
 */
public class TableField {
    private String name;
    private String appType;
    private String  dbType;
    private String dbTypeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbTypeName() {
        return dbTypeName;
    }

    public void setDbTypeName(String dbTypeName) {
        this.dbTypeName = dbTypeName;
    }

    @Override
    public String toString() {
        return "TableField{" +
                "name='" + name + '\'' +
                ", appType='" + appType + '\'' +
                ", dbType='" + dbType + '\'' +
                '}';
    }
}
