package com.lvbby.sqler.core.impl;

import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.ContextHandler;
import com.lvbby.sqler.core.ContextTask;

/**
 * Created by peng on 16/7/30.
 */
public abstract class ContextTaskHandler<T> implements ContextTask<T>, ContextHandler {

    @Override
    public void handle(Context context) {
        process(context);
    }

}
