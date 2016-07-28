package com.lvbby.sqler.render;

import org.beetl.core.Template;

import java.io.OutputStream;
import java.util.Map;

/**
 * Created by lipeng on 16/7/28.
 */
public class BeetlTemplateEngine implements ITemplateEngine {
    Template t;

    @Override
    public String render(String key, Object obj) {
        return null;
    }

    @Override
    public String render(Map obj) {
        return null;
    }

    @Override
    public void renderTo(OutputStream outputStream) {

    }
}
