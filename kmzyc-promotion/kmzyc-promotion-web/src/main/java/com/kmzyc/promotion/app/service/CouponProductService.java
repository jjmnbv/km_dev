package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.promotion.app.vobject.CouponProduct;

/**
 * 优惠券产品接口
 * 
 * @author Administrator
 * 
 */
public interface CouponProductService {

	/**
	 * 根据优惠券Id查询,产品
	 */
	public List<CouponProduct> queryListByCouponId(Long couponId) throws Exception;

	/**
	 * 根据优惠券id查询类别
	 */
	public List<CouponProduct> queryCateListByCouponId(Long couponId) throws Exception;

	/**
	 * 根据SKUCODE查询优惠券产品列表
	 * 
	 * @param skuCode
	 * @return
	 * @throws SQLException
	 */
	public List<CouponProduct> selectCouponProductBySkuId(String skuCode) throws Exception;

}
