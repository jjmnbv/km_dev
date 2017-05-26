package com.kmzyc.framework.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.core.event.ProcessEvent;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import com.kmzyc.framework.container.lang.Commands;
import com.kmzyc.framework.container.lang.UossException;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 规则处理器.
 * <p>
 * Processer是规则处理的核心组件，该处理器接收一个{@link ProcessContext}，然后通过{@link #fireRule(Object...)}来驱动规则.
 * 注意：
 * 规则处理器不是一个线程安全的对象，一个规则流程处理完毕，该对象便被丢弃，交由JVM作为垃圾回收。
 * 但是，作为Processer的上下文环境，{@link ProcessContext}却是线程安全的，尽管Processer无法在多个线程间共享数据，
 * 但可通过其持有共享的{@link ProcessContext}来实现多个线程间的数据共享.
 */
public class Processor extends Commands implements Runnable {
    final private static Log log = LogFactory.getLog(Processor.class);
    final private static byte[] lock = new byte[0];
    final private ScheduledExecutorService delayExecutor;
    private static Map<String, Object> config;
    private FactHandle factHandle;
    private StatefulKnowledgeSession ksession;
    private Object processResult;
    private Future<?> future;

    /**
     * 当前状态，该状态由上游规则来维护，并作为下游规则的进入条件.
     */
    private int currentStatus;

    private boolean rollback;

    /**
     * 处理开始状态，常量
     */
    public final static int START = 0;

    /**
     * 处理结束状态，常量
     */
    public final static int END = 99;

    /**
     * 未知异常结束
     */
    public final static int EXCEPTION = -1;

    /**
     * 构造器，只能由 来构造.
     *
     * @param context 规则处理器的上下文环境.
     */
    public Processor(ProcessContext context, KnowledgeBase knowledgeBase) {
        super(context, false);
        ScheduledExecutorService ses = null;
        if (null != context) {
            currentStatus = context.getStartStatus();
            try {
                ses = context.getInstance(ScheduledExecutorService.class, "scheduledExecutor");
            } catch (UossException e) {
                log.error("Failed to get ScheduledExecutorService instance!", e);
            }
        }
        if (null == ses) ses = Executors.newSingleThreadScheduledExecutor();
        this.delayExecutor = ses;
        this.ksession = knowledgeBase.newStatefulKnowledgeSession();
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int status) {
        currentStatus = status;
    }

    /**
     * 属性集
     */
    public Map<?, ?> attributes() {
        return attrs;
    }

    public Map<String, Object> getConfig() {
        if (null == config) {
            synchronized (lock) {
                config = new Hashtable<String, Object>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public synchronized Object get(Object key) {
                        if (!containsKey(key) && null != context) {
                            Object o = context.getProperty((String) key);
                            if (null == o) o = Boolean.FALSE;
                            else if ("true".equals(o) || "false".equals(o)) {
                                o = new Boolean((String) o);
                            }
                            put((String) key, o);
                        }
                        return super.get(key);
                    }
                };
            }
        }
        return config;
    }

    /**
     * 处理规则，每个规则处理完成前应该调用此方法.
     */
    @Override
    public Object process(final Object... args) {

        try {
            processResult = super.process(args);
        } catch (Exception e) {
            log.error("Occur exception(s) on execute command!", e);
            currentStatus = EXCEPTION;
            if (e instanceof ProcessException) {
                ProcessException pe = (ProcessException) e;
                processResult = pe.getProcessResult();
                e = new ProcessException(pe.getMessage(), pe.getCause(), pe.code);
            } else processResult = e;
            ksession.insert(e);
        } finally {
            try {
                if (processResult instanceof ProcessResult) ksession.insert(processResult);
                else if (processResult instanceof ProcessException) {
                    ProcessException pe = (ProcessException) processResult;
                    ksession.insert(pe.getProcessResult());
                    ksession.insert(new ProcessException(pe.getMessage(), pe.getCause(), pe.code));
                } else if (processResult instanceof Object[]) {
                    boolean done = true;
                    for (Object o : (Object[]) processResult) {
                        if (o instanceof ProcessResult) {
                            ksession.insert(o);
                            done = false;
                        } else if (o instanceof ProcessException) {
                            ProcessException pe = (ProcessException) o;
                            ksession.insert(pe.getProcessResult());
                            ksession.insert(new ProcessException(pe.getMessage(), pe.getCause(), pe.code));
                            done = false;
                        }
                    }
                    if (done && currentStatus == 2) {
                        currentStatus++; //退出到"完成"
                    }
                } else {
                    if (currentStatus == 2 && !(processResult instanceof Exception)) {
                        currentStatus++; //退出到"完成"
                    }
                }
            } finally {
                ksession.update(factHandle, this);
            }
        }
        return processResult;
    }

    public boolean isRollbackTransaction() {
        return rollback;
    }


    public void done(String... msg) {
        if (null != ksession) {
            try {
                ksession.dispose();
            } catch (Exception e) {
            }
        }

    }

    public void dispose() {
        if (null != ksession) {
            try {
                ksession.dispose();
                ksession = null;
                if (null != future) {
                    future.cancel(true);
                }
            } catch (Exception e) {
            }
        }
    }

    public void run() {
        if (null != ksession) {
            try {
                ksession.dispose();
                ksession = null;
            } catch (Exception e) {
            }
        }
    }

    protected Object getProcessResult() {
        return processResult;
    }

    /**
     * 驱动规则，只能由 来调用.
     *
     * @param msg 消息(M).
     * @return 返回当前对象.
     */
    protected Processor fireRule(Object... msg) {
        try {
            factHandle = ksession.insert(this);
            if (null != msg) {
                for (Object o : msg) {
                    ksession.insert(o);
                }
            }
            ksession.fireAllRules();
        } catch (Throwable t) {
            log.error("Occur exception(s) on fire rule!", t);
            currentStatus = EXCEPTION;
        } finally {
            try {
                future = delayExecutor.schedule(this, 10, TimeUnit.SECONDS);
            } catch (RejectedExecutionException e) {

            }
        }
        return this;
    }

    protected void notify(ProcessEvent event) {
    }

}
