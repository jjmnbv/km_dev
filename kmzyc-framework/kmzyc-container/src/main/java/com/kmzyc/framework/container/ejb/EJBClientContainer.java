package com.kmzyc.framework.container.ejb;

import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.core.CustContainer;
import com.kmzyc.framework.container.lang.ClassFilter;
import com.kmzyc.framework.container.lang.Destroyable;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.UossException;

/**
 * 不依赖于扫描策略的EJB容器.
 *
 * @author weishanyao
 */
public class EJBClientContainer implements CustContainer, Destroyable {
    final static private Log log = LogFactory.getLog(EJBClientContainer.class);

    private InitialContext ctx = null;

    @Override
    public <T> T getInstance(Class<T> ifClass, Object... param) throws UossException {
        return newInstance(ifClass, param);
    }

    @Override
    public <T> T getInstance(Class<T> ifClass, String name, Object... param) throws UossException {
        return newInstance(ifClass, name, param);
    }

    @Override
    public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T> ifClass, ClassFilter classFilter, Comparator<InstanceClass<?>> comparator) throws UossException {
        return null;
    }

    @Override
    public <T> T newInstance(Class<T> ifClass, Object... param) throws UossException {
        return null;
    }

    @Override
    public <T> T newInstance(Class<T> ifClass, String name, Object... param) throws UossException {
        if (null == name || !name.startsWith("ejb:")) return null;
        name = name.substring(4);
        try {
            return ifClass.cast(ctx.lookup(name + "#" + ifClass.getName()));
        } catch (NamingException e) {
            log.error("Fail to look up EJB " + ifClass.getName() + " for " + name, e);
            return null;
        }
    }

    @Override
    public void init(Hashtable<Object, Object> env) {
        try {
            ctx = new InitialContext(env);
        } catch (NamingException e) {
            log.error("Fail to initial context", e);
        }
    }

    @Override
    public void destroy() {
        try {
            ctx.close();
        } catch (NamingException e) {
            log.error("Can not close init context", e);
        }
    }

}
