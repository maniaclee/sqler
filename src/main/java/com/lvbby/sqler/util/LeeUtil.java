package com.lvbby.sqler.util;

import com.lvbby.sqler.core.Config;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by peng on 16/7/28.
 */
public class LeeUtil {

    public static void doCheck(boolean isTrue, String message) {
        if (!isTrue)
            throw new IllegalArgumentException(message);
    }

    public static String package2path(String pack) {
        if (StringUtils.isEmpty(pack))
            return "";
        validatePackage(pack);
        pack = pack.trim().replaceAll("\\.", "/");
        if (pack.startsWith("/") && pack.length() > 1)
            return pack.substring(1);
        return pack;
    }

    public static String concatPackage(String pack, String subPackage) {
        validatePackage(pack);
        validatePackage(subPackage);
        if (StringUtils.isBlank(subPackage))
            return pack;
        if (StringUtils.isBlank(pack))
            return subPackage;
        return pack + "." + subPackage;
    }

    public static void validatePackage(String pack) {
        doCheck(StringUtils.isBlank(pack) || pack.matches("^([a-zA-Z_])[a-zA-Z0-9_\\-\\.]*([^\\.])$"), "invalid package:" + pack);
    }

    public static File calDirectory(Config config, String pack) {
        return new File(config.getRootPath(), package2path(pack));
    }

}
