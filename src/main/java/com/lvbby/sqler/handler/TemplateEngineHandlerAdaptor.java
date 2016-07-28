package com.lvbby.sqler.handler;

import com.google.common.collect.Maps;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.ContextConverter;
import com.lvbby.sqler.core.ResultMapConst;
import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.render.TemplateEngine;

import java.util.Map;

/**
 * Created by lipeng on 16/7/28.
 */
public class TemplateEngineHandlerAdaptor implements TableHandler {
    private TemplateEngine templateEngine;
    private Map<String, ContextConverter> handlerMap = Maps.newHashMap();

    public static TemplateEngineHandlerAdaptor of(TemplateEngine templateEngine) {
        TemplateEngineHandlerAdaptor templateEngineHandlerAdaptor = new TemplateEngineHandlerAdaptor();
        templateEngineHandlerAdaptor.templateEngine = templateEngine;
        return templateEngineHandlerAdaptor;
    }

    public TemplateEngineHandlerAdaptor bind(String key, ContextConverter tableHandler) {
        handlerMap.put(key, tableHandler);
        return this;
    }


    @Override
    public void handle(Context context) {
        templateEngine.bind("config", context.getConfig());
        templateEngine.bind("table", context.getTableInfo());
        handlerMap.forEach((k, v) -> {
            if (handlerMap.containsKey(k))
                templateEngine.bind(k, handlerMap.get(k).handle(context));
        });
        String re = templateEngine.render();
        System.out.println(re);
        context.store(ResultMapConst.OUTPUT, re);
    }
}
