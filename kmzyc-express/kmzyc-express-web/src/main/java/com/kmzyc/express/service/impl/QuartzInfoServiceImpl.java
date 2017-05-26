package com.kmzyc.express.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import com.kmzyc.express.dao.QuartzInfoDao;
import com.kmzyc.express.service.QuartzInfoService;
import com.kmzyc.express.util.ErrorCode;
import com.pltfm.app.entities.QuartzInfo;
import com.pltfm.exception.ServiceException;

@Service("quartzInfoService")
public class QuartzInfoServiceImpl implements QuartzInfoService {
  @Resource
  private QuartzInfoDao quartzInfoDao;

  /**
   * 刷新job
   * 
   * @param scheduler
   * @param params
   * @throws ServiceException
   */
  @Override
  public void refleshTask(Scheduler scheduler) throws ServiceException {
    try {
      List<QuartzInfo> list = quartzInfoDao.queryTask();
      if (null != list) {
        System.out.println("本次刷新定时任务个数:" + list.size());
        for (QuartzInfo qi : list) {
          if ("1".equals(qi.getIsVolatile())) {
            scheduler.deleteJob(qi.getJobName(), qi.getJobGroup());
            continue;
          }
          JobDetail jobDetail = scheduler.getJobDetail(qi.getJobName(), qi.getJobGroup());
          CronTrigger trigger =
              (CronTrigger) scheduler.getTrigger(qi.getTriggerName(), qi.getTriggerGroup());
          if (null == trigger) {
            trigger = new CronTrigger(qi.getTriggerName(), qi.getTriggerGroup());
          }
          if (!qi.getCronExpression().equals(trigger.getCronExpression())) {
            trigger.setCronExpression(qi.getCronExpression());
            if (null == jobDetail) {
              jobDetail =
                  new JobDetail(qi.getJobName(), qi.getJobGroup(), Class.forName(qi.getJobClass()));
              jobDetail.setDescription(qi.getJobDescription());
            } else {
              scheduler.deleteJob(qi.getJobName(), qi.getJobGroup());
            }
            scheduler.scheduleJob(jobDetail, trigger);
          }
        }
      }
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.EXPRESS_TASK_ERR, "查询定时任务发生异常", e);
    }
  }
}
