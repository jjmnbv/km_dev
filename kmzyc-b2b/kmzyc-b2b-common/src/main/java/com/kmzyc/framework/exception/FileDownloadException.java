package com.kmzyc.framework.exception;

/**
 * 文件下载异常类
 * 
 * @author
 * @since 1.0
 * @see BaseException
 */
public class FileDownloadException extends BaseException {

  private static final long serialVersionUID = -2054323226491009690L;

  public FileDownloadException() {}

  public FileDownloadException(String message) {
    super(message);
  }
}
