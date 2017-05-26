package com.quartz.product;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import java.text.ParseException;
import java.util.Date;

/**
 * Quartz Scheduler 管理类
 * 不带groupName参数的方法都采用自动填充默认组的形式 Scheduler.DEFAULT_GROUP
 * @author PigWing
 *
 */
//@Component("schedulerManager")
public class SchedulerManager {
 
//	@Resource(name="quartzScheduler")
    private Scheduler scheduler;
    
 
    public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	/***
     * 修改触发器任务。
     */
    public void modifySimpleTrigger(String triggerName, String triggerGroupName, String jobName, String jobGrourpName,
            Date date) throws SchedulerException, ParseException {
    	try {
            if(StringUtils.isEmpty(triggerName)) {
                throw new RuntimeException("triggerName can not be null");
            }
            JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGrourpName);
            if(jobDetail != null) {
                scheduler.addJob(jobDetail, true);
                SimpleTrigger simpleTrigger = (SimpleTrigger)scheduler.getTrigger(triggerName, triggerGroupName);
                simpleTrigger.setStartTime(date);
                simpleTrigger.setJobName(jobName);
                scheduler.rescheduleJob(simpleTrigger.getName(), simpleTrigger.getGroup(), simpleTrigger);
            }else {
                //logger.error("cant not find jobDetail: " + jobGrourpName);
            }
        }catch(SchedulerException e) {
            //logger.error(e.getMessage());
            throw e;
        }
    }
    
    /**
     * 检查定时任务是否存在
     * @param triggerName
     * @param triggerGroupName
     * @return
     */
    public int checkSimpleTriggerExct(String triggerName,String triggerGroupName) throws Exception
    {
    	  SimpleTrigger simpleTrigger = (SimpleTrigger)scheduler.getTrigger(triggerName, triggerGroupName);
    	  if (simpleTrigger == null)
    	  {
    		 return 0; 
    	  }
    	  else
    	  {
    		  return 1;
    	  }
    }

	/***
     * 增加一个触发器任务,采用默认组形式
     */
    public void addTrigger(String triggerName, String jobName,
            String cronExpression) throws SchedulerException, ParseException {
        addTrigger(triggerName, Scheduler.DEFAULT_GROUP, jobName, Scheduler.DEFAULT_GROUP, cronExpression);
    }
    
    /***
     * 增加一个触发器任务,采用默认组形式
     */
    public void addSimpleTrigger(String triggerName, String triggerGroupName, String jobName, String jobGrourpName,
            Date date) throws SchedulerException, ParseException {
    	try {
            if(StringUtils.isEmpty(triggerName)) {
                throw new RuntimeException("triggerName can not be null");
            }
            JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGrourpName);
            if(jobDetail != null) {
                scheduler.addJob(jobDetail, true);
                SimpleTrigger simpleTrigger = new SimpleTrigger(triggerName, triggerGroupName, date);
                simpleTrigger.setJobName(jobName);
                scheduler.scheduleJob(simpleTrigger);
                scheduler.rescheduleJob(simpleTrigger.getName(), simpleTrigger.getGroup(), simpleTrigger);
            }else {
                //logger.error("cant not find jobDetail: " + jobGrourpName);
            }
        }catch(SchedulerException e) {
            //logger.error(e.getMessage());
            throw e;
        }
    }
 
    /**
     * 
     * 增加一个触发器任务
     */
    public void addTrigger(String triggerName, String triggerGroupName,
            String jobName, String jobGrourpName, String cronExpression) throws SchedulerException, ParseException {
        if(StringUtils.isEmpty(triggerName)) {
            throw new RuntimeException("triggerName can not be null");
        }
         
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGrourpName);
            if(jobDetail != null) {
                scheduler.addJob(jobDetail, true);
                CronTrigger cronTrigger = new CronTrigger(triggerName, triggerGroupName, jobDetail.getName(), jobGrourpName);
       
                cronTrigger.setCronExpression(cronExpression);
                scheduler.scheduleJob(cronTrigger);
                scheduler.rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
            }else {
                //logger.error("cant not find jobDetail: " + jobGrourpName);
            }
        }catch(SchedulerException e) {
            //logger.error(e.getMessage());
            throw e;
        }
    }
 
 
    /**
     * 返回所有触发器信息
     *//*
    public List<Map<String, Object>> getAllTriggers() {
        return quartzDao.getQuartzTriggers();
    }*/
 
    /**
     * 停止触发器
     */
    public void parseTrigger(String triggerName, String groupName)
            throws SchedulerException {
        try {
            scheduler.pauseTrigger(triggerName, groupName);
        }catch(SchedulerException e) {
            //logger.error(e.getMessage());
            throw e;
        }
         
    }
 
    /**
     * 停止触发器,采用默认组形式
     */
    public void parseTrigger(String triggerName) throws SchedulerException {
        parseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
 
    }
 
    /**
     * 重启触发器
     */
    public void resumeTrigger(String triggerName, String groupName)
            throws SchedulerException {
        try {
            scheduler.resumeTrigger(triggerName, groupName);
        }catch(SchedulerException e) {
            //logger.error(e.getMessage());
            throw e;
        }
         
 
    }
 
    /**
     * 重启触发器,采用默认组形式
     */
    public void resumeTrigger(String triggerName) throws SchedulerException {
        resumeTrigger(triggerName, Scheduler.DEFAULT_GROUP);
    }
 
    /**
     * 移除触发器
     */
    public boolean removeTrigger(String triggerName, String groupName)
            throws SchedulerException {
        try {
            parseTrigger(triggerName, groupName);
            return scheduler.unscheduleJob(triggerName, groupName);
        }catch(SchedulerException e) {
            //logger.error(e.getMessage());
            throw e;
        }
    }
 
    /**
     * 移除触发器,采用默认组形式
     */
    public boolean removeTrigger(String triggerName) throws SchedulerException {
        try {
            return removeTrigger(triggerName, Scheduler.DEFAULT_GROUP);
        }catch(SchedulerException e) {
            //logger.error(e.getMessage());
            throw e;
        }
    }
 
    /**
     * 返回所有的任务名称
     */
    public String[] getJobNames(String groupName) throws SchedulerException {
        return scheduler.getJobNames(groupName);
    }
 
    public String[] getJobNames() throws SchedulerException {
        return scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
    }
 
     
}
