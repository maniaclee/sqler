package com.lvbby.sqler.core;

/**
 * Created by peng on 16/7/28.
 */
public abstract class AbstractCheck implements Checkable {
    protected void docheck(boolean isTrue, String message) {
        if (!isTrue)
            throw new IllegalArgumentException(message);
    }
}
