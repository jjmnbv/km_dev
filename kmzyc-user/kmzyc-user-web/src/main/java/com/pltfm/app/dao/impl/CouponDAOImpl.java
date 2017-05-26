package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.CouponDAO;
import com.pltfm.app.vobject.CouponGrant;
import com.pltfm.app.vobject.Coupons;

@Component(value = "couponDAO")
public class CouponDAOImpl implements CouponDAO {


  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 查询优惠券信息总数
   * 
   * @param
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int selectCountByCoupons(Coupons coupons) throws SQLException {
    // 总条数
    Integer count = (Integer) sqlMapClient.queryForObject("COUPON.getCouponsCount", coupons);
    return count;
  }


  @SuppressWarnings("unchecked")
  public List<CouponGrant> selectPageByCoupons(Coupons coupons) throws SQLException {
    List<CouponGrant> pageList = sqlMapClient.queryForList("COUPON.searchPageByCoupons", coupons);
    return pageList;
  }

}
