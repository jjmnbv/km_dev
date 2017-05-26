package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.QuartzInfoDao;
import com.pltfm.app.entities.QuartzInfo;
import com.pltfm.app.service.QuartzInfoService;
import com.pltfm.sys.util.ErrorCode;

@Service("quartzInfoService")
public class QuartzInfoServiceImpl implements QuartzInfoService {
  @Resource
  private QuartzInfoDao quartzInfoDao;

  /**
   * 查询任务
   * 
   * @param qi
   * @return
   */
  @Override
public List<QuartzInfo> queryTask(QuartzInfo qi) throws ServiceException {
    try {
      return quartzInfoDao.queryTask(qi);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR, "查询定时任务发生异常", e);
    }
  }

  /**
   * 分页查询任务
   * 
   * @param qi
   * @return
   */
  @Override
public List<QuartzInfo> queryTaskByPage(Map<String, String> params) throws ServiceException {
    try {
      return quartzInfoDao.queryTaskByPage(params);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR, "分页查询定时任务发生异常", e);
    }
  }

  /**
   * 查询任务个数
   * 
   * @param qi
   * @return
   */
  @Override
public Integer queryTaskCount(Map<String, String> params) throws ServiceException {
    try {
      return quartzInfoDao.queryTaskCount(params);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR, "查询任务个数发生异常", e);
    }
  }

  /**
   * 删除任务
   * 
   * @param pid
   * @throws SQLException
   */
  @Override
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int deleteTask(Long pid) throws ServiceException {
    try {
      if (null != pid) {
        quartzInfoDao.deleteTask(pid);
        return 1;
      }
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_UPDATE_ERROR, "删除任务发生异常", e);
    }
    return 0;
  }

  /**
   * 更新任务
   * 
   * @param qi
   * @throws Exception
   */
  @Override
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int updateTask(QuartzInfo qi) throws ServiceException {
    try {
      if (null != qi) {
        quartzInfoDao.updateTask(qi);
        return 1;
      }
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_UPDATE_ERROR, "更新任务发生异常", e);
    }
    return 0;
  }

  /**
   * 新增任务
   * 
   * @param qi
   * @throws SQLException
   */
  @Override
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int addTask(QuartzInfo qi) throws ServiceException {
    try {
      if (null != qi) {
        quartzInfoDao.addTask(qi);
        return 1;
      }
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_RETURNS_UPDATE_ERROR, "新增任务发生异常", e);
    }
    return 0;
  }

  /**
   * 刷新job
   * 
   * @param scheduler
   * @param params
   * @throws ServiceException
   */
  @Override
public void refleshTask(Scheduler scheduler, QuartzInfo params) throws ServiceException {
    try {
      List<QuartzInfo> list = queryTask(params);
      if (null != list) {
        System.out.println("本次刷新定时任务个数:" + list.size());
        for (QuartzInfo qi : list) {
          if ("1".equals(qi.getIsVolatile())) {//删除停用的定时任务
            scheduler.deleteJob(qi.getJobName(), qi.getJobGroup());
            continue;
          }
          JobDetail jobDetail = scheduler.getJobDetail(qi.getJobName(), qi.getJobGroup());
          CronTrigger trigger =
              (CronTrigger) scheduler.getTrigger(qi.getTriggerName(), qi.getTriggerGroup());
          if (null == trigger) {
            trigger = new CronTrigger(qi.getTriggerName(), qi.getTriggerGroup());
          }
          if (!qi.getCronExpression().equals(trigger.getCronExpression())) {//设置触发时间？
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
      throw new ServiceException(ErrorCode.INNER_RETURNS_QUERY_ERROR, "查询定时任务发生异常", e);
    }
  }
}
