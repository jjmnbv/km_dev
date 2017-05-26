package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponProduct;

/**
 * 优惠券的接口类
 * 
 * @author Administrator
 * 
 */
public interface CouponService {

    /**
     * 优惠券名称重复问题验证
     * 
     * @param map
     * @return
     * @throws Exception
     */
    int selectCouponName(Map<String, String> map) throws Exception;

    /**
     * 优惠券规则有效定时任务
     * 
     * @throws Exception
     */
    void creatJob(Long couponId) throws Exception;

    /**
     * 修改优惠券规则
     * 
     * @param coupon
     * @param couponProduct
     * @param categoryIds
     * @return
     * @throws Exception
     */
    public int updateCoupon(Coupon coupon, List<CouponProduct> couponProduct, String categoryIds)
            throws Exception;

    /**
     * 添加优惠券规则
     * 
     * @param coupon
     * @throws Exception
     */
    int insertRule(Coupon coupon, List<CouponProduct> couponProduct, String categoryIds)
            throws Exception;

    /**
     * 条件查询规则
     * 
     * @author lijianjun
     * @param coupon
     * @return
     * @throws SQLException
     */
    Page selectByCondition(Page pageParam, Coupon coupon) throws Exception;

    /**
     * 查询优惠券列表
     */
    public Page queryCouponList(Page pageParam, Coupon coupon) throws Exception;

    /**
     * 根据优惠券id 查询
     */
    public Coupon queryCouponById(Long couponId) throws Exception;

    /**
     * 修改优惠券方法
     */
    public void editCoupon(Coupon coupon, List<CouponProduct> couponProduct, String categoryIds)
            throws SQLException;

    /**
     * 删除优惠券方法
     */
    public void delCoupon(Coupon coupon) throws SQLException;

    /**
     * 验证优惠券名称是否重复
     */
    public int checkCouponNameRepeat(String couponName) throws SQLException;

    /**
     * 定时任务： 查询过期的优惠券规则
     */
    public List<Coupon> queryExpiredCoupon() throws SQLException;

    /**
     * 定时任务：更新优惠券的状态
     */
    public void updateCouponStatus(Coupon coupon) throws SQLException;

    /**
     * 更新优惠券
     */
    public int updateCouponById(Coupon coupon) throws SQLException;

    /**
     * 查询优惠券规则
     * 
     * @param coupon
     * @return
     * @throws Exception
     */
    Coupon selectCoupon(Coupon coupon) throws Exception;

}
