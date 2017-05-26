package com.pltfm.app.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * token注解类，能在方法前面添加token注解
 *
 * @author zhl
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Token {
}
