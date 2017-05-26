package com.kmzyc.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地对象与远程对象转换异常类
 * 
 * @author lishiming
 * @since 1.0
 * @see BaseException
 */
public class ObjectTransformException extends BaseException {

  private static final long serialVersionUID = -2054323226491009690L;
  
  private static final Logger log = LoggerFactory.getLogger(ObjectTransformException.class);
  
  public ObjectTransformException() {}

  public ObjectTransformException(String message) {
    super(message);
    log.error("Object Transform Exception: ", message);
  }
}
