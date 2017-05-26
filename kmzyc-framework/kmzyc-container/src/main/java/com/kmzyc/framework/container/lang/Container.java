package com.kmzyc.framework.container.lang;

import java.util.Collection;
import java.util.Comparator;


/**
 * 容器.
 * <p>负责细化对象工厂（ObjectFactory）的实例管理工作</p>
 *
 * @author weishanyao
 */
public interface Container {

    /**
     * 取回或创建一个对象.
     *
     * @param clazz 接口类.
     * @return 返回一个配置的接口类的实现.
     * @throws UossException 创建异常。.
     */
    <T> T getInstance(Class<T> clazz, Object... args) throws UossException;

    /**
     * 创建一个对象.
     *
     * @param clazz 接口类.
     * @return 返回一个配置的接口类的实现.
     * @throws UossException 创建异常。.
     */
    <T> T newInstance(Class<T> clazz, Object... args) throws UossException;

    /**
     * 取回或创建一个对象(单例).
     *
     * @param clazz 接口类.
     * @param name  鉴别列, 用于有一个接口多个实现类并存的情况.
     * @return 返回一个配置的接口类的实现.
     * @throws UossException 创建异常。.
     */
    <T> T getInstance(Class<T> clazz, String name, Object... args) throws UossException;

    /**
     * 创建一个对象。.
     *
     * @param clazz 接口类.
     * @param name  鉴别列, 用于有一个接口多个实现类并存的情况.
     * @return 返回一个配置的接口类的实现.
     * @throws UossException 创建异常。.
     */
    <T> T newInstance(Class<T> clazz, String name, Object... args) throws UossException;

    /**
     * 取回配置于容器中从<code>ifClass</code>中实现出的所有具体类。.
     *
     * @param ifClass 指定的接口类.
     * @param filter  类过滤器，可以为空，表示不过滤集合.
     * @param comp    比较器，据此可以返回一个经过排序的集合，此值可以为空.
     * @return 返回所有指定配置的接口类的实现.
     * @throws UossException 取回<code>ifClass</code>的实现类时出现异常状况。.
     */
    <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass,
                                                                  ClassFilter filter, Comparator<InstanceClass<?>> comp) throws UossException;
}
