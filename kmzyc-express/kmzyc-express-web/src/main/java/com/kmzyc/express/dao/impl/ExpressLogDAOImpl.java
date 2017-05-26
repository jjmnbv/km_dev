package com.kmzyc.express.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmzyc.express.dao.BaseDAO;
import com.kmzyc.express.dao.ExpressLogDAO;
import com.kmzyc.express.entities.ExpressLog;

@Component(value = "expressLogDAO")
public class ExpressLogDAOImpl extends BaseDAO<ExpressLog> implements ExpressLogDAO {

  public int queryExpressLogCount(Map<String, String> paramMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMORDER_EXPRESS_LOG.queryExpressLogCount",
        paramMap);
  }

  @SuppressWarnings("unchecked")
  public List queryExpressLogList(Map<String, String> paramMap) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_EXPRESS_LOG.queryExpressLogList", paramMap);
  }

  @Override
  public BigDecimal insertExpressLog(ExpressLog record) throws SQLException {
    return (BigDecimal) sqlMapClient.insert("KMORDER_EXPRESS_LOG.insertExpressLog", record);
  }

}
