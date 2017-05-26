package com.kmzyc.promotion.app.service;

import java.sql.SQLException;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantFlow;

/**
 * 优惠券的接口类
 * 
 * @author Administrator
 *
 */
public interface CouponGrantFlowService {

    public void insert(CouponGrantFlow couponGrantFlow) throws SQLException;

    public Page selectList(CouponGrantFlow couponGrantFlow, Page page) throws SQLException;



    public boolean insertFlow(CouponGrant couponGrant, Coupon coupon);



}
