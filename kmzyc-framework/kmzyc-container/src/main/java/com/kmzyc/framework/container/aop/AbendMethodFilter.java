package com.kmzyc.framework.container.aop;

import java.lang.reflect.Method;

import com.kmzyc.framework.container.aop.annotations.Abend;
import com.kmzyc.framework.container.aop.annotations.Pointcut;


public class AbendMethodFilter extends AbstractMethodFilter {

    @Override
    protected Pointcut[] getPointcuts(Method adviceMethod) {
        Abend abend = adviceMethod.getAnnotation(Abend.class);
        if (null==abend) return null;
        return abend.pointcuts();
    }

}
