package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.InvoiceInfo;
import com.kmzyc.b2b.vo.PayMoney;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.vobject.OrderVo;

public interface AccountInfoService {
  /**
   * 
   * <p>
   * Title: findByLoginId
   * </p>
   * <p>
   * Description: 获取账户信息
   * </p>
   * 
   * @param loingId
   * @return
   * @throws SQLException
   */
  public AccountInfo findByLoginId(Long loingId) throws ServiceException;

  public Coupon getCouponByCouponGrantId(Long couponGrantId);

  /**
   * 获取不可用优惠券
   *
   * @return
   */
  public List<HashMap<String,String>> getUnavailableCoupon(Map<String, Object> map) throws ServiceException;

  /**
   * 写支付信息到memcache
   * 
   * @param usessionId
   * @param obj
   */
  public void savePaymodelDeliveryInfo(String usessionId, Object obj) throws ServiceException;

  /**
   * 从缓存中读取发票信息
   * 
   * @param usessionId
   * @return
   */
  public InvoiceInfo getInvoiceInfoFormMemcache(String usessionId) throws ServiceException;

  /**
   * 持久化发票信息到缓存中
   * 
   * @param usessionId
   * @param invoiceInfo
   */
  public void saveInvoiceInfoToMemcache(String usessionId, InvoiceInfo invoiceInfo)
      throws ServiceException;

  /**
   * 从缓存中移除发票信息
   * 
   * @param usessionId
   */
  public void removeInvoiceInfoToMemcache(String usessionId) throws ServiceException;

  /**
   * 保存付款使用的金额到缓存中
   * 
   * @param usessionId
   * @param paymoney
   */
  public void savePayMoneyToMemCache(String usessionId, PayMoney paymoney) throws ServiceException;

  /**
   * 获取付款使用的金额
   * 
   * @param usessionId
   * @return
   */
  public PayMoney getPayMoneyFormMemCache(String usessionId) throws ServiceException;

  /**
   * 清除支付信息
   * 
   * @param usessionId
   */
  public void clearMemcache(String usessionId) throws ServiceException;

  /**
   * 保存
   * 
   * @param usessionId
   * @param coupon
   */
  public void updatePayMoneyToMemCache(String usessionId, Coupon coupon) throws ServiceException;

  /**
   * 查询用户可用优费券
   * 
   * @param map
   * @param usessionId
   * @return
   * @throws ServiceException
   */
  List<Coupon> findCouponGrants(Map<String, Object> map, String usessionId) throws ServiceException;


  /**
   * 将可用不可用优惠券保存到memCached中
   * 
   * @param map
   * @param sessionId
   */
  public void savaCouponsListCached(HashMap<String, List<HashMap<String, String>>> map,
      String sessionId);

  /**
   * 获取可用不可用优惠券信息,从memCached中
   * 
   * @param sessionId
   * @return
   */
  public HashMap<String, List<HashMap<String, String>>> getCouponsListCached(String sessionId);

  /**
   * 删除cached中的优惠券信息
   * 
   * @param sessionId
   */
  public void delCouponsListCached(String sessionId);

  /**
   * 获取支付信息
   * 
   * @return
   */
  public Map<String, String> getPaymodelDeliveryInfoFromMemcache(String usessionId)
      throws ServiceException;

  /**
   * 更新支付信息
   * 
   * @param usessionId
   * @param fieldName
   * @param val
   * @throws ServiceException
   */
  public void updatePayMoneyToMemCache(String usessionId, String fieldName, Object val)
      throws ServiceException;

  /**
   * 根据登录ID查询帐号信息，结算用
   * 
   * @param loginId
   * @return
   * @throws ServiceException
   */
  public AccountInfo getAccountInfoByLoginIdForSett(Long loginId) throws ServiceException;

  /**
   * 将用户信息保存到memCached中，供查询优惠券时使用
   * 
   * @param userId
   */
  public void savaAccountInfoCached(AccountInfo accountInfo, String userId);

  /**
   * 获取用户优惠券
   * 
   * @param orderVoList
   * @param uid
   * @param culateMoney
   * @param checkSellerId
   * @return
   * @throws ServiceException
   */
  public HashMap<String, List<Coupon>> findCouponGrants(List<OrderVo> orderVoList, String uid,
      BigDecimal culateMoney, Long checkSellerId) throws ServiceException;

  /**
   * 结算页提交订单时，检验优惠券是否可用,传入的优惠券id与缓存中用户可以优惠券比较
   * @param couponId
   * @param loginId
   * @return
   */
  public boolean checkCoupon(String couponId, String loginId);
}
