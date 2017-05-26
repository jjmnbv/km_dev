package com.kmzyc.framework.container.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.Configurable;
import com.kmzyc.framework.container.lang.Container;
import com.kmzyc.framework.container.lang.Destroyable;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.ObjectFactory;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;


/**
 * 统一支持平台引擎。.
 * 统一支持平台引擎是系统的工厂的统一配置中心和创建中心。.
 *
 * @author weishanyao
 */
public final class UossEngine implements ObjectFactory {
    private final static Log log = LogFactory.getLog(UossEngine.class);
    public final static String INIT_PROPERTIES_FILE = "uoss-config.properties";
    public final static String DEFAULT_COMPONENT_KEY = "default.component.category";

    private final ComponentCreator cc;

    private final Properties props = new Properties();

    private final Map<Class<?>, Map<String, Object>> instances
            = new HashMap<Class<?>, Map<String, Object>>();

    private final Container[] containers;
    private final Renewer[] renewers;

    public UossEngine() {
        props.putAll(System.getProperties());
        cc = new ComponentCreator(context);
        containers = new Container[]{new ConfigureInstances(cc, context)};
        renewers = new Renewer[]{(Renewer) containers[0]};
    }

    public UossEngine(Container[] instances) {
        cc = new ComponentCreator(context);
        props.putAll(System.getProperties());
        instance = this;
        int i = 0;
        for (Container cnr : instances) {
            if (null != cnr) {
                try {
                    instances[i++] = cnr;
                    if (cnr instanceof Configurable) ((Configurable) cnr).init(context, null);
                } catch (UossException e) {
                    log.error("Can't init Container " + cnr, e);
                }
            }
        }
        containers = new Container[i + 1];
        containers[0] = new ConfigureInstances(cc, context);
        System.arraycopy(instances, 0, containers, 1, i);
        Renewer[] renews = null;
        for (Object o : containers) {
            if (o instanceof Renewer) {
                renews = null == renews ? new Renewer[]{(Renewer) o} : (Renewer[]) ArrayUtils.add(renews, o);
            }
        }
        renewers = renews;
    }

    @Override
    public void init(InputStream is) {
        if (null != is)
            try {
                props.load(is);
            } catch (IOException e) {
            }
    }

    @Override
    public void init(String file) {
        if (null == file || file.length() == 0) init();
        else {
            try {
                init(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void init() {
        init(getClass().getResourceAsStream("/" + INIT_PROPERTIES_FILE));
    }

    @Override
    public void init(Map<String, String> overrides) {
        init();
        if (null != overrides)
            props.putAll(overrides);
    }

    @Override
    public void init(String file, Map<String, String> overrides) {
        init(file);
        if (null != overrides)
            props.putAll(overrides);
    }

    @Override
    public <T> T newInstance(Class<T> clazz, Object... args) throws UossException {
        return newInstance(clazz, null, args);
    }

    @Override
    public <T> T newInstance(Class<T> clazz, final String name, Object... args) throws UossException {
        try {
            if (null != containers) {//多样化组件，支持EJB3组件.
                T t;
                for (Container cnr : containers) {
                    if (null != (t = cnr.newInstance(clazz, name, args))) return t;
                }
            }
            Class<?> clss;
            if (!Modifier.isAbstract(clazz.getModifiers())) clss = clazz;
            else {
                if (null != renewers) {
                    T t;
                    for (Renewer rr : renewers) {
                        if (null != (t = rr.renew(clazz, name, args))) return t;
                    }
                }
                String pname = clazz.getPackage().getName();
                String sname = clazz.getSimpleName();
                if ((sname.startsWith("Abstract") && Character.isUpperCase(sname.charAt(8)))) {
                    sname = sname.substring(8);
                } else if ((sname.charAt(0) == 'I' && Character.isUpperCase(sname.charAt(1)))) {
                    sname = sname.substring(1);
                }
                clss = getClassByName(pname + ".Default" + sname);
                if (null == clss) clss = getClassByName(pname + ".Default" + sname + "Impl");
                if (null == clss) clss = getClassByName(pname + ".impl.Default" + sname);
                if (null == clss) clss = getClassByName(pname + ".impl.Default" + sname + "Impl");
                if (null == clss) clss = getClassByName(pname + "." + sname + "Impl");
                if (null == clss) clss = getClassByName(pname + ".impl." + sname + "Impl");
                if (null != clss) {
                    if (log.isDebugEnabled()) log.debug("Guess a implement of \"" + clazz.getName() + "\": " + clss);
                } else throw new UossException("No implement for " + clazz.getName() + "!");
            }
            try {
                return initInstance(clazz.cast(cc.newObject(clss, name, args)), name);
            } catch (Exception e) {
                log.warn("Can't create instance of " + clss, e);
            }
            throw new UossException("No instance found for " + clazz.getName() + "!");
        } catch (Exception e) {
            if (e instanceof UossException) throw (UossException) e;
            else throw new UossException("Can't create " + clazz.getName() + "!", e);
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz, Object... args) throws UossException {
        return getInstance(clazz, null, args);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name, Object... args) throws UossException {
        synchronized (clazz) {
            Map<String, Object> map = instances.get(clazz);
            if (null == map) {
                instances.put(clazz, map = new HashMap<String, Object>());
            }
            Object o = map.get(name);
            if (null != o) return clazz.cast(o);
            T t;
            if (null != containers) {//多样化组件，支持EJB3组件.
                for (Container cnr : containers) {
                    if (null != (t = cnr.getInstance(clazz, name, args))) return t;
                }
            }
            t = newInstance(clazz, name, args);
            map.put(name, t);
            return t;
        }
    }

    @Override
    public <T> T initInstance(T instance, String name) throws UossException {
        if (null != instance) {
            cc.inject(instance, null);
            if (instance instanceof Configurable) {
                ((Configurable) instance).init(getUossContext(), name);
            }
        }
        return instance;
    }

    /**
     * 只适用元数据配置的情况.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass, ClassFilter filter, Comparator<InstanceClass<?>> comp) throws UossException {
        ArrayList<InstanceClass<? extends T>> list = new ArrayList<InstanceClass<? extends T>>();
        if (null != containers) {//多样化组件，支持EJB3组件.
            Collection<InstanceClass<? extends T>> l;
            for (Container cnr : containers) {
                if (null != (l = cnr.getInstanceClasses(ifClass, filter, comp))) {
                    list.addAll(l);
                }
            }
        }
        if (null != comp && !list.isEmpty()) Collections.sort(list, comp);
        return list;
    }

    private final UossContext context = new UossContext() {
        private Map<String, Object> attributes = Collections.synchronizedMap(new HashMap<String, Object>());

        @Override
        public Properties getEnvironment() {
            return props;
        }

        @Override
        public String getProperty(String key) {
            return props.getProperty(key);
        }

        @Override
        public String getProperty(String key, String def) {
            return props.getProperty(key, def);
        }

        @Override
        public int getProperty(String key, int def) {
            return NumberUtils.toInt(props.getProperty(key), def);
        }

        @Override
        public boolean isTrue(String key) {
            return "true".equals(props.getProperty(key));
        }

        @Override
        public long getProperty(String key, long def) {
            return NumberUtils.toLong(props.getProperty(key), def);
        }

        @Override
        public <T> T getInstance(Class<T> clazz, Object... args) throws UossException {
            return UossEngine.this.getInstance(clazz, args);
        }

        @Override
        public <T> T newInstance(Class<T> clazz, Object... args) throws UossException {
            return UossEngine.this.newInstance(clazz, args);
        }

        @Override
        public <T> T getInstance(Class<T> clazz, String name, Object... args)
                throws UossException {
            return UossEngine.this.getInstance(clazz, name, args);
        }

        @Override
        public <T> T newInstance(Class<T> clazz, String name, Object... args)
                throws UossException {
            return UossEngine.this.newInstance(clazz, name, args);
        }

        @Override
        public <T> T initInstance(T instance, String name) throws UossException {
            return UossEngine.this.initInstance(instance, name);
        }

        @Override
        public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass, ClassFilter filter, Comparator<InstanceClass<?>> comp)
                throws UossException {
            return UossEngine.this.getInstanceClasses(ifClass, filter, comp);
        }

        @Override
        public Object getAttribute(String key) {
            return attributes.get(key);
        }

        @Override
        public Object removeAttribute(String key) {
            return attributes.remove(key);
        }

        @Override
        public Object setAttribute(String key, Object value) {
            return attributes.put(key, value);
        }

        @Override
        public void debug(String msg) {
            log.debug(msg);
        }

        @Override
        public void info(String msg) {
            log.info(msg);
        }

        @Override
        public void error(String msg, Exception exception) {
            log.error(msg, exception);
        }

        @Override
        public void warn(String msg, Exception exception) {
            log.warn(msg, exception);
        }
    };

    @Override
    public UossContext getUossContext() {
        return context;
    }

    @Override
    public void destroy() {
        for (Container c : containers) {
            if (c instanceof Destroyable) {
                ((Destroyable) c).destroy();
            }
        }
        for (Map<String, Object> m : instances.values()) {
            for (Object o : m.values()) {
                if (o instanceof Destroyable) {
                    ((Destroyable) o).destroy();
                }
            }
        }
    }

    private static ObjectFactory instance;

    public static ObjectFactory getObjectFactory() {
        if (null == instance) {
            instance = new UossEngine();
            instance.init();
        }
        return instance;
    }

    private static Class<?> getClassByName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            if (log.isDebugEnabled()) log.warn("Class: \"" + name + "\" not found!");
            return null;
        }
    }

}
