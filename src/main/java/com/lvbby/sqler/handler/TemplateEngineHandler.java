package com.lvbby.sqler.handler;

import com.google.common.collect.Maps;
import com.lvbby.codebot.chain.ContextTask;
import com.lvbby.codebot.chain.ContextTaskPipedHandler;
import com.lvbby.codema.render.TemplateEngine;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.ContextConverter;

import java.util.Map;

/**
 * Created by lipeng on 16/7/28.
 */
public class TemplateEngineHandler implements ContextTask<Context, String> {
    private TemplateEngine templateEngine;
    private Map<String, ContextConverter> handlerMap = Maps.newHashMap();

    public static TemplateEngineHandler of(TemplateEngine templateEngine) {
        TemplateEngineHandler templateEngineHandler = new TemplateEngineHandler();
        templateEngineHandler.templateEngine = templateEngine;
        return templateEngineHandler;
    }

    public TemplateEngineHandler bind(String key, ContextConverter tableHandler) {
        handlerMap.put(key, tableHandler);
        return this;
    }


    @Override
    public String process(Context context) {
        templateEngine.bind("config", context.getConfig());
        templateEngine.bind("table", context.getTableInfo());
        handlerMap.forEach((k, v) -> {
            if (handlerMap.containsKey(k))
                templateEngine.bind(k, handlerMap.get(k).handle(context));
        });
        return templateEngine.render();
    }

    public ContextTaskPipedHandler toPipeLine() {
        return new ContextTaskPipedHandler(this);
    }

}
