package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.QuartzInfoDao;
import com.pltfm.app.entities.QuartzInfo;

@Repository("quartzInfoDao")
@SuppressWarnings("unchecked")
public class QuartzInfoDaoImpl extends BaseDAO implements QuartzInfoDao {

  /**
   * 查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTask(QuartzInfo qi) throws SQLException {
    return sqlMapClient.queryForList("KMUTILS.SQL_QUERY_TASK", qi);
  }

  /**
   * 分页查询任务
   * 
   * @param qi
   * @return
   */
  public List<QuartzInfo> queryTaskByPage(Map<String, String> params) throws SQLException {
    return sqlMapClient.queryForList("KMUTILS.SQL_QUERY_TASK_BY_PAGE", params);
  }

  /**
   * 分页查询任务
   * 
   * @param qi
   * @return
   */
  public Integer queryTaskCount(Map<String, String> params) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMUTILS.SQL_QUERY_TASK_COUNT", params);
  }

  /**
   * 删除任务
   * 
   * @param qi
   * @throws SQLException
   */
  public void deleteTask(Long pid) throws SQLException {
    sqlMapClient.delete("KMUTILS.SQL_DELETE_TASK", pid);
  }

  /**
   * 更新任务
   * 
   * @param qi
   * @throws Exception
   */
  public void updateTask(QuartzInfo qi) throws SQLException {
    sqlMapClient.update("KMUTILS.SQL_UPDATE_TASK", qi);
  }

  /**
   * 新增任务
   * 
   * @param qi
   * @throws SQLException
   */
  public void addTask(QuartzInfo qi) throws SQLException {
    sqlMapClient.insert("KMUTILS.SQL_INSERT_TASK", qi);
  }
}
