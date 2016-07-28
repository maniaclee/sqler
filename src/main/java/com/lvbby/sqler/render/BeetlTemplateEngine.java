package com.lvbby.sqler.render;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by lipeng on 16/7/28.
 */
public class BeetlTemplateEngine implements TemplateEngine {
    Template t;


    private BeetlTemplateEngine(String context) {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            t = gt.getTemplate("hello,${name}");
        } catch (IOException e) {
            throw new RuntimeException("error create template ", e);
        }
    }

    public static BeetlTemplateEngine create(String context) {
        return new BeetlTemplateEngine(context);
    }

    @Override
    public String render(String key, Object obj) {
        t.binding(key, obj);
        return t.render();
    }

    @Override
    public String render(Map obj) {
        t.binding(obj);
        return t.render();
    }

    @Override
    public void renderTo(OutputStream outputStream) {
        t.renderTo(outputStream);
    }
}
