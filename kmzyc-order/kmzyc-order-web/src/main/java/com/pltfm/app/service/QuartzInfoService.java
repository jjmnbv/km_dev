package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.QuartzInfo;

public interface QuartzInfoService {
  /**
   * 查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTask(QuartzInfo qi) throws ServiceException;

  /**
   * 分页查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTaskByPage(Map<String, String> params) throws ServiceException;

  /**
   * 查询任务个数
   * 
   * @param qi
   * @return
   */
  public Integer queryTaskCount(Map<String, String> params) throws ServiceException;

  /**
   * 删除任务
   * 
   * @param pid
   * @throws SQLException
   */
  public int deleteTask(Long pid) throws ServiceException;

  /**
   * 更新任务
   * 
   * @param qi
   * @throws Exception
   */
  public int updateTask(QuartzInfo qi) throws ServiceException;

  /**
   * 新增任务
   * 
   * @param qi
   * @throws SQLException
   */
  public int addTask(QuartzInfo qi) throws ServiceException;

  /**
   * 刷新job
   * 
   * @param scheduler
   * @param params
   * @throws ServiceException
   */
  public void refleshTask(Scheduler scheduler, QuartzInfo params) throws ServiceException;
}
