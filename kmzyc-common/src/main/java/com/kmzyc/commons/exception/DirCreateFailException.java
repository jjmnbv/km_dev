package com.kmzyc.commons.exception;

/**
 * Created by min on 2016/10/31.
 */
public class DirCreateFailException extends RuntimeException {
    public DirCreateFailException() {
        super();
    }

    public DirCreateFailException(String message) {
        super(message);
    }

    public DirCreateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirCreateFailException(Throwable cause) {
        super(cause);
    }

    protected DirCreateFailException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
