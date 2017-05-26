package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.OrderRiskScore;

/**
 * 风控判断得分
 * 
 * @author xlg
 * 
 */
public interface OrderRiskScoreDao {
  /**
   * 批量插入判断得分
   * 
   * @param srcList
   * @return
   * @throws SQLException
   */
  public void batchInsert(List<OrderRiskScore> srcList) throws SQLException;

  /**
   * 查询判断得分
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<OrderRiskScore> queryOrderRiskScore(String orderCode) throws SQLException;

  /**
   * 将订单之前风控判断得分置空
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public void emptyOrderRiskScore(String orderCode) throws SQLException;
}
