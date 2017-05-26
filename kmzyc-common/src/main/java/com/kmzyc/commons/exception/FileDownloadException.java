package com.kmzyc.commons.exception;

/**
 * 文件下载异常类
 *
 * @author
 * @see BaseException
 * @since 1.0
 */
public class FileDownloadException extends BaseException {

    private static final long serialVersionUID = -2054323226491009690L;

    public FileDownloadException() {
        super();
    }

    public FileDownloadException(String message) {
        super(message);
    }

    public FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDownloadException(Throwable cause) {
        super(cause);
    }

    protected FileDownloadException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
