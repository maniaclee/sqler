package com.lvbby.sqler.exceptions;

/**
 * Created by peng on 16/7/30.
 */
public class SqlerException extends RuntimeException {
    public SqlerException(Throwable e) {
        super(e);
    }

    public SqlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlerException(String message) {
        super(message);
    }
}
