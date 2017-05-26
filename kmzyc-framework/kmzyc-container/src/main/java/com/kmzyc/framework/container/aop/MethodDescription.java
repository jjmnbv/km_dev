package com.kmzyc.framework.container.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * 方法规格。
 * @author weishanyao
 *
 */
public class MethodDescription implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 参数类型.
     */
    private final Class<?>[] argTypes;
    
    /**
     * 注解类型.
     */
    private final Annotation[] annotans;
    
    /**
     * 声明类.
     */
    private final Class<?> decClass;
    
    /**
     * 方法名.
     */
    private final String name;
    
    public MethodDescription(Method method) {
        super();
        argTypes = method.getParameterTypes();
        annotans = method.getAnnotations();
        decClass = method.getDeclaringClass();
        name = method.getName();
    }

    
    public Class<?>[] getArgTypes() {
        return argTypes;
    }
    public Annotation[] getAnnotans() {
        return annotans;
    }
    public Class<?> getDecClass() {
        return decClass;
    }
    public String getName() {
        return name;
    }
    public String getFullName() {
        return decClass.getSimpleName()+"."+name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result+Arrays.hashCode(argTypes);
        result = prime*result+((name==null)? 0: name.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null) return false;
        if (getClass()!=obj.getClass()) return false;
        final MethodDescription other = (MethodDescription)obj;
        if (!Arrays.equals(argTypes,other.argTypes)) return false;
        if (name==null) {
            if (other.name!=null) return false;
        }
        else if (!name.equals(other.name)) return false;
        return true;
    }


    @Override
    public String toString() {
        String s = getClass().getName()+" - method: "+getFullName();
        s += "\n\targs: "+Arrays.toString(argTypes);
        s += "\n\tannotations: "+Arrays.toString(annotans);
        return s;
    }

    
}
