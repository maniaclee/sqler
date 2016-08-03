package com.lvbby.sqler.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by peng on 16/8/3.
 */
public class JdbcTypes {


    public static String java2jdbcType(String t) {
        t = StringUtils.trimToEmpty(t).toLowerCase();
        switch (t) {
            case "int":
            case "Integer":
                return "int";
            case "long":
                return "bigint";
            case "string":
                return "varchar(256)";
            case "float":
            case "bigdecimal":
                return "decimal";
            case "date":
                return "date";
            default:
                throw new RuntimeException("unknown type:" + t);
        }
    }

    public static String java2jdbcDefaultValue(String t) {
        t = StringUtils.trimToEmpty(t);
        switch (t) {
            case "int":
            case "long":
            case "bigdecimal":
            case "float":
                return "0";
            default:
                return "null";
        }
    }

    public static boolean java2jdbcNullable(String t) {
        t = StringUtils.trimToEmpty(t);
        switch (t) {
            case "int":
            case "long":
            case "bigdecimal":
            case "float":
                return false;
            default:
                return true;
        }
    }
}
