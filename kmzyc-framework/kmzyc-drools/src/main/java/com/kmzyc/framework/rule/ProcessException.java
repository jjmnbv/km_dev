package com.kmzyc.framework.rule;

import com.kmzyc.framework.container.core.UossEngine;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;

public class ProcessException extends UossException {
    private static final long serialVersionUID = 1L;

    protected static final UossContext context = UossEngine.getObjectFactory().getUossContext();
    private final ProcessResult processResult;

    public ProcessException() {
        super();
        processResult = null;
    }

    public ProcessException(ProcessResult processResult) {
        super();
        this.processResult = processResult;
    }

    public ProcessException(String msg, String code) {
        super(msg, code);
        processResult = null;
    }

    public ProcessException(String msg, String code, ProcessResult processResult) {
        super(msg, code);
        this.processResult = processResult;
    }

    public ProcessException(String msg, Throwable source, String code) {
        super(msg, source, code);
        processResult = null;
    }

    public ProcessException(String msg, Throwable source, String code, ProcessResult processResult) {
        super(msg, source, code);
        this.processResult = processResult;
    }

    public ProcessException(String msg, Throwable source) {
        super(msg, source);
        processResult = null;
    }

    public ProcessException(String msg, Throwable source, ProcessResult processResult) {
        super(msg, source);
        this.processResult = processResult;
    }

    public ProcessException(String msg) {
        super(msg);
        processResult = null;
    }

    public ProcessException(String msg, ProcessResult processResult) {
        super(msg);
        this.processResult = processResult;
    }

    public ProcessException(Throwable cause, String code) {
        super(cause, code);
        processResult = null;
    }

    public ProcessException(Throwable cause, String code, ProcessResult processResult) {
        super(cause, code);
        this.processResult = processResult;
    }

    public ProcessException(Throwable cause) {
        super(cause);
        processResult = null;
    }

    public ProcessException(Throwable cause, ProcessResult processResult) {
        super(cause);
        this.processResult = processResult;
    }

    // --

    public ProcessResult getProcessResult() {
        return processResult;
    }

}
