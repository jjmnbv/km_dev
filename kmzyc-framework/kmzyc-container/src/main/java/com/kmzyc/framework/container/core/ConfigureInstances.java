package com.kmzyc.framework.container.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.Configurable;
import com.kmzyc.framework.container.lang.Container;
import com.kmzyc.framework.container.lang.Destroyable;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;

/**
 * 配置的组件管理器.
 *
 * @author weishanyao
 * @version $2013-8-6 上午10:36:10$
 *
 */
public class ConfigureInstances implements Container,Destroyable,Renewer {
    private static final Log log = LogFactory.getLog(ConfigureInstances.class);
    
    private final Map<Class<?>, Map<String,Object>> instances = new HashMap<Class<?>, Map<String,Object>>();
    private final UossContext context;
    private final ComponentCreator cc;
    
    protected ConfigureInstances(ComponentCreator cc, UossContext context) {
        this.cc = cc;
        this.context = context;
    }
    
    @Override
    public<T> T newInstance(Class<T> clazz, String name, Object... args) {
        String key = clazz.getName();
        String className = key; 
        if (null!=name && name.length()>1) className = key+"."+name;
        className = context.getProperty(className);
        if (null==className && null!=name) className = context.getProperty(name);
        if (null==className) className = context.getProperty(key);
        if (null!=className && className.length()>0) {
            try {
                Class<?> clss = Class.forName(className);
                Object o = cc.newObject(clss,name,args);
                cc.inject(o,clss);
                if (o instanceof Configurable) ((Configurable)o).init(context, name);
                return clazz.cast(o);
            }
            catch(Exception e) {
                log.warn("Can't create instance of "+className, e);
                return null;
            }
        }
        return null;
    }

    @Override
    public<T> T renew(Class<T> clazz, String name, Object... args) {
        if (null==name || name.length()==0) return null;
        String className = context.getProperty(clazz.getName());
        if (null!=className && className.length()>0) {
            try {
                Class<?> clss = Class.forName(className);
                Object o = cc.newObject(clss,name,args);
                cc.inject(o,clss);
                if (o instanceof Configurable) ((Configurable)o).init(context, name);
                return clazz.cast(o);
            }
            catch(Exception e) {
                log.warn("Can't create instance of "+className, e);
                return null;
            }
        }
        return null;
    }

    @Override
    public<T> T getInstance(Class<T> clazz, String name, Object... args) {
        Map<String,Object> map = instances.get(clazz);
        if (null==map) {
            instances.put(clazz, map=new HashMap<String,Object>());
        }
        Object o = map.get(name);
        if (null!=o) return clazz.cast(o);
        T t = newInstance(clazz, name, args);
        map.put(name,t);
        return t;
    }

    
    @Override
    public<T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass, ClassFilter filter, Comparator<InstanceClass<?>> comp) {
        ArrayList<InstanceClass<? extends T>> list = new ArrayList<InstanceClass<? extends T>>();
        Properties ps = context.getEnvironment();
        String ifClassName = ifClass.getName();
        int len = ifClassName.length();
        Class<?> c;
        String cn,name;
        InstanceClass<T> ic;
        for (Map.Entry<?,?> me: ps.entrySet()) {
            c = null;
            name = null;
            cn = (String)me.getKey();
            if (cn.startsWith(ifClassName)) {
                name = cn.substring(len);
                if (name.length()>0 && !name.startsWith(".")) continue;
                name = name.substring(1);
                try {
                    c = Class.forName((String)me.getValue());
                    if (null!=c && ifClass.isAssignableFrom(c)) {
                        if ((null==filter || filter.accept(c,name))) {
                            ic = new InstanceClass<T>(c.asSubclass(ifClass),name);
                            if (!list.contains(ic)) list.add(ic);
                        }
                    }
                }
                catch(Exception e) {
                    log.debug("Can't instance class "+me.getValue()+", cause - "+e.getMessage());
                }
            }
        }
        if (null!=comp && !list.isEmpty()) Collections.sort(list,comp);
        return list;
    }
    
    @Override
    public void destroy() {
        for (Map<String,Object> m: instances.values()) {
            for (Object o: m.values()) {
                if (o instanceof Destroyable) {
                    ((Destroyable)o).destroy();
                }
            }
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz, Object... args) throws UossException {
        return getInstance(clazz,null,args);
    }

    @Override
    public <T> T newInstance(Class<T> clazz, Object... args) throws UossException {
        return newInstance(clazz,null,args);
    }
}
