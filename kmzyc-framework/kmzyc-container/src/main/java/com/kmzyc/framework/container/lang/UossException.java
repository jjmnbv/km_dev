package com.kmzyc.framework.container.lang;


/**
 * 异常基类.
 *
 * @author weishanyao
 */
public class UossException extends Exception {
    private static final long serialVersionUID = -1513027462260162796L;

    /**
     * 异常代码
     */
    public final String code;

    public UossException() {
        super();
        code = null;
    }

    public UossException(Throwable cause) {
        this(cause, null);
    }

    public UossException(String msg, Throwable source) {
        this(msg, source, null);
    }

    public UossException(String msg) {
        super(msg);
        code = null;
    }


    public UossException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public UossException(String msg, Throwable source, String code) {
        super(msg, source);
        this.code = code;
    }

    public UossException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
