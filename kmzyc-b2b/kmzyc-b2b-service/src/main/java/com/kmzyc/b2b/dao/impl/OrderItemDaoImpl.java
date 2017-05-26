package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.OrderItemDao;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.SupplierOrderItem;
import com.km.framework.persistence.impl.DaoImpl;

@SuppressWarnings("unchecked")
@Repository
public class OrderItemDaoImpl extends DaoImpl implements OrderItemDao {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public OrderItem findById(Long orderItemId) throws SQLException {
    return (OrderItem) sqlMapClient.queryForObject("OrderItem.selectByPrimaryKey", orderItemId);
  }

  @Override
  public OrderItem findByIdForReturnShop(Long orderItemId) throws SQLException {
    return (OrderItem) sqlMapClient.queryForObject("OrderItem.selectByPrimaryKeyForReturnShop",
        orderItemId);
  }

  /**
   * 查询订单总积分
   * 
   * @author km-张文平
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public double getTotalCredits(String orderCode) throws SQLException {
    return (Double) sqlMapClient.queryForObject("OrderItem.getTotalCredits", orderCode);
  }

  @Override
  public List<OrderItem> findByOrderCode(String orderCode) throws SQLException {
    return (List<OrderItem>) sqlMapClient.queryForList("OrderItem.findByOrderCode", orderCode);
  }

  @Override
  public Long selectOverplusNum(Long orderItemId) throws SQLException {
    Long result = (Long) sqlMapClient.queryForObject("OrderItem.selectOverplusNum", orderItemId);
    return null == result ? 0L : result;
  }

  @Override
  public List<Long> querySuitIdByOrderCode(String orderCode) throws SQLException {
    return (List<Long>) sqlMapClient.queryForList("OrderItem.querySuitIdByOrderCode", orderCode);
  }

  @Override
  public List<OrderItem> findByOrderCodeWithoutGiftProduct(String orderCode) throws SQLException {
    return (List<OrderItem>) sqlMapClient.queryForList(
        "OrderItem.findByOrderCodeWithoutGiftProduct", orderCode);

  }

  @Override
  public List<String> checkProductStockNumber(String orderCode) throws SQLException {
    return (List<String>) sqlMapClient.queryForList("OrderItem.checkProductStockNumber", orderCode);
  }

  @Override
  public List<String> queryErrProductAmountInPromotion(String orderCode) throws SQLException {
    return (List<String>) sqlMapClient.queryForList("OrderItem.queryErrProductAmountInPromotion",
        orderCode);
  }

  @Override
  public List<SupplierOrderItem> findSupplierOrderItem(Long orderItemId) throws SQLException {

    return (List<SupplierOrderItem>) sqlMapClient.queryForList("OrderItem.findSupplierOrderItem",
        orderItemId);
  }

  /**
   * 查询产品明细库存
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public List<Long> queryOrderItemStock(String orderCode) throws SQLException {
    return (List<Long>) sqlMapClient
        .queryForList("OrderItem.SQL_QUERY_ORDER_ITEM_STOCK", orderCode);
  }

  /**
   * 查询订单是否存在不符合结算状态的产品
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public List<String> queryOrderItemExceptionStatusProduct(String orderCode) throws SQLException {
    return (List<String>) sqlMapClient.queryForList(
        "OrderItem.SQL_QUERY_ORDER_ITEM_EXCEPTION_STATUS_PRODUCT", orderCode);
  }

  /**
   * 查询订单是否存在不符合结算状态的套餐
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public List<String> queryOrderItemExceptionStatusSuits(String orderCode) throws SQLException {
    return (List<String>) sqlMapClient.queryForList(
        "OrderItem.SQL_QUERY_ORDER_ITEM_EXCEPTION_STATUS_SUITS", orderCode);
  }

  /**
   * 查询订单的产品活动
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public Map<Long, String> queryOrderProductPromotion(String orderCode) throws SQLException {
    return (Map<Long, String>) sqlMapClient.queryForMap(
        "OrderItem.SQL_QUERY_ORDER_PRODUCT_PROMOTION", orderCode, "DKEY", "DVALUE");
  }

  /**
   * 查询活动期间用户购买量
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public Integer queryUserBuyPromotionNum(Long userId, Long skuId, String promotionId)
      throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("userId", userId);
    params.put("skuId", skuId);
    params.put("promotionId", promotionId);
    Integer sum =
        (Integer) sqlMapClient.queryForObject("OrderItem.SQL_QUERY_USER_BUY_PROMOTION_NUM", params);
    return null == sum ? 0 : sum;
  }

  /**
   * 查询活动期间用户购买量
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public Integer queryUserBuyPromotionNumForPressell(Long userId, Long skuId, String pressellId)
      throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("userId", userId);
    params.put("skuId", skuId);
    params.put("pressellId", pressellId);
    Integer sum =
        (Integer) sqlMapClient.queryForObject(
            "OrderItem.SQL_QUERY_USER_BUY_PROMOTION_NUM_FORPRESSELL", params);
    return null == sum ? 0 : sum;
  }
}
