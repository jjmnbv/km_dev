package com.kmzyc.framework.container.lang;

public class InstanceClass<T> {
    public final Class<? extends T> instanceClass;
    public final String name;
    public final int index;
    public InstanceClass(Class<? extends T> instanceClass, String name) {
        this(instanceClass,name,-1);
    }
    public InstanceClass(Class<? extends T> instanceClass, String name, int index) {
        this.instanceClass = instanceClass;
        this.name = name;
        this.index = index;
    }
    @Override
    public int hashCode() {
        return instanceClass.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof InstanceClass) {
            return instanceClass.equals(((InstanceClass<?>)obj).instanceClass);
        }
        return false;
    }
    @Override
    public String toString() {
        return "InstanceClass ["+instanceClass + ", name=" + name + ", index=" + index + "]";
    }
    
}
