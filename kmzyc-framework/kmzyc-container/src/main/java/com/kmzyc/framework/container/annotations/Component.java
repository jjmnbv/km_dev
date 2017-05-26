package com.kmzyc.framework.container.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Resource;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    String name() default "";
    String lookup() default "";
    String mappedName() default "";
    String description() default "";
    boolean shareable() default true;
    Class<?> type() default Object.class;
    Resource.AuthenticationType authenticationType() default Resource.AuthenticationType.CONTAINER;
}
