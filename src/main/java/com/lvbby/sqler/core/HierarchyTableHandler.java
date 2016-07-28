package com.lvbby.sqler.core;

import java.util.List;


/**
 * Created by peng on 16/7/27.
 */
public interface HierarchyTableHandler extends TableHandler {

    /**
     * return the implementation instance to achieve stream programing
     */
    <T extends HierarchyTableHandler> T addNext(TableHandler t);

    List<TableHandler> getChildren();
}
