package com.lvbby.sqler.core;

import java.util.List;


/**
 * Created by peng on 16/7/27.
 */
public interface HierarchyTableHandler<T extends Context> extends TableHandler<T> {

    void addNext(TableHandler<T> t);

    List<TableHandler> getChildren();
}
