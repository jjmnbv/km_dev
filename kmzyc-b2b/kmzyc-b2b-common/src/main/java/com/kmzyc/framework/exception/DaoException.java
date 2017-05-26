package com.kmzyc.framework.exception;

/**
 * 数据访问模块异常类
 * 
 * @author
 * @since 1.0
 * @see BaseException
 */
public class DaoException extends BaseException {

  private static final long serialVersionUID = 2357594864159475349L;

  public DaoException() {}

  public DaoException(String message) {
    super(message);
  }

}
