package com.kmzyc.b2b.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.supplier.model.SupplierFare;

@SuppressWarnings("unchecked")
public interface OrderMainDAO {

  public List<OrderMain> findByOrderCode(Map<String, String> map) throws SQLException;

  public java.math.BigDecimal findNeedToPayMoney(String orderCode) throws SQLException;

  public List<OrderMain> findPaySuccessLaterStages(Map<String, String> map) throws SQLException;

  public BigDecimal queryPayMoney(String orderCode) throws SQLException;

  public BigDecimal findPaiedMoney(String orderCode) throws SQLException;

  /**
   * 查询活动期间某用户购买指定产品数量
   * 
   * @return
   * @throws SQLException
   */
  public BigDecimal querySumUserBuySKUNum(Map map) throws SQLException;

  public Integer byPrizeNumberCount(OrderMain orderMain) throws SQLException;

  public Integer queryUserBuyProductAccount(Map<String, Object> map) throws SQLException;

  /**
   * 查询自营邮费信息
   * 
   * @param channel
   * @return
   * @throws SQLException
   */
  public SupplierFare querySelfFare(String channel) throws SQLException;

  /**
   * 根据商品Id查询一个商品的销量
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  public Long getSalesVolume(String skuId) throws SQLException;

  /**
   * 查询用户购买次数
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  public Integer queryUserBuyNum(Long loginId) throws SQLException;

  /**
   * 校验订单、用户是否匹配
   * 
   * @param userId
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public Integer checkOrderUser(Map<String, Object> params) throws SQLException;

  /**
   * 查询支付回调订单信息
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public Map<String, Object> queryOrderMainForPayCall(String orderCode) throws SQLException;


  int queryYesterdayOrderCount(Map<String, String> params) throws Exception;

  /**
   * 查询预售订单需要支付的定金
   * @param orderCode
   * @return
   */
  public BigDecimal findNeedToPayDeposit(String orderCode) throws SQLException;
  
  /**
   * 查询预售支付回调订单信息
   *
   *
   * @author KM 
   * @param mapCondition
   * @return
   * @throws SQLException
   */
  public Map<String, Object> queryPayCallForYs(Map<String,String> mapCondition) throws SQLException;
}
