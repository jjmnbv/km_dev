package com.kmzyc.framework.container.core;

/**
 * 无状态会话BEAN管理器.
 * @author weishanyao
 * @version $2013-8-6 上午10:36:10$
 *
 */
public class StatelessInstances extends SessionBeanInstances {
    @Override
    protected String getComponentName(Class<?> implClass) {
        javax.ejb.Stateless cmp = implClass.getAnnotation(javax.ejb.Stateless.class);
        return cmp.name();
    }
}
