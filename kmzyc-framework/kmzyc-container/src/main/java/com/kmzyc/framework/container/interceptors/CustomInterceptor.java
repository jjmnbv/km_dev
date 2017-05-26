package com.kmzyc.framework.container.interceptors;


import com.kmzyc.framework.container.aop.AOP;
import com.kmzyc.framework.container.lang.EventContext;
import com.kmzyc.framework.container.lang.Interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CustomInterceptor implements Interceptor {
    private final static Log log = LogFactory.getLog(CustomInterceptor.class);

    public void abend(EventContext context, Throwable exception) {
        fire(context, AOP.WeaveType.ABEND, exception);
    }

    public void after(EventContext context) {
        if (log.isDebugEnabled()) {
            log.debug("After method \"" + context.getMethodDescription().getFullName() + "\" ...");
        }
        fire(context, AOP.WeaveType.AFTER, null);
        if (log.isDebugEnabled()) {
            log.debug("Ater method \"" + context.getMethodDescription().getFullName() + "\" - O.K.");
        }
    }

    public void before(EventContext context) {
        if (log.isDebugEnabled()) {
            log.debug("Before method \"" + context.getMethodDescription().getFullName() + "\" ...");
        }
        fire(context, AOP.WeaveType.BEFORE, null);
        if (log.isDebugEnabled()) {
            log.debug("Before method \"" + context.getMethodDescription().getFullName() + "\" - O.K.");
        }
    }


    private void fire(EventContext context, AOP.WeaveType weaveType, Throwable exception) {
        Object source = context.getSource();
        if (source instanceof AOP) ((AOP) source).advise(weaveType, context, exception);
        else log.warn("Source " + source + " not a ClassMethodCache, can't listen for it!");
    }
}
