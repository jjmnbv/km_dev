package com.kmzyc.framework.container.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.kmzyc.framework.container.aop.annotations.Pointcut;
import com.kmzyc.framework.container.lang.MethodFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 缺省的方法过滤器.
 * 
 * @author weishanyao
 *
 */
public abstract class AbstractMethodFilter implements MethodFilter {
    private final static Log log = LogFactory.getLog(AbstractMethodFilter.class);

    public AbstractMethodFilter() {
        super();
    }

    public boolean accept(Method adviceMethod, MethodDescription sourceMethod) {
        if (log.isDebugEnabled()) {
            log.debug("Accept method \""+adviceMethod+"\" for \""+sourceMethod.getFullName()+"\" ...");
        }
        Pointcut[] pcs = getPointcuts(adviceMethod);
        if (null==pcs) return false;
        if (pcs.length<1) {
            return adviceMethod.getName().equals(sourceMethod.getName());
        }
        for (Pointcut pc: pcs) {
            if (match(pc,sourceMethod)) return true;
        }
        return false;
    }
    
    
    
    protected boolean match(Pointcut pc, MethodDescription md) {
        return matchName(pc.name(),md.getName()) && matchArguments(pc.arguments(),md.getArgTypes()) && matchAnnotation(pc.annotations(),md.getAnnotans());
    }
    protected boolean matchName(String name, String method) {
        int size = name.length();
        if (size<1 || "*".equals(name)) return true;
        if (name.startsWith("*")) return method.endsWith(name.substring(1));
        if (name.endsWith("*")) return method.startsWith(name.substring(0,size-1));
        return name.equals(method);
    }
    
    /**
     * 参数匹配.
     * 
     * @param spec 规约.
     * @param args 方法参数.
     *
     */
    protected boolean matchArguments(Class<?>[] spec, Class<?>[] args) {
        int size = spec.length;
        if (size==1 && spec[0]==void.class) return true;
        return Arrays.equals(spec,args);
    }
    
    /** 注解匹配(匹配任何一个即可) */
    protected boolean matchAnnotation(Class<? extends Annotation>[] spec, Annotation[] anns) {
        if (spec.length<1) return true;
        if (null==anns) return false;
        for (Annotation a: anns) {
            for (Class<? extends Annotation> ac: spec) {
                if (ac.isInstance(a)) {
                    return true;
                }
            }
        }
        return false;
    }



    protected abstract Pointcut[] getPointcuts(Method adviceMethod);
}
