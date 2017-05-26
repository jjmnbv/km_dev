package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.ProductRelation;
import com.kmzyc.b2b.model.SupplierOrderItem;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.vo.PayMoneyPresell;
import com.kmzyc.framework.exception.ServiceException;
import com.pltfm.app.vobject.OrderPreferentialVO;

public interface OrderItemService {

  public OrderItem findById(Long orderItemId) throws ServiceException;

  /**
   * 查询订单总积分
   * 
   * @author km-张文平
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public double getTotalCredits(String orderCode) throws ServiceException;

  public List<OrderItem> findByOrderCode(String orderCode) throws ServiceException;

  public Long selectOverplusNum(Long orderItemId) throws ServiceException;

  public OrderItem findByIdForReturnShop(Long orderItemId) throws ServiceException;

  public List<ProductRelation> querySuitIdByOrderCode(String orderCode) throws ServiceException;

  public List<OrderItem> findByOrderCodeWithoutGiftProduct(String orderCode)
      throws ServiceException;

  public List<String> queryErrProductAmountInPromotion(String orderCode) throws ServiceException;

  public SupplierOrderItem findSupplierOrderItem(Long orderItemId) throws ServiceException;

  /**
   * 查询产品明细库存
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public List<Long> queryOrderItemStock(String orderCode) throws ServiceException;

  /**
   * 校验结算购物车1:changed;2:offshelf;3:outstock;
   * 
   * @param settShopCart
   * @param shopCartCache
   * @return
   */
  public Map<String, String> compareSett(ShopCart settShopCart, ShopCart shopCartCache,
      String outStockConfirm) throws ServiceException;

  /**
   * 订单明细
   * 
   * @return
   */
  public void generateOrderItem(AccountInfo settAccountInfo,
      com.pltfm.app.entities.OrderMain orderMain, ShopCart settShopCart,
      List<com.pltfm.app.entities.OrderItem> oiList, List<OrderPreferentialVO> opList,
      BigDecimal[] moneyInfo, boolean[] identity, PayMoneyPresell payMoneyPresell)
      throws ServiceException;

  /**
   * 查询活动期间用户购买量（预售活动）
   * @param userId
   * @param skuId
   * @param pressellId
   * @return
   * @throws SQLException
   */
  public Integer queryUserBuyPromotionNumForPressell(Long userId, Long skuId, String pressellId)
      throws SQLException;
}
