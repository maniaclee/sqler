package com.lvbby.sqler.core;

import com.lvbby.codebot.ContextHandler;

import java.util.List;


/**
 * Created by peng on 16/7/27.
 */
public interface HierarchyContextHandler<T> extends ContextHandler<T> {

    /**
     * return the implementation instance to achieve stream programing
     */
    <T extends HierarchyContextHandler> T addNext(ContextHandler t);

    List<ContextHandler> getChildren();
}
