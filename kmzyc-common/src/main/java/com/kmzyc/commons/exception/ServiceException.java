package com.kmzyc.commons.exception;


public class ServiceException extends BaseException {

    private static final long serialVersionUID = 1L;



    private int errorCode = 0;

    public ServiceException(int errorCode, String errorString) {
        super(errorString);
        this.errorCode = errorCode;

    }

    public ServiceException(int errorCode, String errorString, Throwable cause) {
        super(errorString, cause);
        this.errorCode = errorCode;

    }

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorString() {
        return this.getMessage();
    }
}
