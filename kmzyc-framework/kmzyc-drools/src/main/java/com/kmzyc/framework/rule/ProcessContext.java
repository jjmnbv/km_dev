package com.kmzyc.framework.rule;


import org.kie.api.marshalling.Marshaller;

import com.kmzyc.framework.container.lang.UossContext;

/**
 * 处理器上下文.
 */
public interface ProcessContext extends Marshaller, UossContext {

    /**
     * 取回开始状态.
     */
    int getStartStatus();

    /**
     * 取得系统识别码.
     *
     * @return
     */
    int getSystemId();
}
