package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.Live800Dao;

@SuppressWarnings("unchecked")
@Repository("live800Dao")
public class Live800DaoImpl implements Live800Dao {

  @javax.annotation.Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 获取用户的基本信息
   * 
   * @param userName 登录用户名
   * @return
   */
  @Override
  public Map<String, Object> getCustomInfo(final long userId) throws SQLException {
    return (Map<String, Object>) sqlMapClient.queryForObject("LIVE800.SQL_QUERY_CUSTOM_INFO",
        userId);
  }

  /**
   * 获取用户收获地址信息
   * 
   * @param userId 用户ID
   * @return
   */
  @Override
  @Deprecated
  public List<Map<String, Object>> getGoodsReceiptAddress(final long userId) throws SQLException {
    return (List<Map<String, Object>>) sqlMapClient.queryForList("LIVE800.SQL_QUERY_ADDRESS",
        userId);
  }

  @Override
  public long getUserId(String userName) throws SQLException {
    Long userId = (Long) sqlMapClient.queryForObject("LIVE800.getUserId", userName);
    long id = userId == null ? 0 : userId.longValue();
    return id;
  }

}
