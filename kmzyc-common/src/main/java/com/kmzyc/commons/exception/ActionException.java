package com.kmzyc.commons.exception;

/**
 * 页面控制模块异常类
 *
 * @author
 * @see BaseException
 * @since 1.0
 */
public class ActionException extends BaseException {

    private static final long serialVersionUID = -7553293136071625348L;

    public ActionException() {
        super();
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }

    protected ActionException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
