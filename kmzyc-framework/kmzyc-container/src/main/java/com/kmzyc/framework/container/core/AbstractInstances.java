package com.kmzyc.framework.container.core;

import java.util.Set;

import com.kmzyc.framework.container.lang.Configurable;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;


public abstract class AbstractInstances implements Instances,Configurable {

    protected UossContext context;

    @Override
    public void init(UossContext context, String name) {
        this.context = context;
    }

    @Override
    public <T> T getInstance(Class<T> clazz, Object... args) throws UossException {
        return getInstance(clazz, null, args);
    }

    @Override
    public <T> T newInstance(Class<T> clazz, Object... args) throws UossException {
        return newInstance(clazz, null, args);
    }



    // --
    
    protected void extractInterfaces(Class<?>[] fs, Set<Class<?>> set) {
        if (null==fs || fs.length<1) return;
        for (Class<?> f: fs) {
            if (null!=f && f.isInterface()) {
                set.add(f);
                extractInterfaces(f.getInterfaces(), set);
            }
        }
    }
    protected void extractSuperClass(Class<?> c, Set<Class<?>> set) {
        if (null!=c && Object.class!=c) {
            set.add(c);
            extractInterfaces(c.getInterfaces(), set);
            extractSuperClass(c.getSuperclass(), set);
        }
    }
    

    /**
     * 获取组件名.
     * 
     * @param implClass 实现类.
     * 
     *
     */
    protected abstract String getComponentName(Class<?> implClass);
}
