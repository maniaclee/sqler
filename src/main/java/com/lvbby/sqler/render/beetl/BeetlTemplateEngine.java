package com.lvbby.sqler.render.beetl;

import com.lvbby.sqler.render.TemplateEngine;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;

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
            t = gt.getTemplate(context);
        } catch (IOException e) {
            throw new RuntimeException("error create template ", e);
        }
    }

    public static BeetlTemplateEngine create(String context) {
        return new BeetlTemplateEngine(context);
    }

    @Override
    public void bind(String key, Object obj) {
        t.binding(key, obj);
    }

    @Override
    public String render() {
        return t.render();
    }


}
