package com.kmzyc.framework.container.interceptors;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kmzyc.framework.container.lang.Configurable;
import com.kmzyc.framework.container.lang.EventContext;
import com.kmzyc.framework.container.lang.Interceptor;
import com.kmzyc.framework.container.lang.UossContext;
import com.kmzyc.framework.container.persistence.LocalTransaction;
import com.kmzyc.framework.container.persistence.TransactionException;
import com.kmzyc.framework.container.persistence.TransactionFetcher;

/**
 * 声明式事务处理监听器.
 *
 * @author weishanyao
 */
public class TransactionInterceptor implements Interceptor, Configurable {
    private final static Log log = LogFactory.getLog(TransactionInterceptor.class);
    private final static String STATUS_KEY = "PERSISTENCE_UNIT_NAME:";

    private static enum STATUS {NEW_CONNECTION, NEW_TRANSACTION}

    private UossContext context;

    @Override
    public void init(UossContext context, String name) {
        this.context = context;
    }

    @Override
    public void abend(EventContext context, Throwable exception) {
        log.error("Cautch some exception in method \"" + context.getMethodDescription().getFullName() + "\", rollback the transaction. (if any)", exception);
        //TransactionAttribute ta = context.getAnnotation(TransactionAttribute.class);
        List<LocalTransaction> ts = TransactionFetcher.getTransactions(getPersistenceUnits(context),
                context.getAnnotation(TransactionAttribute.class), this.context);
        if (null != ts) {
            for (LocalTransaction t : ts) {
                if (null != t) abend4Transaction(context, t.persistenceUnitName(), t);
            }
        }
    }

    @Override
    public void after(EventContext context) {
        //TransactionAttribute ta = context.getAnnotation(TransactionAttribute.class);
        List<LocalTransaction> ts = TransactionFetcher.getTransactions(getPersistenceUnits(context),
                context.getAnnotation(TransactionAttribute.class), this.context);
        if (null != ts) {
            for (LocalTransaction t : ts) {
                if (null != t) after4Transaction(context, t.persistenceUnitName(), t);
            }
        }
        if (log.isDebugEnabled())
            log.debug("Exit method \"" + context.getMethodDescription().getFullName() + "\" - O.K.");
    }

    @Override
    public void before(EventContext context) {
        if (log.isDebugEnabled()) log.debug("Enter " + context.getMethodDescription().getFullName() + " ...");
        TransactionAttributeType type = TransactionAttributeType.REQUIRED;
        TransactionAttribute ta = context.getAnnotation(TransactionAttribute.class);
        if (null != ta) {
            type = ta.value();
        }
        List<LocalTransaction> ts = TransactionFetcher.getTransactions(getPersistenceUnits(context),
                context.getAnnotation(TransactionAttribute.class), this.context);
        if (null != ts) {
            for (LocalTransaction t : ts) {
                if (null != t) before4Transaction(context, type, t.persistenceUnitName(), t);
            }
        }
    }

    // --

    private static void abend4Transaction(EventContext context, String persistenceUnitName, LocalTransaction t) {
        //persistenceUnitName = getPersistenceUnitName(context, persistenceUnitName);
        String key = STATUS_KEY + "[" + t.getClass().getName() + "]." + persistenceUnitName;
        if (t.isActive()) {
            Object nt = context.getAttribute(key);
            if (STATUS.NEW_TRANSACTION == nt) try {
                t.rollbackTransaction();
            } catch (Exception e) {
                log.error("Can't rollback transaction!", e);
            }
        } else t.setKeepConnect(false);
        context.removeAttribute(key);
    }

    private static void after4Transaction(EventContext context, String persistenceUnitName, LocalTransaction t) {
        //persistenceUnitName = getPersistenceUnitName(context, persistenceUnitName);
        String key = STATUS_KEY + "[" + t.getClass().getName() + "]." + persistenceUnitName;
        Object status = context.getAttribute(key);
        if (log.isDebugEnabled()) {
            log.debug("Exitting method \"" + context.getMethodDescription().getFullName() +
                    "\" with status - t.isActive():" + t.isActive() + "; t.isKeepConnect():" +
                    t.isKeepConnect() + "; newTransaction:" + (STATUS.NEW_TRANSACTION == status ? "false" : "true") +
                    "; newConnection:" + (STATUS.NEW_CONNECTION == status ? "false" : "true") + " ...");
        }
        if (STATUS.NEW_TRANSACTION == status || STATUS.NEW_CONNECTION == status) {
            if (t.isActive()) {
                if (log.isDebugEnabled()) log.debug("Finishing the transaction ...");
                try {
                    t.endTransaction();
                    if (log.isDebugEnabled())
                        log.debug("Finished the transaction - O.K. method: \"" +
                                context.getMethodDescription().getFullName() + "\" is over");
                } catch (Exception e) {
                    //if (log.isDebugEnabled()) log.debug("Can't commit transaction!", e);
                    context.removeAttribute(key); //防止重复回滚.
                    throw new TransactionException("Can't commit transaction!", e);
                }
            } else if (t.isKeepConnect()) {
                if (log.isDebugEnabled())
                    log.debug("Disconnec database after " + context.getMethodDescription().getFullName());
                t.setKeepConnect(false);
            }
            context.removeAttribute(key);
        }
    }

    private static void before4Transaction(EventContext context, TransactionAttributeType type,
                                           String persistenceUnitName, LocalTransaction t) {
        //persistenceUnitName = getPersistenceUnitName(context, persistenceUnitName);
        String key = STATUS_KEY + "[" + t.getClass().getName() + "]." + persistenceUnitName;
        if (type == TransactionAttributeType.SUPPORTS) {
            if (t.isActive()) {
                if (log.isDebugEnabled())
                    log.debug("Joinning a exist transaction for " + context.getMethodDescription().getFullName() + " ...");
            } else if (!t.isKeepConnect()) {
                if (log.isDebugEnabled())
                    log.debug("Begin connect to database for " + context.getMethodDescription().getFullName() + " ...");
                context.setAttribute(key, STATUS.NEW_CONNECTION);
                t.setKeepConnect(true);
            }
        } else if (type == TransactionAttributeType.REQUIRED) {
            if (log.isDebugEnabled())
                log.debug("Request a transaction for " + context.getMethodDescription().getFullName() + " ...");
            if (!t.isActive()) {
                if (log.isDebugEnabled())
                    log.debug("There was not exist transaction, create a new transaction for " +
                            context.getMethodDescription().getFullName() + " ...");
                try {
                    t.beginTransaction();
                    context.setAttribute(key, STATUS.NEW_TRANSACTION);
                    if (log.isDebugEnabled()) log.debug("Began a new transaction - O.K.");
                } catch (Exception e) {
                    log.error("Fail to begin a new transaction!", e);
                }
            }
        } else if (type == TransactionAttributeType.NEVER) {
            if (t.isActive()) throw new TransactionException("A transaction was actived!");
        } else if (type == TransactionAttributeType.REQUIRES_NEW) {
            if (log.isDebugEnabled())
                log.debug("Beginning a new transaction for " + context.getMethodDescription().getFullName() + " ...");
            if (t.isActive()) {
                if (log.isWarnEnabled()) log.warn("There was a old transaction, rollback it!");
                try {
                    t.rollbackTransaction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                t.beginTransaction();
                context.setAttribute(key, STATUS.NEW_TRANSACTION);
                if (log.isDebugEnabled()) log.debug("Began a new transaction - O.K.");
            } catch (Exception e) {
                log.error("Fail to begin a new transaction!", e);
            }
        } else if (type == TransactionAttributeType.NOT_SUPPORTED) {
            //TODO: NOT_SUPPORTED
        } else if (type == TransactionAttributeType.MANDATORY) {
            if (null != t && !t.isActive())
                throw new TransactionException("Transaction is required!");
        }
    }

    private static String getPersistenceUnitName(EventContext context, String persistenceUnitName) {
        return TransactionFetcher.getPersistenceUnitName((UossContext) context.getAttribute(EventContext.KEY_UOSS_CONTEXT),
                context.getSource(), persistenceUnitName);
    }

    private static String[] getPersistenceUnits(EventContext context) {
        PersistenceUnit punit = context.getAnnotation(PersistenceUnit.class);
        if (null == punit) {
            String s = getPersistenceUnitName(context, null);
            if (null == s) return null;
            else return new String[]{s};
        }
        String persistenceUnitNames = punit.unitName();
        if ("".equals(persistenceUnitNames)) persistenceUnitNames = punit.name();
        return persistenceUnitNames.split(",");
    }
}
