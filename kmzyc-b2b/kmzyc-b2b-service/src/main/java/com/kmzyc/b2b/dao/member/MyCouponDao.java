package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.CouponGrant;

public interface MyCouponDao extends Dao {
  /**
   * 时代会员登录易创后查询健康网个人优惠券信息
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public ArrayList<CouponGrant> findCouponList(String eraNo) throws SQLException;
}
