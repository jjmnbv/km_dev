package com.kmzyc.framework.container.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.kmzyc.framework.container.core.CustContainer;
import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.Configurable;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;

/**
 * Spring容器.
 *
 * @author weishanyao
 */
public class SpringContainer implements CustContainer, Configurable {
    private static final Log log = LogFactory.getLog(SpringContainer.class);

    public final static String KEY_SPRING_APPLICATION_CONTEXT = ContextLoader.CONFIG_LOCATION_PARAM;

    private UossContext context;
    private ApplicationContext ac;
    private final HashSet<String> initeds = new HashSet<String>();

    @Override
    public void init(Hashtable<Object, Object> env) {
        String[] ss = StringUtils.split((String) env.get(KEY_SPRING_APPLICATION_CONTEXT), ",");
        if (null != ss && ss.length > 0) {
            for (int i = 0; i < ss.length; i++) {
                ss[i] = ss[i].trim();
            }
            Properties props;
            if (env instanceof Properties) {
                props = (Properties) env;
            } else {
                props = new Properties();
                props.putAll(env);
            }
            ac = new XmlApplicationContext(ss, props);
        }
    }

    @Override
    public void init(UossContext context, String name) throws UossException {
        this.context = context;
    }

    @Override
    public <T> T getInstance(Class<T> clazz, Object... args)
            throws UossException {
        if (null == ac) return null;
        if (ApplicationContext.class == clazz) return clazz.cast(ac);
        String[] ss = ac.getBeanNamesForType(clazz);
        if (null == ss || ss.length < 1) {
            log.debug("No bean found for specify class: " + clazz.getName());
            return null;
        }
        if (ss.length > 1) {
            log.warn("Found multi- implements from " + clazz.getName() + ", get the first for you!");
        }
        String name = ss[0];
        try {
            T t = clazz.cast(ac.getBean(name));
            if (t instanceof Configurable && !initeds.contains(name)) {
                ((Configurable) t).init(context, name);
                initeds.add(name);
            }
            return t;
        } catch (org.springframework.beans.factory.NoSuchBeanDefinitionException e) {
            log.debug(e.getMessage() + " for " + clazz);
            return null;
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name, Object... args)
            throws UossException {
        if (null == ac) return null;
        if (ApplicationContext.class == clazz) return clazz.cast(ac);
        if (StringUtils.isEmpty(name)) {
            return getInstance(clazz);
        } else {
            try {
                T t = clazz.cast(ac.getBean(name, clazz));
                if (t instanceof Configurable && !initeds.contains(name)) {
                    ((Configurable) t).init(context, name);
                    initeds.add(name);
                }
                return t;
            } catch (org.springframework.beans.factory.NoSuchBeanDefinitionException e) {
                log.debug(e.getMessage() + " for " + clazz);
                return null;
            }
        }
    }

    @Override
    public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(
            Class<T> ifClass, ClassFilter filter, Comparator<InstanceClass<?>> comp)
            throws UossException {
        if (null == ac) return null;
        String[] ss = ac.getBeanNamesForType(ifClass);
        if (null == ss || ss.length < 1) {
            return null;
        }
        ArrayList<InstanceClass<? extends T>> list = new ArrayList<InstanceClass<? extends T>>(ss.length);
        Class<?> c;
        InstanceClass<T> ic;
        for (String s : ss) {
            if (null != (c = ac.getType(s)) && (null == filter || filter.accept(c, s))) {
                ic = new InstanceClass<T>(c.asSubclass(ifClass), s);
                if (!list.contains(ic)) list.add(ic);
            }
        }
        if (null != comp && !list.isEmpty()) Collections.sort(list, comp);
        return list;
    }

    @Override
    public <T> T newInstance(Class<T> clazz, Object... args)
            throws UossException {
        return getInstance(clazz, args);
    }

    @Override
    public <T> T newInstance(Class<T> clazz, String name, Object... args)
            throws UossException {
        return getInstance(clazz, name, args);
    }

}
