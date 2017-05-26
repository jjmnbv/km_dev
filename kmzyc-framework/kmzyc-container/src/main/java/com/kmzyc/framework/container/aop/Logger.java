package com.kmzyc.framework.container.aop;

import com.kmzyc.framework.container.aop.annotations.LOG;
import com.kmzyc.framework.container.aop.annotations.LOG.POSITION;
import com.kmzyc.framework.container.lang.EventContext;

/**
 * 日志记录器.
 * 
 * @author weishanyao
 *
 */
public interface Logger {

    /**
     * 自动记录日志.
     * 
     * @param annotation 必须以LOG标识的方法才予与记录日志.
     * @param position 时间位置.
     * @param context 上下文.
     * @param exception 目标方法出现的异常，对于ABEND的情况才有此值，其它情况为<code>null</code>.
     * 
     * 
     */
    void log(LOG annotation, POSITION position, EventContext context, Throwable exception);
    
    /**
     * 手动记录日志.
     * 
     */
    void log(LOG.LEVEL level, String message);
}
