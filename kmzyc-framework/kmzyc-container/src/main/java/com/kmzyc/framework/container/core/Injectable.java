package com.kmzyc.framework.container.core;

/**
 * 可进行依赖注入的容器（用该接口来修饰容器，使具有依赖注入的功能）.
 *
 * @author weishanyao
 * @version $2013-8-6 上午10:36:10$
 *
 */
@Deprecated
public interface Injectable {

    /**
     * 往对象o中标有注入元数据的字段注入容器元素.
     * 
     * @param o 接受注入的对象.
     * 
     */
    void inject(Object o);
}
