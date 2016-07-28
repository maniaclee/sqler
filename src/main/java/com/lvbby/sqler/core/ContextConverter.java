package com.lvbby.sqler.core;

/**
 * Created by peng on 16/7/28.
 */
public interface ContextConverter<T> {
    T handle(Context context);
}
