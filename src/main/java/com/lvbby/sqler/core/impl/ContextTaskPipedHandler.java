package com.lvbby.sqler.core.impl;

import com.google.common.collect.Lists;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.PipeLine;

import java.util.List;

/**
 * Created by peng on 16/7/30.
 */
public abstract class ContextTaskPipedHandler<T> extends ContextTaskHandler {
    private List<PipeLine<T>> pipeLines = Lists.newLinkedList();

    public ContextTaskPipedHandler<T> addPipeLine(PipeLine<T> pipeLine) {
        pipeLines.add(pipeLine);
        return this;
    }

    @Override
    public T process(Context context) {
        T t = doProcess(context);
        for (PipeLine<T> pipeLine : pipeLines) {
            try {
                pipeLine.handle(t, context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    public abstract T doProcess(Context context);

}
