package com.kmzyc.express.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.QuartzInfo;

public interface QuartzInfoDao {
  /**
   * 查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTask() throws SQLException;
}
