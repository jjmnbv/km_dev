package com.kmzyc.b2b.dao;

import java.sql.SQLException;

import com.kmzyc.b2b.model.ScoreRule;
import com.kmzyc.framework.exception.DaoException;
import com.km.framework.persistence.Dao;

public interface ScoreRuleDao extends Dao {
  ScoreRule findByCode(String code) throws DaoException, SQLException;

  /**
   * 根据编码查询规则
   * 
   * @param ruleCode
   * @return
   * @throws SQLException
   */
  public ScoreRule queryScoreRuleByRuleCode(String ruleCode) throws SQLException;
}
