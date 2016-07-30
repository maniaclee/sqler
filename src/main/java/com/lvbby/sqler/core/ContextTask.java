package com.lvbby.sqler.core;

/**
 * Created by peng on 16/7/27.
 */
public interface ContextTask<T> {
    T process(Context context);
}
