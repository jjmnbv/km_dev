package com.kmzyc.framework.container.aop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.annotation.Annotation;

/**
 * 被监听的方法（切点）规格.
 * 
 * @author weishanyao
 *
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pointcut {
	
	/**
	 * 方法名，有限支持通配符<code>*</code>号，支持三种情形：即
	 * <code>*</code>、
	 * <code>*name</code>和
	 * <code>name*</code>.
	 * 
	 * 如果此值缺省，则忽略此匹配条件（同<code>*</code>号）.
	 * 
	 */
	String name() default "";
	
	/**
	 * 方法参数定义，默认(void.class)为忽略此匹配条件.
	 *  
	 */
	Class<?>[] arguments() default void.class;
	
    /**
     * 注解，方法必需满足注释要求的条件才能匹配，默认为忽略此匹配条件.
     *  
     */
	Class<? extends Annotation>[]  annotations() default {};
	
}
