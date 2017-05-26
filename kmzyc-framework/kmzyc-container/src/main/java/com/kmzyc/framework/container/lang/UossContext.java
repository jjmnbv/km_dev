package com.kmzyc.framework.container.lang;

import java.util.Properties;

/**
 * 上下文对象.
 *
 * @author weishanyao
 */
public interface UossContext extends Container {

    /**
     * 上下文配置环境。.
     */
    Properties getEnvironment();

    /**
     * 从上下文配置环境中取回一个属性值。.
     *
     * @param key 上下文配置属性的键.
     * @return 返回属性值.
     */
    String getProperty(String key);

    /**
     * 从上下文配置环境中取回一个属性值。.
     *
     * @param key 上下文配置属性的键.
     * @param def 如果没有取到配置属性，则采用此值.
     * @return 返回属性值.
     */
    String getProperty(String key, String def);

    /**
     * 从上下文配置环境中取回一个属性值。.
     *
     * @param key 上下文配置属性的键.
     * @param def 如果没有取到配置属性，则采用此值.
     * @return 返回属性值.
     */
    int getProperty(String key, int def);

    /**
     * 从上下文配置环境中取回一个属性值。.
     *
     * @param key 上下文配置属性的键.
     * @param def 如果没有取到配置属性，则采用此值.
     * @return 返回属性值.
     */
    long getProperty(String key, long def);

    /**
     * 从上下文配置环境中取回一个属性值。.
     *
     * @param key 上下文配置属性的键.
     * @return 返回属性值.
     */
    boolean isTrue(String key);

    /**
     * 初始化/注入(上下文资源)一个在容器外创建的对象。.
     *
     * @param instance 在容器外创建的对象.
     * @param name     名字.
     * @throws UossException 初始化或注入异常。.
     */
    <T> T initInstance(T instance, String name) throws UossException;

    /**
     * 向上下文对象设置一个共享对象，并返回已经存在相同属性名的原来保存的对象.
     *
     * @param key   属性名.
     * @param value 属性值.
     * @return 返回相同属性名的旧对象，如果不存在旧的对象，则返回null.
     */
    Object setAttribute(String key, Object value);

    /**
     * 获取一个共享对象.
     *
     * @param key 属性名.
     * @return 返回以指定属性名保存的对象，如果不存在对应的对象，则返回null.
     */
    Object getAttribute(String key);

    /**
     * 取回一个共享对象（并将其从上下文环境中删除）.
     *
     * @param key 属性名.
     * @return 返回以指定属性名保存的对象，如果不存在对应的对象，则返回null.
     */
    Object removeAttribute(String key);

    /**
     * 调试信息
     */
    void debug(String msg);

    /**
     * 调试信息
     */
    void info(String msg);

    /**
     * 错误信息
     */
    void error(String msg, Exception exception);

    /**
     * 警告信息
     */
    void warn(String msg, Exception exception);
}
