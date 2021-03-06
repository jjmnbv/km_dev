package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.app.vobject.PromotionRuleDataExample;

@SuppressWarnings("unchecked")
public interface PromotionRuleDataDAO {

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PROMOTION_RULE_DATA
   * 
   * @ibatorgenerated Wed Sep 18 15:39:29 CST 2013
   */
  int deleteByExample(PromotionRuleDataExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PROMOTION_RULE_DATA
   * 
   * @ibatorgenerated Wed Sep 18 15:39:29 CST 2013
   */
  void insertSelective(PromotionRuleData record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PROMOTION_RULE_DATA
   * 
   * @ibatorgenerated Wed Sep 18 15:39:29 CST 2013
   */
  List selectByExample(PromotionRuleDataExample example) throws SQLException;

  /**
   * 根据活动ID获取活动规则，Date可为空
   * 
   * @param promotionInfoId
   * @return
   * @throws SQLException
   */
  public List<PromotionRuleData> selectPromotionRuleList(Long promotionInfoId);

  /**
   * 批量新增规则数据
   * 
   * @param promotionRuleDataList
   * @throws SQLException
   */
  public void batchInsertData(List<PromotionRuleData> promotionRuleDataList) throws SQLException;

  /**
   * 批量查询规则数据
   * 
   * @param pids
   * @return
   * @throws SQLException
   */
  public List<PromotionRuleData> queryBatchPromotionRuleData(List<Long> pids) throws SQLException;

  List<com.alibaba.fastjson.JSONObject> selectPromotionRuleAndEntity(Map<String, String> map,
      String sql) throws SQLException;
}
