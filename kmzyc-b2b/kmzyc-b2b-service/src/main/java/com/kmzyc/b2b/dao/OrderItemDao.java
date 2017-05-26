package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.SupplierOrderItem;

public interface OrderItemDao extends Dao {

  public OrderItem findById(Long orderItemId) throws SQLException;

  /**
   * 查询订单总积分
   * 
   * @author km-张文平
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public double getTotalCredits(String orderCode) throws SQLException;

  public List<OrderItem> findByOrderCode(String orderCode) throws SQLException;

  public Long selectOverplusNum(Long orderItemId) throws SQLException;

  public OrderItem findByIdForReturnShop(Long orderItemId) throws SQLException;

  public List<Long> querySuitIdByOrderCode(String orderCode) throws SQLException;

  public List<OrderItem> findByOrderCodeWithoutGiftProduct(String orderCode) throws SQLException;

  public List<String> checkProductStockNumber(String orderCode) throws SQLException;

  public List<String> queryErrProductAmountInPromotion(String orderCode) throws SQLException;

  public List<SupplierOrderItem> findSupplierOrderItem(Long orderItemId) throws SQLException;

  /**
   * 查询产品明细库存
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<Long> queryOrderItemStock(String orderCode) throws SQLException;

  /**
   * 查询订单是否存在不符合结算状态的产品
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<String> queryOrderItemExceptionStatusProduct(String orderCode) throws SQLException;

  /**
   * 查询订单是否存在不符合结算状态的套餐
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<String> queryOrderItemExceptionStatusSuits(String orderCode) throws SQLException;

  /**
   * 查询订单的产品活动
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Map<Long, String> queryOrderProductPromotion(String orderCode) throws SQLException;

  /**
   * 查询活动期间用户购买量
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Integer queryUserBuyPromotionNum(Long userId, Long skuId, String promotionId)
      throws SQLException;

  /**
   * 查询活动期间用户购买量(预售活动)
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Integer queryUserBuyPromotionNumForPressell(Long userId, Long skuId, String pressellId)
      throws SQLException;
}
