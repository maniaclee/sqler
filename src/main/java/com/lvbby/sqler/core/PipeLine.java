package com.lvbby.sqler.core;

/**
 * Created by peng on 16/7/27.
 */
public interface PipeLine<T> {
    void handle(T t , Context context);
}
