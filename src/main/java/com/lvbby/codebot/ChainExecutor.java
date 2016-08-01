package com.lvbby.codebot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng on 16/8/1.
 */
public class ChainExecutor<T> {

    List<ContextHandler<T>> handlers = new ArrayList<>();

    private ChainExecutor(List<ContextHandler<T>> handlers) {
        this.handlers = handlers;
    }

    public static <T> ChainExecutor<T> create(List<ContextHandler<T>> handlers) {
        return new ChainExecutor(handlers);
    }

    public ChainExecutor<T> add(ContextHandler<T> h) {
        if (handlers == null)
            handlers = new ArrayList<>();
        handlers.add(h);
        return this;
    }

    public void exec(T t) {
        if (handlers == null) return;
        int size = handlers.size();
        for (int i = 0; i < size; i++) {
            ContextHandler<T> h = handlers.get(i);
            if (h instanceof ContextChainHandler) {
                ContextHandler<T> next = i < size - 1 ? handlers.get(i + 1) : null;
                ((ContextChainHandler) h).process(t, next);
                return;
            } else {
                h.handle(t);
            }
        }
    }
}
