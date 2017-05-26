package com.pltfm.app.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.FailuerTimeDefiDAO;
import com.pltfm.app.vobject.FailureTimeDefi;

/**
 * 手机随机码信息处理类
 * 
 * @author cjm
 * @since 2013-7-23
 */
@Component(value = "failuerTimeDefiDAO")
public class FailuerTimeDefiDAOImpl implements FailuerTimeDefiDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 根据主键查询失效时间定义信息
   * 
   * @param n_PersonalityId 手机随机码ID
   * @return 返回值
   * @throws Exception 异常
   */

  @Override
  public FailureTimeDefi selectByPrimaryKey(Integer failure_Time_Id) throws SQLException {
    // TODO Auto-generated method stub
    Object failureTimeDefi =
        sqlMapClient.queryForObject("FAILURE_TIME_DEFI.searchById", failure_Time_Id);
    return (FailureTimeDefi) failureTimeDefi;
  }


  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }



}
