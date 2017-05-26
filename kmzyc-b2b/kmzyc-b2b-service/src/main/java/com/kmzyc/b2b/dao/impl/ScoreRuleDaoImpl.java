package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.ScoreRuleDao;
import com.kmzyc.b2b.model.ScoreRule;
import com.kmzyc.b2b.util.SerializeUtil;
import com.kmzyc.framework.exception.DaoException;

import redis.clients.jedis.JedisCluster;

@Component
public class ScoreRuleDaoImpl extends DaoImpl implements ScoreRuleDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;
  @Resource(name = "jedisCluster")
 private JedisCluster jedisCluster;

  final Logger log = LoggerFactory.getLogger(ScoreRuleDaoImpl.class);

  @Override
  public ScoreRule findByCode(String code) throws DaoException, SQLException {
    List list = sqlMapClient.queryForList("ScoreRule.findByCode", code);
    if (list != null && list.size() > 0) {
      ScoreRule scoreRule = (ScoreRule) list.get(0);
      return scoreRule;
    } else {
      throw new DaoException("code: " + code + " ScoreRule not find.");
    }
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
      byte[] key = SerializeUtil.serialize("score_rule_new_" + ruleCode);

      byte[] value = jedisCluster.get(key);
      if (null != value && value.length > 0) {
        result = (ScoreRule) SerializeUtil.unserialize(value);
      } else {
        result =
            (ScoreRule) sqlMapClient.queryForObject("ScoreRule.SQL_QUERY_SCORE_RULE_BY_RULE_CODE",
                ruleCode);
          jedisCluster.set(key, SerializeUtil.serialize(result));
          jedisCluster.expire(key, 300);
      }
    } catch (Exception e) {
      log.error(e.getMessage(),e);
    }
    return result;
  }
}
