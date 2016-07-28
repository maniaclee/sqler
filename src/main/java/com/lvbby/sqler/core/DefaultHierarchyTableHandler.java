package com.lvbby.sqler.core;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by peng on 16/7/28.
 */
public class DefaultHierarchyTableHandler<T extends Context> implements HierarchyTableHandler<T> {
    private List<TableHandler> tableHandlers = Lists.newLinkedList();

    private TableHandler<T> holder;

    public static <T extends Context> DefaultHierarchyTableHandler<T> create() {
        return of(null);
    }

    public static <T extends Context> DefaultHierarchyTableHandler<T> of(TableHandler<T> holder) {
        DefaultHierarchyTableHandler<T> re = new DefaultHierarchyTableHandler<>();
        re.holder = holder;
        return re;
    }


    @Override
    public void addNext(TableHandler<T> t) {
        tableHandlers.add(t);
    }

    @Override
    public List<TableHandler> getChildren() {
        return tableHandlers;
    }

    @Override
    public void handle(T context) {
        if (holder != null)
            holder.handle(context);
        tableHandlers.forEach(s -> s.handle(context));
    }

    public DefaultHierarchyTableHandler addChild(TableHandler tableHandler) {
        addNext(tableHandler);
        return this;
    }
}
