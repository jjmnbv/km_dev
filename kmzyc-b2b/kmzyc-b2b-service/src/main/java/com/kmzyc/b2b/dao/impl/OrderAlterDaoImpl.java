package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.OrderAlterDao;
import com.kmzyc.b2b.model.OrderAlter;
import com.km.framework.persistence.impl.DaoImpl;

@SuppressWarnings("unchecked")
@Component
public class OrderAlterDaoImpl extends DaoImpl implements OrderAlterDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public OrderAlter findOrderAlterPv(Map map) throws SQLException {
    OrderAlter orderAlter = (OrderAlter) sqlMapClient.queryForObject("OrderAlter" +
            ".findOrderAlterPv", map);
    return orderAlter;
  }

}
