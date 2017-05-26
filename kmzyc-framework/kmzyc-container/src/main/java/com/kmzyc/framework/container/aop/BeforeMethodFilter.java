package com.kmzyc.framework.container.aop;

import java.lang.reflect.Method;

import com.kmzyc.framework.container.aop.annotations.Before;
import com.kmzyc.framework.container.aop.annotations.Pointcut;


public class BeforeMethodFilter extends AbstractMethodFilter {

    @Override
    protected Pointcut[] getPointcuts(Method adviceMethod) {
        Before before = adviceMethod.getAnnotation(Before.class);
        if (null==before) return null;
        return before.pointcuts();
    }

}
