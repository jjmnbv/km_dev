package com.kmzyc.framework.container.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 织入.
 * 
 * @author weishanyao
 * 
 * @deprecated 此织入方式不再使用，由{@link Before}、{@link After}和{@link Abend}代替.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Weave {
	enum TYPE {BEFORE,AFTER,ABEND}
    
	TYPE type() default TYPE.BEFORE;
	
	/**
	 * 切点.
	 * 
	 * 如果此值缺省，则默认为与当前方法名称相同.
	 * 
	 * 
	 */
	Pointcut[] pointcuts() default {};
	
}
