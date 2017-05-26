package com.kmzyc.express.service;

import org.quartz.Scheduler;

import com.pltfm.exception.ServiceException;

public interface QuartzInfoService {
  /**
   * 刷新job
   * 
   * @param scheduler
   * @param params
   * @throws ServiceException
   */
  public void refleshTask(Scheduler scheduler) throws ServiceException;
}
