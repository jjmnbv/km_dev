package com.kmzyc.framework.container.deployment;

import java.util.Hashtable;


/**
 * 对象扫描事件.
 *
 * @author weishanyao
 */
public interface ScanContext {

    Hashtable<?, ?> getEnvironment();

    /**
     * 当前类的全局限定名
     */
    String getClassName();

    /**
     * 测试当前类是否为使用annotationClasses（其中之一）进行标识
     */
    boolean checkAnnotation(Class<?>... annotationClasses);

    /**
     * 装载当前类
     */
    Class<?> loadClass() throws ClassNotFoundException;
}
