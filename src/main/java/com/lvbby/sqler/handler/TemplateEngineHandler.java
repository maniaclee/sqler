package com.lvbby.sqler.handler;

import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.render.TemplateEngine;

/**
 * Created by lipeng on 16/7/28.
 */
public class TemplateEngineHandler implements TableHandler<Context> {
    private TemplateEngine templateEngine;
    private String rootKey = "c";

    public static TemplateEngineHandler of(TemplateEngine templateEngine) {
        TemplateEngineHandler templateEngineHandler = new TemplateEngineHandler();
        templateEngineHandler.templateEngine = templateEngine;
        return templateEngineHandler;
    }

    public TemplateEngineHandler setRootKey(String rootKey) {
        this.rootKey = rootKey;
        return this;
    }

    @Override
    public void handle(Context context) {
        templateEngine.render(rootKey, context);
    }
}
