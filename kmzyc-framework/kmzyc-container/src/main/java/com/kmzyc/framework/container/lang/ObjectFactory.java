package com.kmzyc.framework.container.lang;

import java.io.InputStream;
import java.util.Map;

/**
 * 对象工厂。.
 *
 * @author weishanyao
 */
public interface ObjectFactory extends Container, Destroyable {

    /**
     * 初始化操作。.
     * (使用默认配置).
     */
    void init();

    /**
     * 初始化操作。.
     *
     * @param is 配置文件的流。.
     */
    void init(InputStream is);

    /**
     * 初始化操作。.
     *
     * @param config 配置文件的文件名和路径。.
     */
    void init(String config);

    /**
     * 初始化操作。.
     *
     * @param overrides 调用参数将覆盖默认的和配置文件的设置。.
     */
    void init(Map<String, String> overrides);

    /**
     * 初始化操作。.
     *
     * @param config    配置文件的文件名和路径。.
     * @param overrides 调用参数将覆盖默认的和配置文件的设置。.
     */
    void init(String config, Map<String, String> overrides);

    /**
     * 初始化/注入(上下文资源)一个在容器外创建的对象。.
     *
     * @param instance 在容器外创建的对象.
     * @param name     名字.
     * @throws UossException 初始化或注入异常。.
     */
    <T> T initInstance(T instance, String name) throws UossException;

    /**
     * 到得统一支持的配置环境。.
     */
    UossContext getUossContext();
}
