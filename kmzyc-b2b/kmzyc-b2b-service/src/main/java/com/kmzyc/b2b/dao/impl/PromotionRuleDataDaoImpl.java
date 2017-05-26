package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.PromotionRuleDataDao;
import com.kmzyc.b2b.model.PromotionRuleData;
import com.km.framework.persistence.impl.DaoImpl;

@Component("promotionRuleDataDao")
@SuppressWarnings("unchecked")
public class PromotionRuleDataDaoImpl extends DaoImpl implements PromotionRuleDataDao {
  @Resource
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  @Override
  public List<PromotionRuleData> selectPromotionRuleList(Long promotionInfoId) throws SQLException {
    return sqlMapClient.queryForList("PromotionRuleData.findByPromotionRuleDataInfo",
        promotionInfoId);
  }

  /**
   * 根据活动ID和金额查询活动规则数据
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<PromotionRuleData> queryPromotionRuleDataByPromotionId(Map<String, Object> params)
      throws SQLException {
    return sqlMapClient.queryForList("PromotionRuleData.QUERY_PROMOTION_RULE_DATA_BY_PROMOTION_ID",
        params);
  }
}
