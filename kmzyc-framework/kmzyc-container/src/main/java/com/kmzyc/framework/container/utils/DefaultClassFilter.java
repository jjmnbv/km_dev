package com.kmzyc.framework.container.utils;

import java.lang.annotation.Annotation;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.kmzyc.framework.container.annotations.Listener;
import com.kmzyc.framework.container.lang.ClassFilter;


/**
 * 默认的类过滤器.
 *
 * @author weishanyao
 */
public class DefaultClassFilter implements ClassFilter {

    private final Class<?> source;
    private final String compName;

    public DefaultClassFilter() {
        this.source = null;
        this.compName = null;
    }

    public DefaultClassFilter(Class<?> source) {
        String sn = null;
        if (null != source) {
            Resource p;
            while (null == (p = source.getAnnotation(Resource.class)) && Object.class != source) {
                source = source.getSuperclass();
            }
            if (null != p) {
                sn = p.name();
                if (StringUtils.isEmpty(sn)) sn = p.mappedName();
            }
        }
        this.source = source;
        this.compName = sn;
    }

    public boolean accept(Class<?> listener, String key) {
        Listener l = listener.getAnnotation(Listener.class);
        if (null == l) return false;
        return checkName(l) && checkType(l.source());
    }

    private boolean checkName(Listener l) {
        String name = compName;
        if (null == name) {
            Resource p = l.source().getAnnotation(Resource.class);
            if (null != p) {
                name = p.name();
                if (StringUtils.isEmpty(name)) name = p.mappedName();
            }
        }
        if (null == name && StringUtils.isNotEmpty(l.name())) return false;
        return null != name && (StringUtils.isEmpty(l.name()) || l.name().equals(name));
    }

    private boolean checkType(Class<?> lc) {
        return lc == void.class
                || null == source
                || lc.isAssignableFrom(source)
                || (Annotation.class.isAssignableFrom(lc) && source.isAnnotationPresent(lc.asSubclass(Annotation.class)));
    }
}
