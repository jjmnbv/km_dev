package com.kmzyc.promotion.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.kmzyc.promotion.app.dao.CouponProductDAO;
import com.kmzyc.promotion.app.service.CouponProductService;
import com.kmzyc.promotion.app.vobject.CouponProduct;

@Repository("couponProductImpl")
public class CouponProductServiceImpl implements CouponProductService {

    @Resource(name = "couponProductdao")
    private CouponProductDAO couponProductdao;

    /**
     * 根据优惠券Id查询产品
     */
    @Override
    public List<CouponProduct> queryListByCouponId(Long couponId) throws Exception {

        List<CouponProduct> couponProduct = couponProductdao.selectByCouponId(couponId);
        return couponProduct;
    }

    /**
     * 根据优惠券Id查询类别
     */
    @Override
    public List<CouponProduct> queryCateListByCouponId(Long couponId) throws Exception {

        List<CouponProduct> couponProduct = couponProductdao.selectCateByCouponId(couponId);
        return couponProduct;
    }

    @Override
    public List<CouponProduct> selectCouponProductBySkuId(String skuCode) throws Exception {
        return couponProductdao.selectCouponProductBySkuId(skuCode);
    }

}
