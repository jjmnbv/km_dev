package com.pltfm.app.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.SpreadRulesDAO;
import com.pltfm.app.vobject.SpreadRules;

@Service(value = "spreadRulesDAO")
public class SpreadRulesDAOImpl implements SpreadRulesDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  // 查询微商全局设置
  public SpreadRules selectByPrimaryKey(SpreadRules key) throws SQLException {
    SpreadRules record = (SpreadRules) sqlMapClient
        .queryForObject("SPREAD_RULES.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }
}
