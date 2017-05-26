package com.kmzyc.framework.rule;

import java.util.Collection;
import java.util.Comparator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;

public abstract class ProcessContextImpl implements ProcessContext {
    final private static Log log = LogFactory.getLog(ProcessContextImpl.class);

    private final UossContext context;

    public ProcessContextImpl(UossContext context) {
        this.context = context;
    }

    public Properties getEnvironment() {
        return context.getEnvironment();
    }

    @Override
    public String getProperty(String key, String def) {
        return context.getProperty(key, def);
    }

    @Override
    public int getProperty(String key, int def) {
        return context.getProperty(key, def);
    }

    @Override
    public long getProperty(String key, long def) {
        return context.getProperty(key, def);
    }

    @Override
    public String getProperty(String key) {
        return context.getProperty(key);
    }

    @Override
    public boolean isTrue(String key) {
        return context.isTrue(key);
    }

    @Override
    public <T> T initInstance(T instance, String name) throws UossException {
        return context.initInstance(instance, name);
    }

    @Override
    public void debug(String msg) {
        context.debug(msg);
    }

    @Override
    public void info(String msg) {
        context.info(msg);
    }

    @Override
    public void error(String msg, Exception exception) {
        context.error(msg, exception);
    }

    @Override
    public void warn(String msg, Exception exception) {
        context.warn(msg, exception);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, Object... args) throws UossException {
        return context.getInstance(clazz, args);
    }

    @Override
    public <T> T newInstance(Class<T> clazz, Object... args) throws UossException {
        return context.newInstance(clazz, args);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name, Object... args) throws UossException {
        return context.getInstance(clazz, name, args);
    }

    @Override
    public <T> T newInstance(Class<T> clazz, String name, Object... args) throws UossException {
        return context.newInstance(clazz, name, args);
    }

    @Override
    public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass, ClassFilter filter,
                                                                         Comparator<InstanceClass<?>> comp) throws UossException {
        return context.getInstanceClasses(ifClass, filter, comp);
    }

    @Override
    public int getStartStatus() {
        return Processor.START;
    }

    @Override
    public int getSystemId() {
        return context.getProperty("system.id", 0);
    }

    @Override
    public Object setAttribute(String key, Object value) {
        return context.setAttribute(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        return context.getAttribute(key);
    }

    @Override
    public Object removeAttribute(String key) {
        return context.removeAttribute(key);
    }

}
