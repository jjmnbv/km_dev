package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderRiskScoreDao;
import com.pltfm.app.entities.OrderRiskScore;

@SuppressWarnings("unchecked")
@Repository
public class OrderRiskScoreDaoImpl extends BaseDAO implements OrderRiskScoreDao {
  /**
   * 批量插入判断得分
   * 
   * @param srcList
   * @return
   * @throws SQLException
   */
  public void batchInsert(List<OrderRiskScore> srcList) throws SQLException {
    batchInsertData("KMORDER_ORDER_RISK_SCORE.SQL_INSERT_ORDER_RISK_SCORE", srcList);
  }

  /**
   * 查询判断得分
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<OrderRiskScore> queryOrderRiskScore(String orderCode) throws SQLException {
    return sqlMapClient.queryForList("KMORDER_ORDER_RISK_SCORE.SQL_QUERY_ORDER_RISK_SCORE",
        orderCode);
  }

  /**
   * 将订单之前风控判断得分置空
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public void emptyOrderRiskScore(String orderCode) throws SQLException {
    sqlMapClient.update("KMORDER_ORDER_RISK_SCORE.SQL_UPDATE_EMPTY_ORDER_RISKS_CORE", orderCode);
  }
}
