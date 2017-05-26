package com.kmzyc.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(ServiceException.class);
  
  private int errorCode = 0;

  public ServiceException(Exception e) {
    super(e);
    log.error("ServiceException:errorString=" + e);
  }

  public ServiceException(int errorCode, String errorString) {
    super(errorString);
    this.errorCode = errorCode;

    log.error("ServiceException: errorCode=" + errorCode + ", errorString=" + errorString);
  }

  public ServiceException(int errorCode, String errorString, Throwable cause) {
    super(errorString, cause);
    this.errorCode = errorCode;

    log.error("ServiceException: errorCode=" + errorCode + ", errorString=" + errorString);
  }

  public int getErrorCode() {
    return this.errorCode;
  }

  public String getErrorString() {
    return this.getMessage();
  }
}
