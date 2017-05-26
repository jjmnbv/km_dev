package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.supplier.model.SupplierFare;

public interface OrderMainService {

  public List<OrderMain> findByOrderCode(Map<String, String> map) throws ServiceException;

  public BigDecimal findNeedToPayMoney(String orderCode) throws ServiceException;

  /**
   * 查询订单是否处于订单支付完成的后期阶段
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<OrderMain> findPaySuccessLaterStages(Map<String, String> map) throws ServiceException;

  // 查询订单需要支付金额
  public BigDecimal queryPayMoney(String orderCode) throws ServiceException;

  public BigDecimal findPaiedMoney(String orderCode) throws ServiceException;

  /**
   * 查询活动期间某用户购买指定产品数量
   * 
   * @return
   * @throws SQLException
   */
  public BigDecimal querySumUserBuySKUNum(Map map) throws ServiceException;

  public Integer queryUserBuyProductAccount(Map<String, Object> productresult)
      throws ServiceException;

  public List<String> checkProductStockNumber(String orderCode) throws ServiceException;

  public Integer byPrizeNumberCount(OrderMain orderMain) throws ServiceException;

  /**
   * 查询自营邮费信息
   * 
   * @param channel
   * @return
   * @throws SQLException
   */
  public SupplierFare querySelfFare(String channel) throws ServiceException;

  /**
   * 根据商品Id查询一个商品的销量
   * 
   * @param skuId
   * @return
   * @throws ServiceException
   */
  public Long getSalesVolume(String skuId);

  /**
   * 查询用户购买次数
   * 
   * @param loginId
   * @return
   * @throws ServiceException
   */
  public Integer queryUserBuyNum(Long loginId) throws ServiceException;

  /**
   * 支付前校验订单
   * 
   * @param userId
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public String checkPayOrder(Long userId, String orderCode, BigDecimal payMoney)
      throws ServiceException;

  /**
   * 校验订单、用户是否匹配
   * 
   * @param userId
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public boolean checkOrderUser(Long userId, String orderCode, Integer flag)
      throws ServiceException;

  /**
   * 查询支付回调订单信息
   * 
   * @param params
   * @return
   * @throws ServiceException
   */
  public Map<String, Object> queryOrderMainForPayCall(String orderCode) throws ServiceException;

  /**
   * App支付前校验订单
   * 
   * @param userId
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public String appCheckPayOrder(Long userId, String orderCode) throws ServiceException;



  public int queryYesterdayOrderCount() throws ServiceException;
  
  /**
   * 查询预售支付回调信息
   *
   *
   * @author KM 
   * @param mapc
   * @return
   * @throws ServiceException
   */
  public Map<String, Object> queryPayCallForYs(Map<String,String> mapc) throws ServiceException;
  
  /**
   * 查询需要支付的定金
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  BigDecimal findNeedToPayDeposit(String orderCode) throws ServiceException;

  public String checkPayDeposit(Long loginId, String orderCode, BigDecimal bigDecimal);
  
  public String checkPayRetainage(Long loginId, String orderCode, BigDecimal bigDecimal);

}
