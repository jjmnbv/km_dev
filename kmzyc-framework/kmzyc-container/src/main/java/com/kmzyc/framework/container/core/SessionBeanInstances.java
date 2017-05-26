package com.kmzyc.framework.container.core;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.ejb.EJB;
import com.kmzyc.framework.container.ejb.EjbDescriptor;
import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.UossException;


/**
 * 会话BEAN管理器.
 *
 * @author weishanyao
 * @version $2013-8-6 上午10:36:10$
 */
public abstract class SessionBeanInstances extends AbstractInstances implements EJB {
    private static final Log log = LogFactory.getLog(SessionBeanInstances.class);

    protected final Map<String, String> map = new HashMap<String, String>();

    private final Map<Class<?>, Map<String, Object>> instances
            = new HashMap<Class<?>, Map<String, Object>>();

    public <T> T newInstance(final Class<T> clazz, final String name, final Object... args) throws UossException {
        if (clazz.isInterface()) {
            String jndi = getJndiString(clazz, name);
            if (null == jndi) {
                if (log.isDebugEnabled())
                    log.debug("No session bean for interface(" + clazz.getName() + "), JNDI not found!");
                return null;
            }
            if (log.isDebugEnabled()) log.debug("Get session bean (" + clazz + ") by JNDI " + jndi);
            InitialContext ctx = null;
            try {
                Object o = (ctx = new InitialContext(context.getEnvironment())).lookup(jndi);
                if (null == o) {
                    log.warn("No session bean (" + clazz + ") found by JNDI " + jndi + " !");
                    return null;
                }
                return context.initInstance(clazz.cast(o), name);
            } catch (Exception e) {
                log.error("Fatal to lookup session bean (" + clazz + ") by JNDI \"" + jndi + "\"!", e);
            } finally {
                if (null != ctx) {
                    try {
                        ctx.close();
                    } catch (Exception e) {
                    }
                }
            }
            if (StringUtils.isEmpty(name) || (!name.endsWith("remote") && !name.endsWith("local"))) {
                String n = name;
                if (StringUtils.isEmpty(n)) n = "remote";
                else n = n + ".remote";
                T t = newInstance(clazz, n, args);
                if (null == t) {
                    n = name;
                    if (StringUtils.isEmpty(n)) n = "local";
                    else n = n + ".local";
                    return newInstance(clazz, n, args);
                }
            }
        }
//        else {
//            log.debug("Class("+clazz.getName()+") must is a interface!");
//            return null;
//        }
        return null;
    }

    public <T> T getInstance(Class<T> clazz, String name, Object... args) throws UossException {
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


    public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass,
                                                                         ClassFilter filter, Comparator<InstanceClass<?>> comp) {
        return null;
    }

    // --


    public void parse(Class<?> implClass, Hashtable<?, ?> env) {
        mappingBusinessInterface(implClass,
                new MBI(getComponentName(implClass),
                        getJndiString(implClass, Range.LOCAL, env),
                        getJndiString(implClass, Range.REMOTE, env),
                        true));
    }

    // --


    public void configure(EjbDescriptor descriptor, Hashtable<?, ?> env) {
        String name = descriptor.getEjbName();
        String ejbName = name;
        if (StringUtils.isBlank(ejbName)) {
            ejbName = descriptor.getEjbClassName();
            int i = ejbName.lastIndexOf('.');
            if (i > -1) ejbName.substring(i + 1);
            i = ejbName.lastIndexOf('$');
            if (i > -1) ejbName.substring(i + 1);
        }
        if (null != descriptor.getLocalInterfaces()) {
            String jndiLocal = getJndiString(ejbName, Range.LOCAL, env);
            for (String cn : descriptor.getLocalInterfaces()) {
                mappingBusinessInterface(cn, name, Range.LOCAL, jndiLocal);
            }
        }
        if (null != descriptor.getRemoteInterfaces()) {
            String jndiRemote = getJndiString(ejbName, Range.REMOTE, env);
            for (String cn : descriptor.getRemoteInterfaces()) {
                mappingBusinessInterface(cn, name, Range.REMOTE, jndiRemote);
            }
        }
    }


    protected String getEjbName(Class<?> implClass) {
        String n = getComponentName(implClass);
        if (null == n || n.length() < 1) {
            n = implClass.getSimpleName();
            int i = n.lastIndexOf('$');
            if (i > -1) n = n.substring(i + 1);
        }
        return n;
    }

    /**
     * 根据给定的接口类和实现类取得JNDI(扫描类时使用)
     */
    protected String getJndiString(Class<?> implClass, Range type, Hashtable<?, ?> env) {
        return getJndiString(getEjbName(implClass), type, env);
    }

    protected String getJndiString(String ejbName, Range type, Hashtable<?, ?> env) {
        String jndiPattern = null;
        if (Range.REMOTE == type) {
            jndiPattern = (String) env.get(KEY_JNDI_REMOTE_PATTERN);
            if (null == jndiPattern) jndiPattern = (String) env.get(KEY_JNDI_PATTERN);
            if (null == jndiPattern) return ejbName + "Remote";
        } else {
            jndiPattern = (String) env.get(KEY_JNDI_LOCAL_PATTERN);
            if (null == jndiPattern) jndiPattern = (String) env.get(KEY_JNDI_PATTERN);
            if (null == jndiPattern) return ejbName + "Local";
        }
        return jndiPattern.replace("#{ejbName}", ejbName);
    }

    /**
     * 根据给定的接口类和SESSION BEAN名字取得JNDI(客户端使用)
     */
    protected String getJndiString(Class<?> ifClass, String ejbName) {
        String className = ifClass.getName();
        String category = context.getProperty(UossEngine.DEFAULT_COMPONENT_KEY, null);
        String cname = category;
        String jndi = null;
        if (null != cname && cname.length() > 0) {
            if (null != ejbName && ejbName.length() > 0
                    && !ejbName.equals(cname)
                    && !ejbName.startsWith(cname + ".")) {
                cname = cname + "." + ejbName;
            }
        } else cname = ejbName;

        if (null != cname && cname.length() > 0) {
            jndi = map.get(className + "." + cname);
            if (null == jndi && cname != category && null != category) jndi = map.get(className + "." + category);
            if (null == jndi) jndi = map.get(className);

            if (null == jndi) jndi = map.get(className + "." + cname + ".remote");
            if (null == jndi) jndi = map.get(className + "." + cname + ".local");

            if (null == jndi && cname != category && null != category)
                jndi = map.get(className + "." + category + ".remote");
            if (null == jndi && cname != category && null != category)
                jndi = map.get(className + "." + category + ".local");

            if (null == jndi) jndi = map.get(className + ".remote");
            if (null == jndi) jndi = map.get(className + ".local");
        } else {
            jndi = map.get(className);
            if (null == jndi) jndi = map.get(className + ".remote");
            if (null == jndi) jndi = map.get(className + ".local");
        }
        return jndi;
    }

    /**
     * 提交会话BEAN的业务接口（本地接口和远程接口）.
     *
     * @param implClass 会话BEAN的实现类.
     * @param mbi       一个javaBean，保存组件名，本地JNDI和远程JNDI配置，以及一个唯一的可以写入的locale属性.
     */
    protected void mappingBusinessInterface(Class<?> implClass, MBI mbi) {
        javax.ejb.Remote remote = implClass.getAnnotation(javax.ejb.Remote.class);
        javax.ejb.Local local = implClass.getAnnotation(javax.ejb.Local.class);
        if (null != local && local.value().length > 0) {
            mbi.locable = false; //是否允许缺省的本地接口.
            for (Class<?> ic : local.value()) {
                mappingBusinessInterface(ic, mbi.name, Range.LOCAL, mbi.jndiLocal);
            }
        }
        if (null != remote) {
            if (remote.value().length == 0) {
                Class<?> i = getFirstInterface(implClass);
                if (null != i) mappingBusinessInterface(i, mbi.name, Range.REMOTE, mbi.jndiRemote);
            } else for (Class<?> ic : remote.value()) {
                mappingBusinessInterface(ic, mbi.name, Range.REMOTE, mbi.jndiRemote);
            }
        }
        mappingBIfromInterfaces(implClass, mbi);
        mappingLocalBIByDefault(implClass, mbi);
    }

    /**
     * 迭代地读取本地接口和远程接口.
     *
     * @param clazz 当前类（迭代地读取父类）.
     * @param mbi   一个javaBean，保存组件名，本地JNDI和远程JNDI配置，以及一个唯一的可以写入的locale属性.
     */
    protected void mappingBIfromInterfaces(Class<?> clazz, MBI mbi) {
        if (null != clazz && Object.class != clazz) {
            mappingBIfromInterfaces(clazz.getInterfaces(), mbi);
            mappingBIfromInterfaces(clazz.getSuperclass(), mbi);
        }
    }

    /**
     * 迭代地读取本地接口和远程接口.
     *
     * @param clazz 当前继承层次的所有接口.
     * @param mbi   一个javaBean，保存组件名，本地JNDI和远程JNDI配置，以及一个唯一的可以写入的locale属性.
     */
    protected void mappingBIfromInterfaces(Class<?>[] ifs, MBI mbi) {
        if (null == ifs || ifs.length < 1) return;
        for (Class<?> f : ifs) {
            if (!f.getName().startsWith("java")) {
                boolean remote = f.isAnnotationPresent(javax.ejb.Remote.class);
                boolean local = f.isAnnotationPresent(javax.ejb.Local.class);
                if (!local && !remote) {
                    mappingBIfromInterfaces(f.getInterfaces(), mbi);
                } else {
                    if (local) {
                        mappingBusinessInterface(f, mbi.name, Range.LOCAL, mbi.jndiLocal);
                        mbi.locable = false;
                    }
                    if (remote) mappingBusinessInterface(f, mbi.name, Range.REMOTE, mbi.jndiRemote);
                }
            }
        }
    }

    /**
     * 迭代地读取缺省的本地接口（根据规范，当没有显式地声明{@link javax.ejb.Local}时默认第一个接口为本地接口）.
     *
     * @param clazz 当前类（迭代地读取父类）.
     * @param mbi   一个javaBean，保存组件名，本地JNDI和远程JNDI配置，以及一个唯一的可以写入的locale属性.
     */
    protected void mappingLocalBIByDefault(Class<?> clazz, MBI mbi) {
        if (mbi.locable && null != clazz && Object.class != clazz) {
            mappingLocalBIByDefault(clazz.getInterfaces(), mbi);
            mappingLocalBIByDefault(clazz.getSuperclass(), mbi);
        }
    }

    protected void mappingLocalBIByDefault(Class<?>[] ifs, MBI mbi) {
        if (!mbi.locable || null == ifs || ifs.length < 1) return;
        for (Class<?> f : ifs) {
            if (!f.getName().startsWith("java")) {
                if (!f.isAnnotationPresent(javax.ejb.Remote.class)
                        && !f.isAnnotationPresent(javax.ejb.Local.class)) {
                    mappingBusinessInterface(f, mbi.name, Range.LOCAL, mbi.jndiLocal);
                    mbi.locable = false;
                    return;
                }
            }
        }
    }

    // --

    protected void mappingBusinessInterface(Class<?> ifClass, final String cName, Range type, String jndi) {
        mappingBusinessInterface(ifClass.getName(), cName, type, jndi);
    }

    protected void mappingBusinessInterface(String ifClass, final String cName, Range type, String jndi) {
        String n = cName;
        if (null != n && n.length() > 0) n = "." + n;
        String k = ifClass + n;
        //if (log.isDebugEnabled()) log.debug("Mapping session bean(JNDI: "+jndi+") to interface("+ifClass+"), type: "+type+", name: "+n+" ...");
        if (!map.containsKey(ifClass) && n != cName) {
            if (log.isDebugEnabled())
                log.debug("Mapping session bean(JNDI: " + jndi + ") to interface(" + ifClass + "), by default key ...");
            map.put(ifClass, jndi);
        }
        if (Range.REMOTE == type && n != cName && !map.containsKey(ifClass + ".remote")) {
            if (log.isDebugEnabled())
                log.debug("Mapping session bean(JNDI: " + jndi + ") to interface(" + ifClass + "), by key default(" + ifClass + ".remote) ...");
            map.put(ifClass + ".remote", jndi);
        }
        if (Range.LOCAL == type && n != cName && !map.containsKey(ifClass + ".local")) {
            if (log.isDebugEnabled())
                log.debug("Mapping session bean(JNDI: " + jndi + ") to interface(" + ifClass + "), by key default(" + ifClass + ".local) ...");
            map.put(ifClass + ".local", jndi);
        }
        //if (log.isDebugEnabled()) log.debug("Mapping session bean(JNDI: "+jndi+") to interface("+ifClass+"), type: "+type+", name: "+n+", map.get("+k+")="+map.get(k)+" ...");
        if (map.containsKey(k)) k = k + "." + type.toString().toLowerCase();
        if (log.isDebugEnabled())
            log.debug("Mapping session bean(JNDI: " + jndi + ") to interface(" + ifClass + "), by key (" + k + ") ...");
        map.put(k, jndi);
    }

    private static Class<?> getFirstInterface(Class<?> clazz) {
        if (null == clazz || Object.class == clazz) return null;
        Class<?>[] ifs = clazz.getInterfaces();
        if (null != ifs && ifs.length > 0) return ifs[0];
        else return getFirstInterface(clazz.getSuperclass());
    }

    private class MBI {
        private final String name;
        private final String jndiLocal;
        private final String jndiRemote;
        private boolean locable;

        public MBI(String name, String jndiLocal, String jndiRemote, boolean locable) {
            super();
            this.name = name;
            this.jndiLocal = jndiLocal;
            this.jndiRemote = jndiRemote;
            this.locable = locable;
        }
    }
}
