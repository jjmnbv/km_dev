package com.kmzyc.framework.container.persistence;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;

import org.springframework.cglib.proxy.Enhancer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.annotations.Component;
import com.kmzyc.framework.container.lang.InstanceClass;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.lang.UossException;
import com.kmzyc.framework.container.utils.AnnotationHelper;

/**
 * 事务工厂.
 *
 * @author weishanyao
 */
public abstract class TransactionFetcher {
    private final static Log log = LogFactory.getLog(TransactionFetcher.class);

    protected static Set<TransactionFetcher> fetchers;


    public static synchronized List<LocalTransaction> getTransactions(String[] punits, TransactionAttribute ta, UossContext context) {
        List<LocalTransaction> trans;
        if (null == punits || punits.length == 0 || (punits.length == 1 && StringUtils.isEmpty(punits[0]))) {
            trans = getTransactionsByPersistenceUnitName(null, ta, context);
        } else {
            trans = new ArrayList<LocalTransaction>(getTransactionFetchers(context).size());
            for (int i = 0; i < punits.length; i++) {
                trans.addAll(getTransactionsByPersistenceUnitName(punits[i].trim(), ta, context));
            }
        }
        return trans;
    }

    public static String getPersistenceUnitName(UossContext context, Object source, String defaultUnitName) {
        if ("".equals(defaultUnitName)) defaultUnitName = null;
        if (null == defaultUnitName) {
            if (null != source) {
                Class<?> c = source.getClass();
                while (Enhancer.isEnhanced(c)) c = c.getSuperclass();
                @SuppressWarnings("unchecked")
                Annotation a = AnnotationHelper.getAnnotation(c, Resource.class, Component.class); //TODO: 未取自属性配置文件.
                String name = null == a ? null : (String) AnnotationHelper.getAnnotationProperty(a, "name", "mappedName");
                String pn = c.getName() + "_persistence_unit_name";
                if (null != name && name.length() > 0) pn += "." + name;
                pn = context.getProperty(pn);
                if (null != pn && pn.length() > 0) {
                    defaultUnitName = pn;
                }
            }
        }
        return defaultUnitName;
    }


    // --

    /**
     * 根据一个持久单元名称取回一个对应的事务.
     *
     * @param persistenceUnitName 持久单元名称.
     */
    public abstract LocalTransaction getTransaction(String persistenceUnitName, TransactionAttribute ta);

    /**
     * 判断当前TransactionFetcher是否有效.
     */
    public abstract boolean isActive();

    // --

    ///** 添加一个持久化单元 */
    //protected abstract void addPersistenceUnitName(String persistenceUnitName);

    // --

    private static synchronized List<LocalTransaction> getTransactionsByPersistenceUnitName(String persistenceUnitName, TransactionAttribute ta, UossContext context) {
        if ("".equals(persistenceUnitName)) persistenceUnitName = null;
        List<LocalTransaction> trans = new ArrayList<LocalTransaction>(getTransactionFetchers(context).size());
        for (TransactionFetcher fetcher : getTransactionFetchers(context)) {
            LocalTransaction t = fetcher.getTransaction(persistenceUnitName, ta);
            if (null != t) trans.add(t);
        }
        return trans;
    }

    private synchronized static Set<TransactionFetcher> getTransactionFetchers(UossContext context) {
        if (null == fetchers) {
            fetchers = Collections.synchronizedSet(new HashSet<TransactionFetcher>());
            try {
                for (InstanceClass<? extends TransactionFetcher> tc : context.getInstanceClasses(TransactionFetcher.class, null, null)) {
                    try {
                        if (null != tc) {
                            TransactionFetcher fetcher = context.getInstance(tc.instanceClass, tc.name);
                            if (fetcher.isActive()) {
                                fetchers.add(fetcher);
                            }
                        }
                    } catch (Throwable e) {
                        log.error("Fail to create TransactionFetcher for " + tc.instanceClass + ", because of: " + e);
                    }
                }
            } catch (UossException e) {
                log.error("Fail to get class of TransactionFetcher", e);
            }
        }
        return fetchers;
    }
}
