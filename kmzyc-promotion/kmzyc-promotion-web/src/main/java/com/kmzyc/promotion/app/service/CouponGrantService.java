package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantExample;
import com.pltfm.app.dataobject.UserInfoDO;

public interface CouponGrantService {

    /**
     * 更新发放表中的优惠券状态
     */
    public Integer updateGrantStatus(CouponGrant couponGrant) throws SQLException;

    /**
     * 根据条件查询优惠券发放表
     */
    public List<CouponGrant> findGrantByCondition(CouponGrantExample grantExzample)
            throws SQLException;

    /**
     * 查找未过期且有效日期小于等于多少天的优惠券
     * 
     * @throws SQLException
     */
    public List<CouponGrant> selectUseLeaseDay(int remainDay) throws SQLException;


    /**
     * 按条件查询已发放的优惠券列表
     * 
     * @param pageCondition
     */
    public void queryAlreadyGrantCouponList(Page pageCondition, CouponGrant queryPara);

    /**
     * 根据优惠券有效结束时间查询出券
     * 
     * @return
     * @throws SQLException
     */
    public List<CouponGrant> queryCouponGrantByTime(CouponGrant cg) throws SQLException;

    public CouponGrant selectCouponGrantById(CouponGrant cg) throws SQLException;


    /**
     * 更新已到期的优惠券
     */
    public int updateExpiredCoupon() throws SQLException;

    /**
     * 给用户插入优惠券信息
     * 
     * @param user
     * @param couponNew 优惠券规则信息
     * @param couponGrantDetailType :优惠券发放明细类型,值取自类CouponGrantDetailType
     * @param couponGrantFlowStatus:优惠券流水操作类型,值取自类couponGrantFlowStatus
     * @throws SQLException
     */
    public void insertCouponInfo(UserInfoDO user, Coupon couponNew, String couponGrantDetailType,
            String couponGrantFlowStatus) throws SQLException;

}
