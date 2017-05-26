package com.kmzyc.framework.container.persistence;

/**
 * 事务处理异常.
 *
 *
 *
 * @author weishanyao
 *
 *
 */
public class TransactionException extends IllegalStateException {
    private static final long serialVersionUID = 1L;

    public TransactionException() {}

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }


}
