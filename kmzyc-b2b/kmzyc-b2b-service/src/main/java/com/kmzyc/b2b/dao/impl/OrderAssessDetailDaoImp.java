package com.kmzyc.b2b.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.OrderAssessDetailDao;
import com.kmzyc.b2b.model.OrderAssessDetail;
import com.kmzyc.b2b.model.OrderAssessInfo;
import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;

@SuppressWarnings("unchecked")
@Repository("orderAssessDetailDao")
public class OrderAssessDetailDaoImp implements OrderAssessDetailDao {

  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  //private static Logger logger = Logger.getLogger(OrderAssessDetailDaoImp.class);
  
  private static Logger logger = LoggerFactory.getLogger(OrderAssessDetailDaoImp.class);

  /**
   * 根据条件查询订单的评价详细
   * 
   * @param orderAssessDetail
   * @return
   */
  public List<OrderAssessDetail> findOrderAssessDetaiByCondition(OrderAssessDetail orderAssessDetail)
      throws SQLException {
    List<OrderAssessDetail> orderAssessDetaiList =
        sqlMapClient.queryForList("ORDERASSESSDETAIL.ibatorgenerated_selectByCondition",
            orderAssessDetail);
    return orderAssessDetaiList;
  }

  /**
   * 查询订单的评论信息
   * 
   * @author luoyi
   * @date 2013/12/18
   */
  public OrderAssessInfo findAssessInfoByCode(String orderCode) {
    OrderAssessInfo orderAssessInfo = null;
    try {
      orderAssessInfo =
          (OrderAssessInfo) sqlMapClient.queryForObject(
              "ORDERASSESSDETAIL.findAssessInfoByOrderNo", orderCode);
      logger.info("根据订单号查询评论信息成功。");
      return orderAssessInfo;
    } catch (SQLException e) {
      logger.error("根据订单号查询评论信息失败" + e.getMessage(),e);
      return null;
    }
  }

  /**
   * 查询店铺评分
   * 
   * @param supplierId
   * @return
   * @throws SQLException
   */
  public Map<String, BigDecimal> queryShopScore(Long supplierId) throws SQLException {
    List<Map<String, BigDecimal>> list =
        (List<Map<String, BigDecimal>>) sqlMapClient.queryForList(
            "ORDERASSESSDETAIL.SQL_QUERY_SHOP_SCORE", supplierId);
    if (null != list && !list.isEmpty()) {
      return list.get(0);
    } else {
      return null;
    }
  }

  /**
   * 查询评价店铺评分
   * 
   * @return
   * @throws SQLException
   */
  public Map<String, BigDecimal> queryAvgShopScore() throws SQLException {
    List<Map<String, BigDecimal>> list =
        (List<Map<String, BigDecimal>>) sqlMapClient
            .queryForList("ORDERASSESSDETAIL.SQL_QUERY_SHOP_AVG_SCORE");
    if (null != list && !list.isEmpty()) {
      return list.get(0);
    } else {
      return null;
    }
  }
}
