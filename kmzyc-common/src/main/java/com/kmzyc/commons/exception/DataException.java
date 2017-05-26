package com.kmzyc.commons.exception;

/**
 * 数据异常类
 *
 * @author
 * @see BaseException
 * @since 1.0
 */
public class DataException extends BaseException {

    private static final long serialVersionUID = 7138217038057726081L;

    public DataException() {
        super();
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    protected DataException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
