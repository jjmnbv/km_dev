package com.kmzyc.framework.container.lang;

/**
 * 事件拦截器.
 *
 * @author weishanyao
 */
public interface Interceptor extends java.util.EventListener {

    /**
     * 方法开始前调用
     */
    void before(EventContext context);

    /**
     * 方法结束后调用
     */
    void after(EventContext context);

    /**
     * 方法执行时出现异常
     */
    void abend(EventContext context, Throwable exception);
}
