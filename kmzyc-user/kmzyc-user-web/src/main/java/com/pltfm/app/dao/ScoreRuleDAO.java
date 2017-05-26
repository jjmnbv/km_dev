package com.pltfm.app.dao;

import com.pltfm.app.vobject.ScoreRule;
import com.pltfm.app.vobject.ScoreRuleExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 客户积分规则数据处理接口
 * 
 * @author zhl
 * @since 2013-07-09
 */
@SuppressWarnings("unchecked")
public interface ScoreRuleDAO {
  /**
   * 添加客户积分规则
   * 
   * @param scoreRule 积分规则实体
   * @throws SQLException 异常
   */
  public void insert(ScoreRule scoreRule) throws SQLException;

  /**
   * 通过id主键更新客户积分规则
   * 
   * @param scoreRule 积分规则实体
   * @throws SQLException 异常
   */
  public int updateByPrimaryKey(ScoreRule scoreRule) throws SQLException;

  /**
   * 多条件查询客户积分规则
   * 
   * @param example 查询实体
   * @throws SQLException 异常
   */
  public List selectByExample(ScoreRuleExample example) throws SQLException;

  /**
   * 通过id主键查询客户积分规则
   * 
   * @param nScoreRuleId 客户规则主键
   * @throws SQLException 异常
   */
  public ScoreRule selectByPrimaryKey(Integer nScoreRuleId) throws SQLException;

  /**
   * 多条件查询删除客户积分规则
   * 
   * @param example 查询实体
   * @throws SQLException 异常
   */
  public int deleteByExample(ScoreRuleExample example) throws SQLException;

  /**
   * 通过id主键删除客户积分规则
   * 
   * @param nScoreRuleId 客户积分规则主键
   * @throws SQLException 异常
   */
  public int deleteByPrimaryKey(Integer nScoreRuleId) throws SQLException;

  /**
   * 分页查询客户积分规则
   * 
   * @param scoreRule
   * @return
   * @throws SQLException
   */
  public List queryForPage(ScoreRule scoreRule) throws SQLException;

  /**
   * 多条件查询统计客户积分规则条数
   * 
   * @param example 查询实体
   * @throws SQLException 异常
   */
  public int countByExample(ScoreRule scoreRule) throws SQLException;

  /**
   * 根据编码查询规则
   * 
   * @param ruleCode
   * @return
   * @throws SQLException
   */
  public ScoreRule queryScoreRuleByRuleCode(String ruleCode) throws SQLException;
}
