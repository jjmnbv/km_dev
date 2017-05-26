package com.kmzyc.b2b.third.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.third.dao.ThirdAccountInfoDao;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;

/**
 * 实现类
 * 
 * @author Administrator 2014-03-18
 */
@Repository("thirdAccountInfoDao")
public class ThirdAccountInfoDaoImpl extends DaoImpl implements ThirdAccountInfoDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public void insert(ThirdAccountInfo entity) throws SQLException {
    sqlMapClient.insert("ThirdAccountInfo.ibatorgenerated_saveInfo", entity);
  }

  @Override
  public ThirdAccountInfo queryByPrimaryKey(ThirdAccountInfo condition) throws SQLException {
    return (ThirdAccountInfo) sqlMapClient.queryForObject("ThirdAccountInfo.queryByPrimaryKey",
        condition);
  }

  @Override
  public int updateAcctInfo(ThirdAccountInfo entity) throws SQLException {
    return sqlMapClient.update("ThirdAccountInfo.updateAcctInfo", entity);
  }

  @Override
  public void deleteAccountInfo(ThirdAccountInfo condition) throws SQLException {
    sqlMapClient.delete("ThirdAccountInfo.delAccountInfo", condition);
  }

  @Override
  public String queryOpenIdByLoginId(String loginId) throws SQLException {
    return (String) sqlMapClient.queryForObject("ThirdAccountInfo.queryOpenIdByLoginId", loginId);
  }

  /**
   * 查询第三方帐号信息
   * 
   * @param loginId
   * @param type
   * @return
   * @throws SQLException
   */
  @Override
  public ThirdAccountInfo queryThirdAccountInfo(Map<String, Object> params) throws SQLException {
    return (ThirdAccountInfo) sqlMapClient.queryForObject(
        "ThirdAccountInfo.SQL_QUERY_THIRD_ACCOUNT_INFO", params);
  }
}
