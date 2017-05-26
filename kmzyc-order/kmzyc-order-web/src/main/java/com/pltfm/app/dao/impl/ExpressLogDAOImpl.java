package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmzyc.express.entities.ExpressLog;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.ExpressLogDAO;

@Component(value = "expressLogDAO")
public class ExpressLogDAOImpl extends BaseDAO<ExpressLog> implements ExpressLogDAO {

  @Override
public int queryExpressLogCount(Map<String, String> paramMap) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMORDER_EXPRESS_LOG.queryExpressLogCount",
        paramMap);
  }

  @Override
@SuppressWarnings("unchecked")
  public List queryExpressLogList(Map<String, String> paramMap) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_EXPRESS_LOG.queryExpressLogList", paramMap);
  }
}
