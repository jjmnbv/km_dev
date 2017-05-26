package com.kmzyc.framework.container.lang;

import java.lang.reflect.Method;

import com.kmzyc.framework.container.aop.MethodDescription;


/**
 * 方法过滤器。.
 *
 * @author weishanyao
 */
public interface MethodFilter {

    /**
     * 是否认可给定的方法.
     *
     * @param adviceMethod 判断此方法是否可以被接受（通知方法）.
     *                     <!-- @param adviceClass 持有方法adviceMethod（通知方法）的类. -->
     * @param sourceMethod 原方法，此方法请调用通知方法.
     * @return 如果通知方法被接受，则返回<code>true</code>，否则返回<code>false</code>.
     */
    boolean accept(Method adviceMethod,/* Class<?> adviceClass,*/ MethodDescription sourceMethod);
}
