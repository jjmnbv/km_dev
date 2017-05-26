package com.kmzyc.framework.container.aop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.kmzyc.framework.container.aop.AOPClassFilter;
import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.InstanceClass;

/**
 * 通知组件注解(必须与Component注解同时使用).
 * @author weishanyao
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Advice {
    
    /** 默认的监听器排序器 */
    public final static Comparator<InstanceClass<?>> sorter = new Comparator<InstanceClass<?>>() {
        public int compare(InstanceClass<?> cls1,InstanceClass<?> cls2) {
            Advice l1 = cls1.instanceClass.getAnnotation(Advice.class);
            Advice l2 = cls2.instanceClass.getAnnotation(Advice.class);
            if (null==l1) return 1;
            if (null==l2) return -1;
            return l1.index()-l2.index();
        }
    };
    
    /** 一组类过滤器（给定被监听的类） */
    public final static Map<Class<?>,ClassFilter> filters = new HashMap<Class<?>,ClassFilter>() {
        private static final long serialVersionUID = 1L;
        @Override
        public ClassFilter get(Object key) {
            if (key instanceof Class) {
                ClassFilter cf = super.get(key);
                if (null==cf) {
                    Class<?> source = (Class<?>)key;
                    put(source, cf=new AOPClassFilter(source));
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
    Aspect[] aspects() default {};
    
    /** 监听器索引号 */
    int index() default 0;
}
