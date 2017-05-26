package com.kmzyc.framework.container.core;

import com.kmzyc.framework.container.lang.UossException;




/**
 * 状态会话BEAN管理器.
 *
 * @author weishanyao
 * @version $2013-8-6 上午10:36:10$
 *
 */
public class StatefulInstances extends SessionBeanInstances {
    
    @Override
    public <T> T getInstance(Class<T> clazz, String name, Object... args) throws UossException {
        return newInstance(clazz,name,args);
    }

    @Override
    protected String getComponentName(Class<?> implClass) {
        javax.ejb.Stateful cmp = implClass.getAnnotation(javax.ejb.Stateful.class);
        return cmp.name();
    }
    
}
