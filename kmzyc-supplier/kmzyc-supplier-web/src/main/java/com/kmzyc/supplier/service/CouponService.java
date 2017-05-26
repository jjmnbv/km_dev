package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.km.framework.page.Pagination;

public interface CouponService {

    /**
     * 查询优惠券列表
     * @param page
     * @param coupon
     * @return
     * @throws Exception
     */
	Pagination queryCouponList(Pagination page, Coupon coupon) throws ServiceException;
}
