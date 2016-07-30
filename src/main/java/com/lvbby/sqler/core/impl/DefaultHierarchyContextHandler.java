package com.lvbby.sqler.core.impl;

import com.google.common.collect.Lists;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.ContextHandler;
import com.lvbby.sqler.core.HierarchyContextHandler;

import java.util.List;

/**
 * Created by peng on 16/7/28.
 */
public class DefaultHierarchyContextHandler implements HierarchyContextHandler {
    private List<ContextHandler> contextHandlers = Lists.newLinkedList();

    private ContextHandler holder;

    public static DefaultHierarchyContextHandler create() {
        return of(null);
    }

    public static DefaultHierarchyContextHandler of(ContextHandler holder) {
        DefaultHierarchyContextHandler re = new DefaultHierarchyContextHandler();
        re.holder = holder;
        return re;
    }

    public static DefaultHierarchyContextHandler connect(ContextHandler... holders) {
        DefaultHierarchyContextHandler re = create();
        if (holders == null || holders.length == 0)
            return re;
        for (ContextHandler holder : holders) re.addNext(holder);
        return re;
    }


    @Override
    public DefaultHierarchyContextHandler addNext(ContextHandler t) {
        contextHandlers.add(t);
        return this;
    }

    @Override
    public List<ContextHandler> getChildren() {
        return contextHandlers;
    }

    @Override
    public void handle(Context context) {
        if (holder != null)
            holder.handle(context);
        contextHandlers.forEach(s -> s.handle(context));
    }

}
