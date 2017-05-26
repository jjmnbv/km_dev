package com.kmzyc.framework.container.aop;

import com.kmzyc.framework.container.aop.annotations.Abend;
import com.kmzyc.framework.container.aop.annotations.After;
import com.kmzyc.framework.container.aop.annotations.Before;
import com.kmzyc.framework.container.lang.EventContext;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.MethodFilter;
import com.kmzyc.framework.container.utils.AnnotationHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 缓存器，用于缓存拦截方法。
 *
 * @author weishanyao
 */
public interface AOP {

    enum WeaveType {
        BEFORE(Before.class),
        AFTER(After.class),
        ABEND(Abend.class);
        final public Class<? extends Annotation> annoClass;



        WeaveType(Class<? extends Annotation> type) {
            annoClass = type;
        }
    }

    /**
     * 切点(类及其方法)。
     *
     * @author weishanyao
     */
    class ClassMethod implements java.io.Serializable {
        private final static Log log = LogFactory.getLog(ClassMethod.class);
        private static final long serialVersionUID = 1L;

        private final Class<?> clazz;
        private final Method method;

        ClassMethod(Class<?> clazz, Method method) {
            super();
            this.clazz = clazz;
            this.method = method;
        }

        public Class<?> getOwnClass() {
            return clazz;
        }

        public Method getMethod() {
            return method;
        }

        /**
         * 根据给定的方法过滤器和方法定义从给定的一组类中取得切点方法.
         *
         * @param mf        方法过滤器.
         * @param md        切点方法的定义.
         * @param classes   一组类，该类中定义了切点方法.
         * @param weaveType 编入类型.
         * @return 返回切点（方法及其定义类）.
         */
        public static Collection<ClassMethod> pointcut(MethodFilter mf, MethodDescription md,
                                                       Collection<InstanceClass<?>> classes, WeaveType weaveType) {
            Collection<ClassMethod> cms = new ArrayList<>();
            if (null != classes) for (InstanceClass<?> c : classes) {
                if (log.isDebugEnabled()) log.debug("Checking " + c + " ... ");
                try {
                    for (Method m : AnnotationHelper.getMethods(c.instanceClass, weaveType.annoClass)) {
                        if (log.isDebugEnabled()) log.debug("Checking " + m + " ...");
                        if (mf.accept(m, md)) {
                            if (log.isDebugEnabled()) log.debug("Mapping \""
                                    + m
                                    + "\" of "
                                    + c
                                    + " for \""
                                    + md.getDecClass().getName()
                                    + "."
                                    + md.getName()
                                    + "\" ...");
                            cms.add(new ClassMethod(c.instanceClass, m));
                        }
                    }
                } catch (Exception e) {
                    log.error("Fail to choose aop methods from " + c, e);
                }
            }
            return cms;
        }
    }


    /**
     * 向AOP的Advice发出通知.
     *
     * @param type    编入类型.
     * @param context 事件上下文.
     * @param e       方面发出的异常警告.
     */
    void advise(WeaveType type, EventContext context, Throwable e);
}
