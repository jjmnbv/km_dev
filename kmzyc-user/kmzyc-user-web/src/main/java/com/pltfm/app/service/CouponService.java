package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.CouponGrant;
import com.pltfm.app.vobject.Coupons;

public interface CouponService {


  int searchPageByCoupons(Coupons coupons) throws Exception;

  public List<CouponGrant> selectPageByCoupons(Coupons coupons) throws Exception;

}
