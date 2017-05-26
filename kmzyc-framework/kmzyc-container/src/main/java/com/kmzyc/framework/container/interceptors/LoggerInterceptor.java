package com.kmzyc.framework.container.interceptors;

import com.kmzyc.framework.container.aop.Logger;
import com.kmzyc.framework.container.aop.annotations.LOG;
import com.kmzyc.framework.container.aop.annotations.LOG.POSITION;
import com.kmzyc.framework.container.lang.EventContext;
import com.kmzyc.framework.container.lang.Interceptor;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志拦截.
 *
 * @author weishanyao
 */
public class LoggerInterceptor implements Interceptor {
    private final static Log log = LogFactory.getLog(LoggerInterceptor.class);

    private Logger logger;

    public void abend(EventContext context, Throwable exception) {
        LOG a = context.getAnnotation(LOG.class);
        if (isLoggable(a, POSITION.ABEND)) getLogger(context).log(a, POSITION.ABEND, context, exception);
    }

    public void after(EventContext context) {
        LOG a = context.getAnnotation(LOG.class);
        if (isLoggable(a, POSITION.AFTER)) getLogger(context).log(a, POSITION.AFTER, context, null);
    }

    public void before(EventContext context) {
        LOG a = context.getAnnotation(LOG.class);
        if (isLoggable(a, POSITION.BEFORE)) getLogger(context).log(a, POSITION.BEFORE, context, null);
    }

    private boolean isLoggable(LOG annotation, POSITION postion) {
        if (null != annotation) for (POSITION p : annotation.position()) {
            if (p == postion) return true;
        }
        return false;
    }

    private Logger getLogger(EventContext context) {
        if (null == logger) {
            try {
                logger = ((UossContext) context.getAttribute(EventContext.KEY_UOSS_CONTEXT)).getInstance(Logger.class, "logger");
            } catch (UossException e) {
                log.error("Can't find any Logger!", e);
                logger = new Logger() {
                    public void log(LOG a, POSITION p, EventContext c, Throwable t) {
                    }

                    public void log(LOG.LEVEL l, String s) {
                    }
                };
            }
        }
        return logger;
    }
}
