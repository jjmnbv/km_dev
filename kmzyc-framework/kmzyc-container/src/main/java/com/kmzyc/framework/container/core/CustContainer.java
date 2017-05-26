package com.kmzyc.framework.container.core;

import java.util.Hashtable;

import com.kmzyc.framework.container.lang.Container;


/**
 * 客户化容器，可以直接配置使用.
 * <p>请参考SpringContainer实现</p>
 * @author weishanyao
 */
public interface CustContainer extends Container {
    
    /**
     * 初始化环境.
     * 
     * @param env 注入配置资源.
     * 
     * 
     */
    void init(Hashtable<Object,Object> env);
    
}
