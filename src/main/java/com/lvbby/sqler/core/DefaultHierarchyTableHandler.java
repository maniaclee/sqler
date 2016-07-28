package com.lvbby.sqler.core;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by peng on 16/7/28.
 */
public class DefaultHierarchyTableHandler implements HierarchyTableHandler {
    private List<TableHandler> tableHandlers = Lists.newLinkedList();

    private TableHandler holder;

    public static DefaultHierarchyTableHandler create() {
        return of(null);
    }

    public static DefaultHierarchyTableHandler of(TableHandler holder) {
        DefaultHierarchyTableHandler re = new DefaultHierarchyTableHandler();
        re.holder = holder;
        return re;
    }


    @Override
    public DefaultHierarchyTableHandler addNext(TableHandler t) {
        tableHandlers.add(t);
        return this;
    }

    @Override
    public List<TableHandler> getChildren() {
        return tableHandlers;
    }

    @Override
    public void handle(Context context) {
        if (holder != null)
            holder.handle(context);
        tableHandlers.forEach(s -> s.handle(context));
    }

}
