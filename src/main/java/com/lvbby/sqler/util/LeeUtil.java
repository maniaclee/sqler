package com.lvbby.sqler.util;

/**
 * Created by peng on 16/7/28.
 */
public class LeeUtil {

    public static void doCheck(boolean isTrue, String message) {
        if (!isTrue)
            throw new IllegalArgumentException(message);
    }
}
