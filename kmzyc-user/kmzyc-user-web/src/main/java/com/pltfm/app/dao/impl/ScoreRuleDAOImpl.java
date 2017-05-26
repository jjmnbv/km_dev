package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.ScoreRuleDAO;
import com.pltfm.app.util.SerializeUtil;
import com.pltfm.app.vobject.ScoreRule;
import com.pltfm.app.vobject.ScoreRuleExample;

import redis.clients.jedis.JedisCluster;



/**
 * 客户积分规则数据处理类
 * 
 * @author zhl
 * @since 2013-07-09
 */
@SuppressWarnings("unchecked")
@Component(value = "scoreRuleDAO")
public class ScoreRuleDAOImpl implements ScoreRuleDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  final Logger log = LoggerFactory.getLogger(ScoreRuleDAOImpl.class);
  @Resource
  private JedisCluster jedis;

  /**
   * 添加客户积分规则
   */
  public void insert(ScoreRule record) throws SQLException {
    sqlMapClient.insert("SCORE_RULE.abatorgenerated_insert", record);
  }

  /**
   * 通过主键更新客户积分规则
   */
  public int updateByPrimaryKey(ScoreRule record) throws SQLException {
    int rows = sqlMapClient.update("SCORE_RULE.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 多条件查询客户积分规则信息
   */
  public List selectByExample(ScoreRuleExample example) throws SQLException {
    return sqlMapClient.queryForList("SCORE_RULE.abatorgenerated_selectByExample", example);
  }

  /**
   * 通过主键查询客户积分规则信息
   * 
   * @param nScoreRuleId 客户积分规则主键
   * @return
   * @throws SQLException 异常
   */
  public ScoreRule selectByPrimaryKey(Integer nScoreRuleId) throws SQLException {
    ScoreRule key = new ScoreRule();
    key.setN_scoreRuleId(nScoreRuleId);
    return (ScoreRule) sqlMapClient.queryForObject("SCORE_RULE.abatorgenerated_selectByPrimaryKey",
        key);
  }

  /**
   * 多条件查询删除客户积分规则信息
   */
  public int deleteByExample(ScoreRuleExample example) throws SQLException {
    return sqlMapClient.delete("SCORE_RULE.abatorgenerated_deleteByExample", example);
  }

  /**
   * 通过主键删除客户积分规则信息
   * 
   * @param nScoreRuleId 客户积分规则主键
   * @return
   * @throws SQLException 异常
   */
  public int deleteByPrimaryKey(Integer nScoreRuleId) throws SQLException {
    ScoreRule key = new ScoreRule();
    key.setN_scoreRuleId(nScoreRuleId);
    return sqlMapClient.delete("SCORE_RULE.abatorgenerated_deleteByPrimaryKey", key);
  }

  /**
   * 多条件查询统计客户积分规则条数
   */
  public int countByExample(ScoreRule scoreRule) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("SCORE_RULE.abatorgenerated_countByRule",
        scoreRule);
  }

  /**
   * 分页查询客户积分规则
   */
  public List queryForPage(ScoreRule scoreRule) throws SQLException {
    return sqlMapClient.queryForList("SCORE_RULE.abatorgenerated_pageByRule", scoreRule);
  }

  /**
   * 根据编码查询规则
   * 
   * @param ruleCode
   * @return
   * @throws SQLException
   */
  @Override
  public ScoreRule queryScoreRuleByRuleCode(String ruleCode) throws SQLException {
    ScoreRule result = null;
  
    try {
   
      byte[] key = SerializeUtil.serialize("score_rule_" + ruleCode);
      byte[] value = jedis.get(key);
      if (null != value && value.length > 0) {
        result = (ScoreRule) SerializeUtil.unserialize(value);
      }
      
      if(result==null){
          result = (ScoreRule) sqlMapClient
                          .queryForObject("SCORE_RULE.SQL_QUERY_SCORE_RULE_BY_RULE_CODE", ruleCode);
         jedis.setex(key, 300, SerializeUtil.serialize(result));
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return result;
  }
}
