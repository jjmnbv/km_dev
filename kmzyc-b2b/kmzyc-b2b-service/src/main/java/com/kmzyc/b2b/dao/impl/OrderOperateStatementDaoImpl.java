package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.OrderOperateStatementDao;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.framework.exception.DaoException;

@Component("orderOperateStatementDao")
public class OrderOperateStatementDaoImpl implements OrderOperateStatementDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public List<OrderOperateStatement> findByOrderNo(String orderNo) throws SQLException,
      DaoException {
    List list = sqlMapClient.queryForList("OrderOperateStatement.findByOrderNo", orderNo);
    return list;
  }

  @Override
  public List<OrderOperateStatement> findByOrderNoForView(String orderNo) throws SQLException,
      DaoException {
    List list = sqlMapClient.queryForList("OrderOperateStatement.findByOrderNoForView", orderNo);
    return list;
  }

}
