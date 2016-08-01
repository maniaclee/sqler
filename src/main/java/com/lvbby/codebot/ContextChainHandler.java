package com.lvbby.codebot;

/**
 * Created by peng on 16/7/27.
 */
public abstract class ContextChainHandler<T> implements ContextHandler<T> {
    public abstract void process(T context, ContextHandler<T> next);

    @Override
    public void handle(T context) {
        process(context, null);
    }
}
