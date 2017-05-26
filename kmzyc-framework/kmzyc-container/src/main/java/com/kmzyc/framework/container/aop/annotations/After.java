package com.kmzyc.framework.container.aop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 后织入.
 * 
 * <p>
 * 被注解的方法必需符合以下定义：<br />
 * <pre>
 * void method(EventContext)
 * </pre>
 * </p>
 * 
 * @author weishanyao
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface After {
	
	/**
	 * 切点.
	 * 
	 * 如果此值缺省，则默认为与当前方法名称相同.
	 * 
	 * 
	 */
	Pointcut[] pointcuts() default {};
	
}
