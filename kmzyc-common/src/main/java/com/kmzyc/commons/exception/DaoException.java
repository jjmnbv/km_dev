package com.kmzyc.commons.exception;

/**
 * 数据访问模块异常类
 *
 * @author
 * @see BaseException
 * @since 1.0
 */
public class DaoException extends BaseException {

    private static final long serialVersionUID = 2357594864159475349L;

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    protected DaoException(String message, Throwable cause, boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
