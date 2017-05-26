package com.kmzyc.framework.rule;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kie.internal.KnowledgeBase;

/**
 * 基于Drools的规则启动器.
 */
public class RuleLauncher  {
    final private static Log log = LogFactory.getLog(RuleLauncher.class);
    
	/**
	 * 规则处理上下文.
	 */
    protected ProcessContext processContext;
    @Resource protected ScheduledExecutorService scheduledExecutor;
    @Resource protected KnowledgeBase knowledgeBase;


	public Processor launch(int startStatus, Object... params) {
	    Processor p = new Processor(processContext,knowledgeBase);
	    p.setCurrentStatus(startStatus);
        return p.fireRule(params);
	}
    
    public void launchByAsyn(final int second, final int startStatus, final Object... params) {
        Runnable r = new Runnable() {
            public void run() {
                Processor p = new Processor(processContext,knowledgeBase);
                p.setCurrentStatus(startStatus);
                p.fireRule(params);
            }
        };
        if (second<1) scheduledExecutor.execute(r);
        else scheduledExecutor.schedule(r,second,TimeUnit.SECONDS);
    }
}
