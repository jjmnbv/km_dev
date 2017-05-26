package com.pltfm.app.dao;

import com.pltfm.app.vobject.CouponGrant;
import com.pltfm.app.vobject.Coupons;

import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {
  int selectCountByCoupons(Coupons coupons) throws SQLException;

  List<CouponGrant> selectPageByCoupons(Coupons coupons) throws SQLException;
}
