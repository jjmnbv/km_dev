package com.kmzyc.framework.container.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;


/**
 * Annotation帮助类。
 *
 * @author weishanyao
 */
public class AnnotationHelper {
    private static Map<Class<? extends Annotation>, Map<Class<?>, Map<Field, Class<?>>>> fields
            = Collections.synchronizedMap(new HashMap<Class<? extends Annotation>, Map<Class<?>, Map<Field, Class<?>>>>());
    private static Map<Class<? extends Annotation>, Map<Class<?>, Set<Method>>> methods
            = Collections.synchronizedMap(new HashMap<Class<? extends Annotation>, Map<Class<?>, Set<Method>>>());

    /**
     * 取回clazz（包括父类）中所有标有annotationClass的属性.
     *
     * @param clazz           给定的类.
     * @param annotationClass 属性标注.
     * @return 返回一个Map，其键为找到的属性对象，值为属性的值类型，如果该属性为非泛型类型，则为null.
     */
    public static Map<Field, Class<?>> getFields(Class<?> clazz,
                                                 Class<? extends Annotation> annotationClass) {
        if (null == clazz || clazz == Object.class) return null;

        Map<Class<?>, Map<Field, Class<?>>> cache = fields.get(annotationClass);
        Map<Field, Class<?>> fs = null;
        if (null == cache) fields.put(annotationClass, cache = new HashMap<Class<?>, Map<Field, Class<?>>>());
        else fs = cache.get(clazz);

        if (null == fs) {
            fs = new HashMap<Field, Class<?>>();
            pickFeilds(fs, clazz, annotationClass, null);
            Map<Type, Class<?>> atas = GenericHelper.pickTypeMapped(clazz, null);
            cache.put(clazz, fs = getFields(clazz.getSuperclass(), annotationClass, fs, atas));
        }

        return Collections.unmodifiableMap(fs);
    }

    private static Map<Field, Class<?>> getFields(Class<?> clazz,
                                                  Class<? extends Annotation> annotationClass, Map<Field, Class<?>> fs, Map<Type, Class<?>> atas) {
        if (null == clazz || clazz == Object.class) return fs;
        pickFeilds(fs, clazz, annotationClass, atas);
        GenericHelper.pickTypeMapped(clazz, atas);
        return getFields(clazz.getSuperclass(), annotationClass, fs, atas);
    }

    // --

    /**
     * 取回clazz（包括父类）中所有标有annotationClass的方法.
     *
     * @param clazz           给定的类.
     * @param annotationClass 方法标注.
     * @return 返回一个符合条件的方法的集合.
     */
    public static Set<Method> getMethods(Class<?> clazz,
                                         Class<? extends Annotation> annotationClass) {
        if (null == clazz || clazz == Object.class) return null;

        Map<Class<?>, Set<Method>> cache = methods.get(annotationClass);
        Set<Method> ms = null;
        if (null == cache) methods.put(annotationClass, cache = new HashMap<Class<?>, Set<Method>>());
        else ms = cache.get(clazz);

        if (null == ms) {
            ms = new HashSet<Method>();
            for (Method m : clazz.getDeclaredMethods()) {
                if (null != annotationClass) {
                    if (m.isAnnotationPresent(annotationClass)) {
                        if (!m.isAccessible()) m.setAccessible(true);
                        ms.add(m);
                    }
                }
            }
            cache.put(clazz, ms = getMethods(clazz.getSuperclass(), annotationClass, ms));
        }

        return Collections.unmodifiableSet(ms);
    }

    private static Set<Method> getMethods(Class<?> clazz, Class<? extends Annotation> annotationClass, Set<Method> ms) {
        if (null == clazz || clazz == Object.class) return ms;
        for (Method m : clazz.getDeclaredMethods()) {
            if (null != annotationClass) {
                if (m.isAnnotationPresent(annotationClass)) {
                    if (!m.isAccessible()) m.setAccessible(true);
                    ms.add(m);
                }
            }
        }
        return getMethods(clazz.getSuperclass(), annotationClass, ms);
    }

    /**
     * 取回clazz（包括父类）中所有标有annotationClass且与给定参数类型相匹配的方法.
     *
     * @param clazz           给定的类.
     * @param annotationClass 方法标注.
     * @param paramClass      参数类型.
     * @return 返回一个符合条件的方法的集合.
     */
    public static Set<Method> getMethods(Class<?> clazz, Class<? extends Annotation> annoClass, Class<?>... paramClass) {
        Set<Method> ms = getMethods(clazz, annoClass);
        Set<Method> set = new HashSet<Method>();
        for (Method m : ms) {
            if (Arrays.equals(m.getParameterTypes(), paramClass)) set.add(m);
        }
        return set;
    }

    /**
     * 取得Annotation的属性的值.
     *
     * @param ann      Annotation实例.
     * @param property 第一个属性，如果此属性指定了值，则直接将此值返回.
     * @param another  备选属性，如果第一个属性没有指定值，则从这些属性中返回一个.
     * @return 返回属性的配置的值.
     */
    public static Object getAnnotationProperty(Annotation ann, String property, String... another) {
        try {
            Method m = ann.getClass().getMethod(property);
            Object o = m.invoke(ann);
            if (another.length > 0 && "".equals(o)) {
                for (String name : another) {
                    try {
                        m = ann.getClass().getMethod(name);
                        o = m.invoke(ann);
                        if (!"".equals(o)) return o;
                    } catch (Exception e) {
                    }
                }
            }
            return o;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取回所有标注的元数据中的其中之一.
     *
     * @param clazz 目标类.
     * @param annos 元数据.
     * @return 返回其中之一的元数据配置，否则返回<code>null</code>.
     */
    public static Annotation getAnnotation(Class<?> clazz, Class<? extends Annotation>... annos) {
        while (Enhancer.isEnhanced(clazz)) clazz = clazz.getSuperclass();
        Annotation a;
        for (Class<? extends Annotation> ca : annos) {
            if (null != (a = clazz.getAnnotation(ca))) return a;
        }
        return null;
    }


    // --

    /**
     * 组识属性及其泛型类型（实参）的映射.
     *
     * @param pocket    一个结果集，方法结束后将传回此集合.
     * @param target    主体类.
     * @param annoClass 标注类.
     * @param mapped    泛型形参与实参的映射.
     */
    private static void pickFeilds(Map<Field, Class<?>> pocket, Class<?> target, Class<? extends Annotation> annoClass, Map<Type, Class<?>> mapped) {
        for (Field f : target.getDeclaredFields()) {
            if (null != annoClass) {
                if (f.isAnnotationPresent(annoClass)) {//System.out.println("==== "+mapped);
                    if (!f.isAccessible()) f.setAccessible(true);
                    Class<?> c;
                    Type t = null;
                    if (null != mapped) t = mapped.get(f.getGenericType());
                    if (t instanceof Class) c = (Class<?>) t;
                    else c = f.getType();
                    pocket.put(f, c);
                }
            }
        }
    }


}
