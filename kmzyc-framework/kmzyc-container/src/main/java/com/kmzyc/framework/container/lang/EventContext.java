package com.kmzyc.framework.container.lang;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.kmzyc.framework.container.aop.MethodDescription;

public class EventContext extends java.util.EventObject {
    private static final long serialVersionUID = 1L;

    /**
     * 键：保存源方法的返回值
     */
    public final static String KEY_RETURN_VALUE = "returnValue";

    /**
     * 键：保存组件工厂
     */
    public final static String KEY_UOSS_CONTEXT = "uoss.context";

    /**
     * 键：保存WEB请求引用
     */
    public final static String KEY_WEB_HTTP_REQUEST = "web.http.request";

    /**
     * 键：保存WEB请求引用
     */
    public final static String KEY_WEB_SERVLET_CONTEXT = "web.servlet.context";


    private final HashMap<String, Object> context = new HashMap<String, Object>();
    protected final MethodDescription md;
    protected final Object args[];

    private List<Throwable> exceptions;

    public EventContext(Object source, MethodDescription md, Object[] args) {
        super(source);
        this.md = md;
        this.args = args;
    }

    public MethodDescription getMethodDescription() {
        return md;
    }

    public Object[] getArguments() {
        return args;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annoClass) {
        for (Annotation a : md.getAnnotans()) {
            if (annoClass.isInstance(a)) return annoClass.cast(a);
        }
        return null;
    }

    public Object getAttribute(String key) {
        return context.get(key);
    }

    public Object setAttribute(String key, Object value) {
        return context.put(key, value);
    }

    public Object removeAttribute(String key) {
        return context.remove(key);
    }

    public void clearAttribute() {
        context.clear();
    }

    public void addException(Throwable e) {
        if (null == exceptions) exceptions = new ArrayList<Throwable>();
        exceptions.add(e);
    }

    public List<Throwable> getExceptions() {
        return exceptions;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(args);
        result = prime * result + ((md == null) ? 0 : md.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final EventContext other = (EventContext) obj;
        if (!Arrays.equals(args, other.args)) return false;
        if (md == null) {
            if (other.md != null) return false;
        } else if (!md.equals(other.md)) return false;
        return true;
    }

    @Override
    public String toString() {
        String s = getClass().getName() + " - method: " + md.getFullName();
        s += "\n\targs: " + Arrays.toString(args);
        s += "\n\tannotations: " + Arrays.toString(md.getAnnotans());
        return s;
    }


}
