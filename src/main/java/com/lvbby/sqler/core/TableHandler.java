package com.lvbby.sqler.core;

/**
 * Created by peng on 16/7/27.
 */
public interface TableHandler<T extends Context> {
    void handle(T context);
}
