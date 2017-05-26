package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.MyCouponDao;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;
import com.km.framework.persistence.impl.DaoImpl;

@Component
public class MyCouponDaoImpl extends DaoImpl implements MyCouponDao {

  //static Logger logger = Logger.getLogger(MyCouponDaoImpl.class);
  private static Logger logger = LoggerFactory.getLogger(MyCouponDaoImpl.class);

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 时代会员登录易创后查询健康网个人优惠券信息
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<CouponGrant> findCouponList(String eraNo) throws SQLException {
    return (ArrayList<CouponGrant>) this.sqlMapClient.queryForList("COUPON.findCouponList", eraNo);

  }

}
