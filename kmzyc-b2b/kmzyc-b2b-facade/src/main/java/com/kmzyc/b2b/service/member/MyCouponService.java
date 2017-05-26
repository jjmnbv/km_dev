package com.kmzyc.b2b.service.member;

import java.sql.SQLException;
import java.util.ArrayList;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.CouponGrant;

public interface MyCouponService {

  /**
   * 查询某个人的优惠券
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findCouponListByPage(Pagination page) throws SQLException;

  /**
   * 时代会员登录易创后查询健康网个人优惠券信息
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public ArrayList<CouponGrant> findCouponList(String eraNo) throws SQLException;

  /**
   * 查询会员的优惠券总数
   * 
   * @param userId
   * @return
   * @throws SQLException
   */
  public Long countCouponByUserId(Long userId, String status) throws SQLException;

  /**
   * 根据grantid查询发放记录
   * 
   * @param couponGrant
   * @return
   * @throws SQLException
   */
  public CouponGrant getGrantByGrantVo(CouponGrant couponGrant) throws SQLException;

  /**
   * 调用远程接口 激活优惠券
   */
  public int activitionCoupon(String grantId, int userId) throws Exception;

  /**
   * 根据激活码 查询对应的激活规则
   * 
   */
  public CouponGrant getCouponByGrantCode(String grantCode) throws SQLException;
}
