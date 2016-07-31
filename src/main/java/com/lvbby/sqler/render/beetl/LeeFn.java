package com.lvbby.sqler.render.beetl;

import com.lvbby.sqler.core.TableInfo;
import com.lvbby.sqler.util.LeeUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by lipeng on 16/7/28.
 */
public class LeeFn {
    public String capital(String s) {
        return StringUtils.capitalize(s);
    }
    public String unCapital(String s) {
        return StringUtils.uncapitalize(s);
    }

    public static String getDaoPackage(String pack) {
        return LeeUtil.concatPackage(pack, "dao");
    }

    public static String getDaoClassName(TableInfo tableInfo) {
        return tableInfo.getName() + "Mapper";
    }

    public static String getEntityPackage(String pack) {
        return LeeUtil.concatPackage(pack, "entity");
    }

    public static String getEntityClassName(TableInfo c) {
        return c.getName() + "Entity";
    }

    public static String getRepositoryPackage(String pack) {
        return LeeUtil.concatPackage(pack, "repo");
    }

    public static String getRepositoryClassName(TableInfo c) {
        return c.getName() + "Repository";
    }

    public static String getMapperXmlPackage(String pack) {
        return LeeUtil.concatPackage(pack, "mapper");
    }

    public static String getMapperXmlFileName(TableInfo tableInfo) {
        return tableInfo.getName() + "Mapper.xml";
    }

}
