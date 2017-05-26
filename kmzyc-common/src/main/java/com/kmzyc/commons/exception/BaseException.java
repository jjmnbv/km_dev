package com.kmzyc.commons.exception;


/**
 * 基础异常类
 *
 * @author
 * @see Exception
 * @since 1.0
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 3358888911029354719L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
