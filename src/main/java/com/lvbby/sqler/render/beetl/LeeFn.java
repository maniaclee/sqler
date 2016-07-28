package com.lvbby.sqler.render.beetl;

import com.google.common.base.CaseFormat;

/**
 * Created by lipeng on 16/7/28.
 */
public class LeeFn {

    public String capital(String s) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
    }

}
