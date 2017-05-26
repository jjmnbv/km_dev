package com.kmzyc.zkconfig.exception;

import com.kmzyc.commons.exception.BaseException;

/**
 * Created by min on 2016/8/10.
 */
public class ZkException extends BaseException {

    public ZkException() {
        super();
    }

    public ZkException(String message) {
        super(message);
    }

    public ZkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZkException(Throwable cause) {
        super(cause);
    }

    protected ZkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
