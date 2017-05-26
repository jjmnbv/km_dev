package com.kmzyc.framework.container.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.utils.DefaultClassFilter;

/**
 * 监听器标注(必须与Component注解同时使用).
 * @author weishanyao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
	
    /** 默认的监听器排序器 */
    public final static Comparator<Class<?>> sorter = new Comparator<Class<?>>() {
        public int compare(Class<?> cls1,Class<?> cls2) {
            Listener l1 = cls1.getAnnotation(Listener.class);
            Listener l2 = cls2.getAnnotation(Listener.class);
            if (null==l1) return 1;
            if (null==l2) return -1;
            return l1.value()-l2.value();
        }
    };
    
    /** 默认的类过滤器 */
    public final static ClassFilter filter = new DefaultClassFilter();
    
    /** 一组类过滤器（给定被监听的类） */
    public final static Map<Class<?>,ClassFilter> filters = new HashMap<Class<?>,ClassFilter>() {
        private static final long serialVersionUID = 1L;
        @Override
        public ClassFilter get(Object key) {
            if (key instanceof Class) {
                ClassFilter cf = super.get(key);
                if (null==cf) {
                    Class<?> source = (Class<?>)key;
                    put(source, cf=new DefaultClassFilter(source));
                }
                return cf;
            }
            throw new IllegalArgumentException("Key "+key+" must is a class!");
        }
    };
	
	// -----
	
	/** 监听器名称，与被监听组件名称一致，默认为忽略此限制(监听所有类) */
	String name() default "";
	
	/** 方面(被监听者类组)，默认为忽略此限制(监听所有类) */
	Class<?> source() default void.class;
	
	/** 监听器索引号 */
	int value() default 0;
}
