package com.kmzyc.express.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kmzyc.express.dao.BaseDAO;
import com.kmzyc.express.dao.QuartzInfoDao;
import com.pltfm.app.entities.QuartzInfo;

@Repository("quartzInfoDao")
@SuppressWarnings("unchecked")
public class QuartzInfoDaoImpl extends BaseDAO<QuartzInfo> implements QuartzInfoDao {

  /**
   * 查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTask() throws SQLException {
    return sqlMapClient.queryForList("KMORDER_EXPRESS_COMPANY.SQL_QUERY_EXPRE_QUARTZ_TASK_LIST");
  }

}
