package com.pltfm.app.dao;

import com.pltfm.app.vobject.PromRewardRule;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface PromRewardRuleDAO {
  /**
   * 删除
   * 
   * @param rewardRuleId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(BigDecimal rewardRuleId) throws SQLException;

  /**
   * 添加
   * 
   * @param record
   * @throws SQLException
   */
  void insert(PromRewardRule record) throws SQLException;

  /**
   * 查询
   * 
   * @param rewardRuleId
   * @return
   * @throws SQLException
   */
  PromRewardRule selectByPrimaryKey(PromRewardRule record) throws SQLException;

  /**
   * 修改
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(PromRewardRule record) throws SQLException;
}
