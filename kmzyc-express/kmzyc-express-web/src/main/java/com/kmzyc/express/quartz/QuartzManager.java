package com.kmzyc.express.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kmzyc.express.service.QuartzInfoService;

public class QuartzManager extends QuartzJobBean   {
  private static final Logger log = Logger.getLogger(QuartzManager.class);

  private QuartzInfoService quartzInfoService;

  public QuartzInfoService getQuartzInfoService() {
    return quartzInfoService;
  }

  public void setQuartzInfoService(QuartzInfoService quartzInfoService) {
    this.quartzInfoService = quartzInfoService;
  }
  @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Scheduler scheduler = context.getScheduler();
            quartzInfoService.refleshTask(scheduler);
          } catch (Exception e) {
            log.error("刷新任务列表时发生异常", e);
          }
    }
}
