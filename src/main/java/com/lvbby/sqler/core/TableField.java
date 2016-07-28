package com.lvbby.sqler.core;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by peng on 16/7/27.
 */
public class TableField {
    private String name;
    private String appType;
    private String  dbType;
    private String dbTypeName;
    private String comment;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
