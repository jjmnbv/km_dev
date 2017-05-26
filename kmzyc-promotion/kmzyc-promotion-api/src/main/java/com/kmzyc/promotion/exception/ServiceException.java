package com.kmzyc.promotion.exception;

import java.sql.SQLException;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // 日志记录
    // private static final Logger log = LoggerFactory.getLogger(ServiceException.class);

    private int errorCode = 0;

    public ServiceException(int errorCode, String errorString) {
        super(errorString);
        this.errorCode = errorCode;

        // log.error("ServiceException: errorCode={}, errorString={}" ,errorCode, errorString);
    }

    public ServiceException(Exception e) {
        super(e);
        // log.error("ServiceException:errorString=", e);
    }

    public ServiceException(String errorString, SQLException e) {
        super(errorString, e);
        // log.error("ServiceException:errorString=", e);
    }

    public ServiceException(int errorCode, String errorString, Throwable cause) {
        super(errorString, cause);
        this.errorCode = errorCode;

        // log.error("ServiceException: errorCode={}, errorString={}", errorCode, errorString);
    }

    public ServiceException(String string) {
        super(string);
        // log.error("ServiceException:errorString={}", string);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorString() {
        return this.getMessage();
    }
}
