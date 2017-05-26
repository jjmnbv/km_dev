package com.kmzyc.framework.container.lang;

/**
 * 可配置对象.
 * 
 * @author weishanyao
 *
 */
public interface Configurable {

    /**
     * 初始化动作。
     * 
     * @param context 上下文环境
     * @param name 模块名称
     * 
     * @throws UossException
     * 
     */
    void init(UossContext context, String name) throws UossException;
}
