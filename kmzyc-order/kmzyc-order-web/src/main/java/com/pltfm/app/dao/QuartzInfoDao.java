package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.QuartzInfo;

public interface QuartzInfoDao {
  /**
   * 查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTask(QuartzInfo qi) throws SQLException;

  /**
   * 分页查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTaskByPage(Map<String, String> params) throws SQLException;

  /**
   * 分页查询任务
   * 
   * @param qi
   * @return
   */
  public Integer queryTaskCount(Map<String, String> params) throws SQLException;

  /**
   * 删除任务
   * 
   * @param pid
   * @throws SQLException
   */
  public void deleteTask(Long pid) throws SQLException;

  /**
   * 更新任务
   * 
   * @param qi
   * @throws Exception
   */
  public void updateTask(QuartzInfo qi) throws SQLException;

  /**
   * 新增任务
   * 
   * @param qi
   * @throws SQLException
   */
  public void addTask(QuartzInfo qi) throws SQLException;

}
