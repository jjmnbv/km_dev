package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.CouponDAO;
import com.pltfm.app.service.CouponService;
import com.pltfm.app.vobject.CouponGrant;
import com.pltfm.app.vobject.Coupons;

@Component(value = "couponService")
public class CouponServiceImpl implements CouponService {

  @Resource(name = "couponDAO")
  private CouponDAO couponDAO;

  public int searchPageByCoupons(Coupons coupons) throws Exception {
    int totalNum = couponDAO.selectCountByCoupons(coupons);
    return totalNum;
  }

  @Override
  public List<CouponGrant> selectPageByCoupons(Coupons coupons) throws Exception {
    return couponDAO.selectPageByCoupons(coupons);
  }
}
