package com.kmzyc.framework.container.core;

import com.kmzyc.framework.container.lang.UossException;

/**
 * 补充创建.
 * 实体分二个阶段创建, 第一阶段严格查找配置类型, 第二阶段模糊匹配配置类型.
 *
 * @author weishanyao
 *
 */
public interface Renewer {

    <T> T renew(Class<T> clazz, String name, Object... args) throws UossException;
}
