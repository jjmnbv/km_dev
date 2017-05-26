package com.kmzyc.framework.container.lang;


/**
 * 加密解密异常.
 * @author weishanyao
 */
public class EncryptionException extends UossException {
    private static final long serialVersionUID = 1L;

    public EncryptionException() {
        super();
    }
    public EncryptionException(String msg, String code) {
        super(msg, code);
    }
    public EncryptionException(String msg, Throwable source, String code) {
        super(msg, source, code);
    }
    public EncryptionException(String msg, Throwable source) {
        super(msg, source);
    }
    public EncryptionException(String msg) {
        super(msg);
    }
    public EncryptionException(Throwable cause, String code) {
        super(cause, code);
    }
    public EncryptionException(Throwable cause) {
        super(cause);
    }
}
