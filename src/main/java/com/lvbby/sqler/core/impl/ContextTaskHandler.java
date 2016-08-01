package com.lvbby.sqler.core.impl;

import com.lvbby.codebot.ContextHandler;
import com.lvbby.codebot.ContextTask;


/**
 * Created by peng on 16/7/30.
 */
public abstract class ContextTaskHandler<T, R> implements ContextTask<T, R>, ContextHandler<T> {

    @Override
    public void handle(T context) {
        process(context);
    }

}
