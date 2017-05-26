package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.PromotionRuleData;

public interface PromotionRuleDataDao {
  public List<PromotionRuleData> selectPromotionRuleList(Long promotionInfoId) throws SQLException;

  /**
   * 根据活动ID和金额查询活动规则数据
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<PromotionRuleData> queryPromotionRuleDataByPromotionId(Map<String, Object> params)
      throws SQLException;
}
