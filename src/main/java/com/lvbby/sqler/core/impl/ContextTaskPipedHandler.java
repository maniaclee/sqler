package com.lvbby.sqler.core.impl;

import com.google.common.collect.Lists;
import com.lvbby.codebot.PipeLine;

import java.util.List;

/**
 * Created by peng on 16/7/30.
 */
public abstract class ContextTaskPipedHandler<T, R> extends ContextTaskHandler<T, R> {
    private List<PipeLine<R, T>> pipeLines = Lists.newLinkedList();

    public ContextTaskPipedHandler<T, R> addPipeLine(PipeLine<R, T> pipeLine) {
        pipeLines.add(pipeLine);
        return this;
    }

    @Override
    public R process(T context) {
        R t = doProcess(context);
        for (PipeLine<R, T> pipeLine : pipeLines) {
            try {
                pipeLine.handle(t, context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    public abstract R doProcess(T context);

}
