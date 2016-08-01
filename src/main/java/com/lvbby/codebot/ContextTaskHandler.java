package com.lvbby.codebot;

/**
 * Created by peng on 16/7/30.
 */
public abstract class ContextTaskHandler<T, R> implements ContextTask<T, R>, ContextHandler<T> {

    @Override
    public void handle(T context) {
        process(context);
    }

}
