package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PromRewardRuleDAO;
import com.pltfm.app.vobject.PromRewardRule;

@Component(value = "promRewardRuleDAO")
public class PromRewardRuleDAOImpl implements PromRewardRuleDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 删除
   */
  public int deleteByPrimaryKey(BigDecimal rewardRuleId) throws SQLException {
    PromRewardRule key = new PromRewardRule();
    key.setRewardRuleId(rewardRuleId);
    int rows = sqlMapClient.delete("PROM_REWARD_RULE.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 添加
   */
  public void insert(PromRewardRule record) throws SQLException {
    sqlMapClient.insert("PROM_REWARD_RULE.ibatorgenerated_insert", record);
  }

  /**
   * 查询
   */
  public PromRewardRule selectByPrimaryKey(PromRewardRule record) throws SQLException {
    record = (PromRewardRule) sqlMapClient
        .queryForObject("PROM_REWARD_RULE.ibatorgenerated_selectByPrimaryKey", record);
    return record;
  }

  /**
   * 修改
   */
  public int updateByPrimaryKeySelective(PromRewardRule record) throws SQLException {
    int rows =
        sqlMapClient.update("PROM_REWARD_RULE.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }
}
