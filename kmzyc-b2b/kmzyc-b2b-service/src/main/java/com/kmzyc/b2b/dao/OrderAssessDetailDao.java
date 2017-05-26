package com.kmzyc.b2b.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.OrderAssessDetail;
import com.kmzyc.b2b.model.OrderAssessInfo;

public interface OrderAssessDetailDao {

  /**
   * 根据条件查询订单的评价详细
   * 
   * @param orderAssessDetail
   * @return
   */
  public List<OrderAssessDetail> findOrderAssessDetaiByCondition(OrderAssessDetail orderAssessDetail)
      throws SQLException;

  /**
   * 查询订单的评论信息
   * 
   * @author luoyi
   * @date 2013/12/18
   */
  public OrderAssessInfo findAssessInfoByCode(String orderCode);

  /**
   * 查询店铺评分
   * 
   * @param supplierId
   * @return
   * @throws SQLException
   */
  public Map<String, BigDecimal> queryShopScore(Long supplierId) throws SQLException;

  /**
   * 查询评价店铺评分
   * 
   * @return
   * @throws SQLException
   */
  public Map<String, BigDecimal> queryAvgShopScore() throws SQLException;
}
