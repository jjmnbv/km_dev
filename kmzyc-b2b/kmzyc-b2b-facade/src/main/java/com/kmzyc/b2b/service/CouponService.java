package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.CouponLoginInfo;
import com.kmzyc.framework.exception.ServiceException;

public interface CouponService {

    /**
     * 发送优惠券
     * 
     * @param loginInfo
     * @return
     */
    public Coupon sendCoupons(CouponLoginInfo loginInfo) throws ServiceException;

    /**
     * 插入优惠券信息
     * 
     * @param loginInfo
     * @param coupon
     * @throws SQLException
     */
    public void insertCouponInfo(CouponLoginInfo loginInfo, Coupon coupon) throws SQLException;

    public JSONArray queryCouponList(long start, long end);

    public String grantCouponForActivity(Long usrId) throws ServiceException;
}
