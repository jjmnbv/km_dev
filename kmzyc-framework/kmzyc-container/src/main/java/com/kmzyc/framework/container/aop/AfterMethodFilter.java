package com.kmzyc.framework.container.aop;

import java.lang.reflect.Method;

import com.kmzyc.framework.container.aop.annotations.After;
import com.kmzyc.framework.container.aop.annotations.Pointcut;


public class AfterMethodFilter extends AbstractMethodFilter {

    @Override
    protected Pointcut[] getPointcuts(Method adviceMethod) {
        After after = adviceMethod.getAnnotation(After.class);
        if (null==after) return null;
        return after.pointcuts();
    }

}
