package com.kmzyc.framework.container.aop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方面(描述被拦截的方面的特征(名字或类型)).
 * 
 * @author weishanyao
 *
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aspect {
	
	/**
	 * 名字，有限支持通配符<code>*</code>号，支持三种情形：即
	 * <code>*</code>、
	 * <code>*name</code>和
	 * <code>name*</code>.
	 * 
	 */
	String name() default "";
	
	/**
	 * 类型，包括注解类型，void.class(默认)为忽略此匹配条件.
	 *  
	 */
	Class<?> value() default void.class;
}
