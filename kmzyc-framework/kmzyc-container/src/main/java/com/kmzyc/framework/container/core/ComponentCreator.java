package com.kmzyc.framework.container.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.annotations.Proxy;
import com.kmzyc.framework.container.annotations.ProxyType;
import com.kmzyc.framework.container.aop.AOP;
import com.kmzyc.framework.container.aop.MethodDescription;
import com.kmzyc.framework.container.aop.annotations.Advice;
import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.EventContext;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.MethodFilter;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;
import com.kmzyc.framework.container.utils.AnnotationHelper;
import com.kmzyc.framework.container.utils.GenericHelper;
import com.kmzyc.framework.container.utils.TypeHelper;

/**
 * 组件构建工具.
 *
 * @author weishanyao
 */
class ComponentCreator {
    private static final Log log = LogFactory.getLog(ComponentCreator.class);
    private static final Class<?>[] persistenceClasses;
    private static final Class<Annotation> persistenceContextClass;
    private static final Class<? extends Annotation> injectClass = Resource.class;

    protected static ComponentCreator instance;

    static {
        Class<?>[] clsses;
        Class<Annotation> clss;
        try {
            clsses = new Class[]{Class.forName("javax.persistence.EntityManager"), Connection.class};
            @SuppressWarnings("unchecked")
            Class<Annotation> ac = (Class<Annotation>) Class.forName("javax.persistence.PersistenceContext");
            clss = ac;
        } catch (Exception e) {
            clsses = null;
            clss = null;
        }
        persistenceClasses = clsses;
        persistenceContextClass = clss;
    }

    private final UossContext context;

    protected ComponentCreator(UossContext context) {
        this.context = context;
        instance = this;
    }

    protected Object newObject(Class<?> clss, String name, Object... args) throws Exception {
        Proxy comp = clss.getAnnotation(Proxy.class);
        ProxyType[] pxys = null;
        if (null != comp) pxys = comp.value();
        Map<Class<?>, AnnotatedElement> es = null;
        if (null != persistenceClasses) {
            es = new HashMap<Class<?>, AnnotatedElement>();
            for (Field f : AnnotationHelper.getFields(clss, persistenceContextClass).keySet()) {
                if (ArrayUtils.contains(persistenceClasses, f.getType())) {
                    es.put(f.getType(), f);
                }
            }
            for (Class<?> c : persistenceClasses) {
                for (Method m : AnnotationHelper.getMethods(clss, persistenceContextClass, c)) {
                    es.put(c, m);
                }
            }
        }
        if (null != es && !es.isEmpty()) {
            if (null == pxys || pxys.length < 1) pxys = new ProxyType[]{ProxyType.TRANSACTION};
            else if (Arrays.binarySearch(pxys, ProxyType.TRANSACTION) < 0) {
                ProxyType[] pxs = new ProxyType[pxys.length + 1];
                System.arraycopy(pxys, 0, pxs, 0, pxys.length);
                pxs[pxys.length] = ProxyType.TRANSACTION;
                pxys = pxs;
            }
        }
        if (null != pxys && pxys.length > 0) {
            Object o = newObjectByProxy(clss, pxys, args);
            if (null != es) {
                Object em;
                Class<?> c;
                AnnotatedElement m;
                for (Map.Entry<Class<?>, AnnotatedElement> me : es.entrySet()) {
                    c = me.getKey();
                    m = me.getValue();
                    try {
                        Annotation pc = m.getAnnotation(persistenceContextClass);
                        if (null == pc || "".equals(AnnotationHelper.getAnnotationProperty(pc, "unitName", "name"))) {
                            String pn = clss.getName() + "_persistence_unit_name";
                            if (null != name && name.length() > 0) {
                                pn = context.getProperty(pn + "." + name, context.getProperty(pn));
                            } else pn = context.getProperty(pn);
                            if (null != pn && pn.length() > 0) {
                                final javax.persistence.PersistenceContext origin = (javax.persistence.PersistenceContext) pc;
                                final String pun = pn;
                                pc = new PersistenceContext() {
                                    @Override
                                    public Class<? extends Annotation> annotationType() {
                                        return null == origin ? null : origin.annotationType();
                                    }

                                    @Override
                                    public javax.persistence.PersistenceProperty[] properties() {
                                        return null == origin ? null : origin.properties();
                                    }

                                    @Override
                                    public javax.persistence.PersistenceContextType type() {
                                        return null == origin ? null : origin.type();
                                    }

                                    @Override
                                    public SynchronizationType synchronization() {
                                        return null;
                                    }

                                    @Override
                                    public String unitName() {
                                        return pun;
                                    }

                                    @Override
                                    public String name() {
                                        return pun;
                                    }
                                };
                            }
                        }
                        em = context.newInstance(c, pc);
                    } catch (Exception e) {
                        log.error("No EntityManager found for field: " + m, e);
                        continue;
                    }
                    if (m instanceof Field) {
                        try {
                            ((Field) m).set(o, em);
                        } catch (Exception e) {
                            log.error("Fail to inject EntityManager to field: " + m, e);
                        }
                    } else {//method.
                        try {
                            ((Method) m).invoke(o, em);
                        } catch (Exception e) {
                            log.error("Fail to inject EntityManager to method: " + m, e);
                        }
                    }
                }
            }
            return o;
        } else {
            Constructor<?> c;
            if (null != args && args.length > 0) {
                //Class<?>[] cs = new Class[args.length];
                //for (int i=0; i<args.length; i++) {
                //    if (null==args[i]) cs[i] = null;
                //    else cs[i] = args[i].getClass();
                //}
                c = getConstructor(clss, args);
                if (null != c) return c.newInstance(args);
            } else {
                c = getConstructor(clss, String.class);
                if (null != c) return c.newInstance(name);
            }
            return clss.newInstance();
        }
    }

    /**
     * 一组AOP类过滤器（给定被栏截的类）
     */
    private Map<Class<?>, ClassFilter> _aop_filters = new HashMap<Class<?>, ClassFilter>() {
        private static final long serialVersionUID = 1L;

        @Override
        public ClassFilter get(Object key) {
            if (key instanceof Class) {
                ClassFilter cf = super.get(key);
                if (null == cf) {
                    Class<?> source = (Class<?>) key;
                    try {
                        put(source, cf = context.newInstance(ClassFilter.class, "aop", source));
                    } catch (UossException e) {
                        throw new IllegalStateException(e);
                    }
                }
                return cf;
            }
            throw new IllegalArgumentException("Key " + key + " must is a class!");
        }
    };
    private static Class<?>[] _interfaces = {AOP.class};

    private Object newObjectByProxy(final Class<?> clss, final ProxyType[] pts, final Object... args) throws Exception {
        Enhancer e = new Enhancer();
        e.setSuperclass(clss);
        e.setInterfaces(_interfaces);
        e.setCallback(new MethodInterceptor() {
            private HashMap<AOP.WeaveType, HashMap<MethodDescription, Collection<AOP.ClassMethod>>> methodMap
                    = new HashMap<AOP.WeaveType, HashMap<MethodDescription, Collection<AOP.ClassMethod>>>();
            private Collection<InstanceClass<?>> classes;

            public Object intercept(
                    Object obj,
                    Method method,
                    Object[] args,
                    MethodProxy proxy) throws Throwable {

                if (method.getDeclaringClass() == Object.class) {
                    return proxy.invokeSuper(obj, args);
                }

                String methodName = method.getName();

                if (Modifier.isAbstract(method.getModifiers())) {
                    if ("advise".equals(methodName) && args.length == 3 && args[1] instanceof EventContext) {//AOP.advise
                        AOP.WeaveType weaveType = (AOP.WeaveType) args[0];
                        EventContext ectx = (EventContext) args[1];
                        MethodDescription md = ectx.getMethodDescription();
                        HashMap<MethodDescription, Collection<AOP.ClassMethod>> ms = methodMap.get(weaveType);
                        if (null == ms) {
                            if (null == classes) {
                                classes = context.getInstanceClasses(Object.class, _aop_filters.get(clss), Advice.sorter);
                            }
                            ms = new HashMap<MethodDescription, Collection<AOP.ClassMethod>>();
                            methodMap.put(weaveType, ms);
                        }
                        Collection<AOP.ClassMethod> cms = ms.get(md);
                        if (null == cms) {
                            MethodFilter mf = context.getInstance(MethodFilter.class, weaveType.toString().toLowerCase());
                            ms.put(md, cms = AOP.ClassMethod.pointcut(mf, md, classes, weaveType));
                        }
                        fireAdvice(cms, ectx, (Throwable) args[2]);
                    } else {
                        log.error("No realized method for " + methodName);
                    }
                    return void.class;
                }

                EventContext ctx = new EventContext(obj, new MethodDescription(method), args);
                ctx.setAttribute(EventContext.KEY_UOSS_CONTEXT, context);
                beforeProxyInvoke(ctx, pts);
                try {
                    Object result = proxy.invokeSuper(obj, args);
                    if (null != result) ctx.setAttribute(EventContext.KEY_RETURN_VALUE, result);
                    afterProxyInvoke(ctx, pts);
                    return result;
                } catch (Throwable ex) {
                    abendProxyInvoke(ctx, pts, ex);
                    throw ex;
                }
            }
        });
        if (args.length < 1) return e.create();
        return e.create(getParamTypes(clss, args), args);
    }


    private static void beforeProxyInvoke(EventContext ctx, final ProxyType[] pts) {
        for (ProxyType pt : pts) {
            pt.interceptor.before(ctx);
        }
    }

    private static void afterProxyInvoke(final EventContext ctx,
                                         final ProxyType[] pts) {
        for (int i = pts.length - 1; i >= 0; i--) {
            pts[i].interceptor.after(ctx);
        }
    }

    private static void abendProxyInvoke(final EventContext ctx,
                                         final ProxyType[] pts, Throwable exception) {
        for (int i = pts.length - 1; i >= 0; i--) {
            pts[i].interceptor.abend(ctx, exception);
        }
    }

    /**
     * 通知
     */
    private void fireAdvice(Collection<AOP.ClassMethod> cms, EventContext ectx, Throwable ex) {
        if (null != cms) {
            for (AOP.ClassMethod cm : cms) {
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoking method \"" + cm.getMethod() + "\" of " + cm.getOwnClass());
                    }
                    if (null == ex) cm.getMethod().invoke(context.getInstance(cm.getOwnClass()), ectx);
                    else cm.getMethod().invoke(context.getInstance(cm.getOwnClass()), ectx, ex);
                } catch (Exception e) {
                    log.error("Fail to invoke "
                            + cm.getOwnClass().getName()
                            + "."
                            + cm.getMethod().getName(), e);
                }
            }
        }
    }

    // --

    /**
     * 猜测构造子参数类型
     */
    private static Class<?>[] getParamTypes(Class<?> clss, Object... args) throws Exception {
        Constructor<?> cc = getConstructor(clss, args);
        if (null != cc) return cc.getParameterTypes();
        else throw new Exception("No appropriate Constructor for " + clss);
    }

    private static Constructor<?> getConstructor(Class<?> clss, Object... params) throws Exception {
        int n = null == params ? 0 : params.length;
        if (n == 0) return clss.getConstructor();
        Class<?>[] types;
        for (Constructor<?> cc : clss.getConstructors()) {
            if ((types = cc.getParameterTypes()).length != n) continue;
            for (int i = 0; i < n; i++) {
                if (!TypeHelper.checkAssignable(types[i], params[i])) i = n;
                if (i == n - 1) return cc;
            }
        }
        return null;
    }

    // --

    /**
     * 往对象o(类型为clss)中标有anno的字段注入元素
     */
    protected void inject(Object o, Class<?> clss) {
        if (null == o) return;
        if (null == clss) {
            clss = o.getClass();
            while (Enhancer.isEnhanced(clss)) clss = clss.getSuperclass();
        }
        inject2Field(o, clss);
        inject2Method(o, clss, null);
    }

    /**
     * 往字段注入
     */
    private void inject2Field(Object o, Class<?> clss) {
        Annotation in;
        Class<?> type = null;
        String aname, fname = null;
        Map<Field, Class<?>> fs = AnnotationHelper.getFields(clss, injectClass);
        if (null != fs) for (Field f : fs.keySet()) {
            try {
                type = fs.get(f);
                if (null == type) type = f.getType();
                if (null != f.get(o) && !type.isPrimitive()) continue;
                fname = f.getName();
                if (log.isDebugEnabled())
                    log.debug("Inject to (field: " + clss.getName() + "." + fname + ") class(" + type.getName() + ")");
                in = f.getAnnotation(injectClass);
                aname = (String) AnnotationHelper.getAnnotationProperty(in, "name", "mappedName");
                if (null == aname || aname.length() < 1) aname = fname;

                if (UossContext.class == type) f.set(o, context); //注入上下文
                else if (Properties.class == type) f.set(o, context.getEnvironment()); //注入属性配置
                else if (String.class == type) f.set(o, context.getProperty(aname)); //注入属性配置
                else if (type.isPrimitive()) f.set(o, getPrimitiveProperty(type, context.getProperty(aname)));
                else if (type.isArray())
                    f.set(o, getArrayProperty(type.getComponentType(), StringUtils.split(context.getProperty(aname), ","))); //注入数组
                else {
                    f.set(o, (Boolean) AnnotationHelper.getAnnotationProperty(in, "shareable") ? context.getInstance(type, aname) : context.newInstance(type, aname));
                }
            } catch (Exception e) {
                log.warn("Can't inject " + type.getName() + " to " + clss.getName() + "." + fname + ", cause - " + e.getMessage());
            }
        }
    }

    /**
     * 往方法注入
     */
    private void inject2Method(Object o, Class<?> clss, Map<Type, Class<?>> map) {
        if (null == clss || clss == Object.class) return;
        map = GenericHelper.pickTypeMapped(clss, map);
        for (Method m : clss.getDeclaredMethods()) {
            if (m.isAnnotationPresent(injectClass)) {
                if (!m.isAccessible()) m.setAccessible(true);
                invokeMethod(o, m, m.getAnnotation(injectClass), map);
            }
        }
        inject2Method(o, clss.getSuperclass(), map);
    }

    /**
     * 调用注入方法
     */
    private void invokeMethod(Object obj, Method method, Annotation in, Map<Type, Class<?>> mapping) {
        Type[] ts = method.getGenericParameterTypes();
        Object[] os = new Object[ts.length];
        String mname = method.getName();
        String aname = (String) AnnotationHelper.getAnnotationProperty(in, "name", "mappedName");
        if (null == aname || aname.length() < 1) {
            aname = mname;
            if (aname.startsWith("set")) aname = Character.toLowerCase(aname.charAt(3)) + aname.substring(4);
        }
        Type t;
        Class<?> c;
        for (int i = 0; i < ts.length; i++) {
            t = ts[i];
            if (t instanceof Class) c = (Class<?>) t;
            else {
                c = mapping.get(t);
                if (null == c && t instanceof TypeVariable) {
                    TypeVariable<?> tv = (TypeVariable<?>) t;
                    Type[] bs = tv.getBounds();
                    if (null != bs && bs.length == 1 && (bs[0] instanceof Class<?>)) c = (Class<?>) bs[0];
                }
            }

            if (null != c) {
                if (log.isDebugEnabled())
                    log.debug("Inject to (method: " + method.getDeclaringClass().getName() + "." + mname + "()) class(" + c.getName() + ")");
                try {
                    if (UossContext.class == c) os[i] = context; //注入上下文
                    else if (Properties.class == c) os[i] = context.getEnvironment(); //注入属性配置
                    else if (String.class == c) os[i] = context.getProperty(aname); //注入属性配置
                    else if (c.isPrimitive()) os[i] = getPrimitiveProperty(c, context.getProperty(aname));//基本类型
                    else if (c.isArray())
                        os[i] = getArrayProperty(c.getComponentType(), StringUtils.split(context.getProperty(aname), ","));//数组类型.
                    else if ((String.class == ts[0] || int.class == ts[0] || Integer.class == ts[0]) && ts.length == 2) {
                        //方法setProperty(String|int,Object)注入,参数为一个Map<String,Object>或一个数组.
                        if (i == 0) continue;
                        else {
                            injectMap2Method(obj, method, aname, (Class<?>) ts[0], c);
                            return;
                        }
                    } else {
                        os[i] = (Boolean) AnnotationHelper.getAnnotationProperty(in, "shareable") ? context.getInstance(c, aname) : context.newInstance(c, aname);
                    }
                } catch (Exception e) {
                    log.warn("Can't inject " + c.getName() + " to " + method.getDeclaringClass().getName() + "." + mname + ", cause - " + e.getMessage());
                }
            }
        }
        try {
            method.invoke(obj, os);
        } catch (Exception e) {
            log.error("Fatal to invoke method " + method.getDeclaringClass().getName() + "." + mname, e);
        }
    }

    private void injectMap2Method(Object obj, Method method, String field, Class<?> param1, Class<?> param2) {
        Class<?> clss = obj.getClass();
        while (Enhancer.isEnhanced(clss)) clss = clss.getSuperclass();
        String prefix = clss.getName() + "." + field + ".";
        int pos = prefix.length();
        for (String k : context.getEnvironment().stringPropertyNames()) {
            if (k.startsWith(prefix)) {
                k = k.substring(pos);
                try {
                    method.invoke(obj, param1.getConstructor(String.class).newInstance(k), context.getInstance(param2, k));
                } catch (Exception e) {
                    log.error("Fatal to invoke method " + clss.getName() + "." + method.getName() + ", for property " + k, e);
                }
            }
        }

    }

    private static Object getPrimitiveProperty(Class<?> type, String s) {
        if (boolean.class == type || Boolean.class == type)
            return new Boolean("true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s) || "y".equalsIgnoreCase(s));
        if (int.class == type || Integer.class == type) return StringUtils.isEmpty(s) ? 0 : new Integer(s);
        if (long.class == type || Long.class == type) return StringUtils.isEmpty(s) ? 0l : new Long(s);
        if (double.class == type || Double.class == type) return StringUtils.isEmpty(s) ? 0d : new Double(s);
        if (float.class == type || Float.class == type) return StringUtils.isEmpty(s) ? 0f : new Float(s);
        if (char.class == type || Character.class == type)
            return StringUtils.isEmpty(s) ? new Character('\0') : new Character(s.charAt(0));
        if (short.class == type || Short.class == type) return new Short(StringUtils.isEmpty(s) ? "0" : s);
        if (byte.class == type || Byte.class == type) return new Byte(StringUtils.isEmpty(s) ? "0" : s);
        return StringUtils.isEmpty(s) ? null : s;
    }

    private static Object getArrayProperty(Class<?> type, String[] values) {
        if (null == values) return null;
        for (int i = 0; i < values.length; i++) values[i] = values[i].trim();
        if (String.class == type) return values;
        else if (type.isPrimitive()) {
            if (boolean.class == type || Boolean.class == type) {
                boolean[] bs = new boolean[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = "true".equalsIgnoreCase(values[i]) || "yes".equalsIgnoreCase(values[i]) || "y".equalsIgnoreCase(values[i]);
                return bs;
            }
            if (int.class == type || Integer.class == type) {
                int[] bs = new int[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = StringUtils.isEmpty(values[i]) ? 0 : Integer.parseInt(values[i]);
                return bs;
            }
            if (long.class == type || Long.class == type) {
                long[] bs = new long[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = StringUtils.isEmpty(values[i]) ? 0L : Long.parseLong(values[i]);
                return bs;
            }
            if (double.class == type || Double.class == type) {
                double[] bs = new double[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = StringUtils.isEmpty(values[i]) ? 0d : Double.parseDouble(values[i]);
                return bs;
            }
            if (float.class == type || Float.class == type) {
                float[] bs = new float[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = StringUtils.isEmpty(values[i]) ? 0f : Float.parseFloat(values[i]);
                return bs;
            }
            if (char.class == type || Character.class == type) {
                char[] bs = new char[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = StringUtils.isEmpty(values[i]) ? '\0' : values[i].charAt(i);
                return bs;
            }
            if (short.class == type || Short.class == type) {
                short[] bs = new short[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = StringUtils.isEmpty(values[i]) ? 0 : Short.parseShort(values[i]);
                return bs;
            }
            if (byte.class == type || Byte.class == type) {
                byte[] bs = new byte[values.length];
                for (int i = 0; i < values.length; i++)
                    bs[i] = StringUtils.isEmpty(values[i]) ? 0 : Byte.parseByte(values[i]);
                return bs;
            }
            return null;
        }
        return null;
    }

}
