package com.kmzyc.b2b.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.model.CouponGrantFlow;
import com.kmzyc.b2b.model.CouponIssuingSetting;

public interface CouponDao {
  /**
   * 获取用户下所有可用的券
   * 
   * @param loginId
   * @return
   */
  public List<Coupon> queryCoupons(Map<String, Object> map) throws SQLException;

  public BigDecimal getcouponMoneyByCouponId(Long couponId) throws SQLException;

  public Coupon getCouponByCouponId(Long couponId) throws SQLException;

  public CouponGrant queryCouponGrantByCouponId(Map<String, String> paramMap) throws SQLException;

  public BigDecimal getcouponMoneyByCouponGrantId(Long couponGrantId) throws SQLException;

  public Coupon getCouponByCouponGrantId(Long couponGrantId) throws SQLException;

  public List<CouponGrant> findCouponGrants(Map<String, Object> map) throws SQLException;

  public List<Coupon> findcouponsBygrantIds(List<Long> list) throws SQLException;

  /**
   * 通过用户Id和优惠劵规则Id获取实体优惠劵
   * 
   * @param userId
   * @param couponId
   * @return
   * @throws SQLException
   */
  public CouponGrant getCouponGrantByUerIdAndCouponId(Map<String, Object> map) throws SQLException;

  public CouponGrant getCouponGrantByVo(CouponGrant couponGrant) throws SQLException;

  public CouponGrant getcouponByGrantCode(String grantCode) throws SQLException;

  /**
   * 插入优惠券发放表
   * 
   * @param couponGrant
   * @return
   */
  public Long insertCouponGrant(CouponGrant couponGrant) throws SQLException;

  /**
   * 插入优惠券发放设置表
   * 
   * @param setting
   * @return
   * @throws SQLException
   */
  public Long insertCouponIssuingSetting(CouponIssuingSetting setting) throws SQLException;

  /**
   * 插入优惠券操作流水表
   * 
   * @param flow
   * @return
   * @throws SQLException
   */
  public Long insertCouponGrantFlow(CouponGrantFlow flow) throws SQLException;


  public Map couponGrantInfo(int CouponGrantId) throws SQLException;


  public int updateCouponGrant(Map pMap) throws SQLException;


}
