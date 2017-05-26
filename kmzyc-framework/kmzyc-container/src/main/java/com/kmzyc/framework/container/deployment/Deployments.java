package com.kmzyc.framework.container.deployment;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import com.kmzyc.framework.container.core.CustContainer;
import com.kmzyc.framework.container.lang.Container;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 组件部署中心.
 *
 * @author weishanyao
 */
public class Deployments {
    private static final Log log = LogFactory.getLog(Deployments.class);

    public final static String KEY_PREFIX_CONFIGHANDLER_CLASS = "handler:";

    public final static String KEY_PREFIX_SCANHANDER_CLASS = "com.km.framework.container.deployment.";
    public final static String KEY_SUFFIX_SCANHANDER_CLASS = ".ScanHander.class";

    public final static String KEY_SCANNER_CLASS = "com.km.framework.container.deployment.Scanner";

    public final static String KEY_CUST_CONTAINER_CLASS = "com.km.framework.container.cust.Container.classes";

    public static final String KEY_APP_ROOT = "com.km.framework.container.app.root";
    public static final String KEY_FACTORY = "component.factory";

    public final static String KEY_RESOURCE_NAMES = "com.km.framework.container.deployment.resourceNames";

    public final static String[] RESOURCE_NAMES = {"META-INF/component.factory.properties", "META-INF/ejb-jar.xml"};


    private static final String DEFAULT_FACTORY = "com.km.framework.container.core.UossEngine";

    private final Map<String, ConfigHandler> configs = new HashMap<String, ConfigHandler>();
    private final Map<String, ScanHandler> handlers = new HashMap<String, ScanHandler>();

    private final ArrayList<File> dirs = new ArrayList<File>();
    private final ClassLoader classLoader;
    private final Hashtable<Object, Object> internalEnvironment;
    private final Hashtable<Object, Object> externalEnvironment;

    public Deployments(ClassLoader classLoader, Hashtable<Object, Object> env) {
        super();
        this.classLoader = classLoader;
        this.externalEnvironment = env;
        this.internalEnvironment = new Properties();
        this.internalEnvironment.putAll(env);
        initScanHandlers();
        initConfigHandlers();
    }


    public Deployments addFile(File file) {
        if (null != file) dirs.add(file);
        return this;
    }

    /**
     * 扫描资源，并返回组件工厂
     */
    public Object scan() {
        getScanner().scanResources(configs);
        if (!dirs.isEmpty()) getScanner().scanDirectories(dirs);
        return newComponentFactory();
    }


    private Scanner getScanner() {
        String scannerClass = (String) internalEnvironment.get(KEY_SCANNER_CLASS);
        if (null != scannerClass) {
            try {
                Class<?> clazz = classLoader.loadClass(scannerClass);
                Constructor<?> ctr = clazz.getConstructor(ClassLoader.class, Map.class);
                return (Scanner) ctr.newInstance(classLoader, handlers); //XXX:使用构造函数作为接口没有约束意义!
            } catch (Throwable e) {
                log.warn("Can't load scannerClass \"" + scannerClass + "\", cause - " + e + ", use default!");
            }
        }
        return new URLScanner(classLoader, handlers);
    }


    private void initScanHandlers() {
        handlers.put("class", new ClassScanHandler(internalEnvironment));
        handlers.put("xml", new XmlScanHandler(internalEnvironment));
        if (null != internalEnvironment) {
            String key;
            Class<?> c;
            ScanHandler h;
            int prel = KEY_PREFIX_SCANHANDER_CLASS.length();
            int sufl = KEY_SUFFIX_SCANHANDER_CLASS.length();
            for (Map.Entry<?, ?> me : internalEnvironment.entrySet()) {
                if ((key = (String) me.getKey()).endsWith(KEY_SUFFIX_SCANHANDER_CLASS)
                        && key.startsWith(KEY_PREFIX_SCANHANDER_CLASS)) {
                    key = key.substring(prel, key.length() - sufl);
                    try {
                        c = classLoader.loadClass((String) me.getValue());
                        handlers.put(key.toLowerCase(), h = (ScanHandler) c.newInstance());
                        h.init(internalEnvironment);
                    } catch (Exception e) {
                        log.error("Can't create ScanHandler(" + me.getValue() + ") for " + key, e);
                    }
                }
            }
        }
    }

    private void initConfigHandlers() {
        String[] ss = StringUtils.split((String) internalEnvironment.get(KEY_RESOURCE_NAMES), ",");
        if (null == ss || ss.length < 1) {
            configs.put(RESOURCE_NAMES[0], new PropertiesConfigHandler(internalEnvironment));
            configs.put(RESOURCE_NAMES[1], new EJBConfigHandler(internalEnvironment));
        } else {
            for (int i = 0; i < ss.length; i++) {
                ss[i] = ss[i].trim();
                configs.put(ss[i], newConfigHandler((String) internalEnvironment.get(KEY_PREFIX_CONFIGHANDLER_CLASS + ss[i])));
            }
        }
    }

    /**
     * 创建配置处理器
     */
    private ConfigHandler newConfigHandler(String className) {
        if (StringUtils.isBlank(className)) return null;
        try {
            Class<?> clazz = classLoader.loadClass(className.trim());
            ConfigHandler ch = (ConfigHandler) clazz.newInstance();
            ch.init(internalEnvironment);
            return ch;
        } catch (Exception e) {
            log.error("Can't create ConfigHandler(className:" + className + ")", e);
            return null;
        }
    }

    /**
     * 创建统一引擎
     */
    private Object newComponentFactory() {
        String ftry = (String) internalEnvironment.get(KEY_FACTORY);
        if (StringUtils.isBlank(ftry)) ftry = DEFAULT_FACTORY;
        if (null != externalEnvironment && !externalEnvironment.isEmpty()) {
            internalEnvironment.putAll((Map<?, ?>) externalEnvironment);
        }
        try {
            Class<?> c = classLoader.loadClass(ftry);
            Constructor<?> cttr = c.getConstructor(Container[].class);
            Object o = cttr.newInstance(marge(handlers.get("class").getResult(), custContainers()));
            if (null != o) {
                try {
                    Method m = o.getClass().getMethod("init", Map.class);
                    m.invoke(o, internalEnvironment);
                } catch (Exception ex) {
                }
            }
            return o;
        } catch (Exception ex) {
            throw new IllegalStateException("No instance for: " + ftry, ex);
        }
    }

    /**
     * 实例化客户容器
     */
    private Container[] custContainers() {
        String[] ccs = StringUtils.split((String) internalEnvironment.get(KEY_CUST_CONTAINER_CLASS), ",");
        if (null == ccs) return null;
        CustContainer[] cctrs = new CustContainer[ccs.length];
        String cc;
        Class<?> c;
        for (int i = 0; i < ccs.length; i++) {
            cc = ccs[i];
            if (null == cc) continue;
            cc = cc.trim();
            if (0 == cc.length()) continue;
            try {
                c = classLoader.loadClass(cc);
                if (CustContainer.class.isAssignableFrom(c)) {
                    cctrs[i] = (CustContainer) c.newInstance();
                    cctrs[i].init(internalEnvironment);
                } else log.warn("Can't create CustContainer, cause: class " + cc + " is not a CustContainer!");
            } catch (Throwable e) {
                log.warn("Can't create CustContainer for classname: " + cc + ", cause: " + e.getMessage());
            }
        }
        return cctrs;
    }

    /**
     * 合并两个数组
     */
    private static Object marge(Object sysContainers, Container[] custContainers) {
        if (null == custContainers || custContainers.length == 0) return sysContainers;
        if (sysContainers instanceof Container[]) {
            Container[] os = (Container[]) sysContainers;
            if (os.length == 0) return custContainers;
            else {
                Container[] result = new Container[os.length + custContainers.length];
                System.arraycopy(os, 0, result, 0, os.length);
                System.arraycopy(custContainers, 0, result, os.length, custContainers.length);
                return result;
            }
        } else if (sysContainers instanceof Collection) {
            Collection<?> cs = (Collection<?>) sysContainers;
            int size = cs.size();
            if (size == 0) return custContainers;
            else {
                Container[] result = new Container[size + custContainers.length];
                int i = 0;
                for (Object o : cs) result[i++] = (Container) o;
                System.arraycopy(custContainers, 0, result, i, custContainers.length);
                return result;
            }
        } else if (sysContainers instanceof Container) {
            Container[] result = new Container[1 + custContainers.length];
            result[0] = (Container) sysContainers;
            System.arraycopy(custContainers, 0, result, 1, custContainers.length);
            return result;
        } else return custContainers;
    }
}
