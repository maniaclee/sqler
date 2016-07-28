package com.lvbby.sqler.handler;

import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.render.TemplateEngine;

/**
 * Created by lipeng on 16/7/28.
 */
public class TemplateEngineHandler implements TableHandler<Context> {
    private TemplateEngine templateEngine;

    public static TableHandler of(TemplateEngine templateEngine) {
        TemplateEngineHandler templateEngineHandler = new TemplateEngineHandler();
        templateEngineHandler.templateEngine = templateEngine;
        return templateEngineHandler;
    }

    @Override
    public void handle(Context context) {
        templateEngine.render("c", context);
    }
}
