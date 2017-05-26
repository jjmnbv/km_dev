package com.pltfm.exception;

/**
 * 基础异常类
 * 
 * @author
 * @since 1.0
 * @see Exception
 */
public class BaseException extends Exception {

  private static final long serialVersionUID = 3358888911029354719L;

  public BaseException() {}

  public BaseException(String message) {
    super(message);
  }

}
