package com.kmzyc.framework.container.core;

import static com.kmzyc.framework.container.utils.AnnotationHelper.getAnnotationProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.annotations.ProxyType;
import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.Configurable;
import com.kmzyc.framework.container.lang.Destroyable;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;

/**
 * 组件管理器.
 *
 * @author weishanyao
 * @version $2013-8-6 上午10:36:10$
 */
public class ComponentInstances extends AbstractInstances implements Destroyable, Renewer {
    private static final Log log = LogFactory.getLog(ComponentInstances.class);

    protected final Map<String, Class<?>> compClasses = new HashMap<String, Class<?>>();

    private final Map<Class<?>, Map<String, Object>> instances
            = new HashMap<Class<?>, Map<String, Object>>();

    private final Class<? extends Annotation> compType;

    public ComponentInstances() {
        this(Resource.class);
    }

    public ComponentInstances(Class<? extends Annotation> compType) {
        super();
        this.compType = compType;
    }

    @Override
    public void init(UossContext context, String name) {
        super.init(context, name);
        for (ProxyType pt : ProxyType.values()) {
            if (pt.interceptor instanceof Configurable) {
                try {
                    ((Configurable) pt.interceptor).init(context, name);
                } catch (UossException e) {
                    log.error("Can't init for " + pt.name() + ".interceptor", e);
                }
            }
        }

    }

    @Override
    public <T> T newInstance(Class<T> clazz, String name, Object... args) {
        return createInstance(getComponentClassFromAnnotations(clazz, name), name, args);
    }

    @Override
    public <T> T renew(Class<T> clazz, String name, Object... args) {
        return createInstance(getComponentClassFromAnnotations4Renew(clazz), name, args);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name, Object... args) {
        Map<String, Object> map = instances.get(clazz);
        if (null == map) {
            instances.put(clazz, map = new HashMap<String, Object>());
        }
        Object o = map.get(name);
        if (null != o) return clazz.cast(o);
        T t = newInstance(clazz, name, args);
        map.put(name, t);
        return t;
    }


    @Override
    public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass,
                                                                         ClassFilter filter, Comparator<InstanceClass<?>> comp) {
        ArrayList<InstanceClass<? extends T>> list = new ArrayList<InstanceClass<? extends T>>();
        Class<?> c;
        String name;
        InstanceClass<T> ic;
        for (Map.Entry<String, Class<?>> e : compClasses.entrySet()) {
            if (null != (c = e.getValue()) && ifClass.isAssignableFrom(c)) {
                if ((null == filter || filter.accept(c, e.getKey()))) {
                    name = getComponentName(c);
                    if (name.length() < 1) name = null;
                    ic = new InstanceClass<T>(c.asSubclass(ifClass), name);
                    if (!list.contains(ic)) list.add(ic);
                }
            }
        }
        if (null != comp && !list.isEmpty()) Collections.sort(list, comp);
        return list;
    }

    @Override
    public void destroy() {
        for (Map<String, Object> m : instances.values()) {
            for (Object o : m.values()) {
                if (o instanceof Destroyable) {
                    ((Destroyable) o).destroy();
                }
            }
        }
    }

    // --

    @Override
    public void parse(Class<?> implClass, Hashtable<?, ?> env) {
        HashSet<Class<?>> ifs = new HashSet<Class<?>>();
        extractSuperClass(implClass, ifs);
        if (ifs.size() < 2) {
            log.debug("No interfaces found for Component: " + implClass.getName());
        }
        if (log.isDebugEnabled()) {
            String s = "Component " + implClass.getName() + " has realized " + ifs.size() + " interfaces(or parent class):";
            for (Class<?> c : ifs) {
                s += "\n\t" + c;
            }
            log.debug(s);
        }
        mappingComponentByInterfaces(implClass, ifs);
    }


    // --

    /**
     * 根据给定的接口(或抽象父)类取回实现类.
     *
     * @param clss 接口类或父类.
     * @param name 组件名.
     * @param args 参数(在此无用).
     * @return 取回实现类(会话BEAN).
     */
    protected <T> Class<? extends T> getComponentClassFromAnnotations(Class<T> clss, final String name) {
        String className = clss.getName();
        String category = context.getProperty(UossEngine.DEFAULT_COMPONENT_KEY, null);
        String cname = category;
        Class<?> c = null;
        if (null != cname && cname.length() > 0) {
            if (null != name && name.length() > 0
                    && !name.equals(cname)
                    && !name.startsWith(cname + ".")) {
                cname = cname + "." + name;
            }
        } else cname = name;

        if (null != cname && cname.length() > 0) {
            c = compClasses.get(className + "." + cname);
            if (null == c && cname != category && null != category) c = compClasses.get(className + "." + category);
            //if (null==c) c = compClasses.get(className);
        } else c = compClasses.get(className);
        if (log.isDebugEnabled())
            log.debug("Get component(" + className + ") for name(" + name + ") and category(" + category + ") is " + c);
        return null == c ? null : c.asSubclass(clss);
    }

    protected <T> Class<? extends T> getComponentClassFromAnnotations4Renew(Class<T> clss) {
        //默认路径下找不到组件.
        for (Class<?> a : compClasses.values()) {
            if (clss.isAssignableFrom(a) && !Modifier.isAbstract(a.getModifiers())) {
                if (log.isDebugEnabled()) log.debug("Get component(" + clss.getName() + ") on renewer is " + a);
                return a.asSubclass(clss);
            }
        }
        return null;
    }

    // --

    @Override
    protected String getComponentName(Class<?> implClass) {
        return (String) getAnnotationProperty(implClass.getAnnotation(compType), "name", "mappedName");
    }

    protected void mappingComponentByInterfaces(Class<?> component, Set<Class<?>> interfaces) {
        if (null == interfaces || interfaces.size() < 1) {
            log.warn("No interfaces specified for Managed component: " + component.getName());
            return;
        }
        String n = getComponentName(component);
        if (null != n && n.length() > 0) n = "." + n;
        String k;
        for (Class<?> c : interfaces) {
            k = c.getName() + n;
            if (!Modifier.isAbstract(component.getModifiers()) || !compClasses.containsKey(k)) {
                log.debug("Mapping managed component(" + component.getName() + ") to interface(" + c.getName() + "), by key (" + k + ") ...");
                compClasses.put(k, component);
            }
        }
    }


    // --

    private <T> T createInstance(Class<T> clss, String name, Object... args) {
        if (null == clss) return null;
        try {
            Object o = ComponentCreator.instance.newObject(clss, name, args);
            ComponentCreator.instance.inject(o, clss);
            if (o instanceof Configurable) ((Configurable) o).init(context, name);
            return clss.cast(o);
        } catch (Exception e) {
            log.warn("Can't create instance of " + clss, e);
            return null;
        }
    }


    // --

}
