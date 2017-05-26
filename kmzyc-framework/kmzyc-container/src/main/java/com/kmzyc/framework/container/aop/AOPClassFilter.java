package com.kmzyc.framework.container.aop;

import java.lang.annotation.Annotation;

import javax.annotation.Resource;

import com.kmzyc.framework.container.aop.annotations.Advice;
import com.kmzyc.framework.container.aop.annotations.Aspect;
import com.kmzyc.framework.container.lang.ClassFilter;

import org.apache.commons.lang3.StringUtils;


/**
 * 默认的类过滤器.
 * 
 * @author weishanyao
 *
 */
public class AOPClassFilter implements ClassFilter {
    
    private final Class<?> source;
    private final String compName;
    public AOPClassFilter(Class<?> source) {
        String sn = null;
        if (null!=source) {
            Resource p;
            while (null==(p=source.getAnnotation(Resource.class)) && Object.class!=source) {
                source = source.getSuperclass();
            }
            if (null!=p) {
                sn = p.name();
                if (StringUtils.isEmpty(sn)) sn = p.mappedName();
            }
        }
        this.source = source;
        this.compName = sn;
    }

    public boolean accept(Class<?> advice, String key) {
        Advice adv = advice.getAnnotation(Advice.class);
        if (null==adv) return false;
        if (adv.aspects().length<1) {
            return ((null==source && StringUtils.isEmpty(adv.name())) || (null!=source && adv.name().equals(compName)));
        }
        else {
            for (Aspect a: adv.aspects()) {
                if (checkAspect(a)) return true;
            }
            return false;
        }
    }
    
    private boolean checkAspect(Aspect a) {
        return checkName(a.name()) && checkType(a.value());
    }
    private boolean checkName(String name) {
        int size = name.length();
        if (size<1 || "*".equals(name)) return true;
        if (null==compName) return false;
        if (name.startsWith("*")) return compName.endsWith(name.substring(1));
        if (name.endsWith("*")) return compName.startsWith(name.substring(0,size-1));
        return name.equals(compName);
    }
    private boolean checkType(Class<?> type) {
        return void.class==type
            || (null!=source && type.isAssignableFrom(source))
            || (null!=source && Annotation.class.isAssignableFrom(type)
                && source.isAnnotationPresent(type.asSubclass(Annotation.class)));
    }
}
