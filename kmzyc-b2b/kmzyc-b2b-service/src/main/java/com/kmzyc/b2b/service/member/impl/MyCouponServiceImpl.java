package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.CouponDao;
import com.kmzyc.b2b.dao.member.MyCouponDao;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.service.member.MyCouponService;
import com.kmzyc.promotion.remote.service.CouponRemoteService;

// import com.km.framework.common.util.RemoteTool;

@Service
public class MyCouponServiceImpl implements MyCouponService {

    // private static Logger logger = Logger.getLogger(MyCouponServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(MyCouponServiceImpl.class);

    @Resource(name = "myCouponDaoImpl")
    private MyCouponDao myCouponDaoImpl;

    @Resource(name = "couponDaoImpl")
    private CouponDao couponDao;
    @Resource
    private CouponRemoteService couponRemoteService;

    @Override
    public Pagination findCouponListByPage(Pagination page) throws SQLException {
        @SuppressWarnings("unchecked")
        Map<String, Object> condition = (Map<String, Object>) page.getObjCondition();
        logger.info("开始查询我的优惠券,userId:" + condition.get("userId") + ",couponStatus:"
                + condition.get("couponStatus") + ",couponName:" + condition.get("couponName"));
        long startTime = System.currentTimeMillis();
        // 使用产品数据源

        page =
                myCouponDaoImpl.findByPage("COUPON.findCouponListByPage",
                        "COUPON.countCouponByPage", page);
        logger.info("查询我的优惠券列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return page;
    }

    /**
     * 查询会员的优惠券总数
     * 
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public Long countCouponByUserId(Long userId, String status) throws SQLException {
        logger.info("查询我的优惠券总数,userId:" + userId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("status", status);
        // 使用产品数据源

        return (Long) myCouponDaoImpl.findById("COUPON.countCouponByUserId", map);
    }

    /**
     * 根据grantid查询发放记录
     * 
     * @param couponGrant
     * @return
     * @throws SQLException
     */
    @Override
    public CouponGrant getGrantByGrantVo(CouponGrant couponGrant) throws SQLException {
        // 使用产品数据源

        CouponGrant grant = couponDao.getCouponGrantByVo(couponGrant);
        return grant;
    }

    /**
     * 根据激活码 查询对应的激活规则
     * 
     * @throws SQLException
     * 
     */
    @Override
    public CouponGrant getCouponByGrantCode(String grantCode) throws SQLException {
        // 使用产品数据源

        CouponGrant grant = couponDao.getcouponByGrantCode(grantCode);
        return grant;
    }

    /**
     * 返回参数:1：用户Id为空 ,2:优惠券激活码为空,3:优惠券激活码无效,4:优惠券激活码已激活 6：该激活码已过期，7：激活优惠券异常，8：激活优惠券成功
     */
    @Override
    public int activitionCoupon(String grantCode, int userId) throws Exception {
        // CouponRemoteService couponService =
        // (CouponRemoteService) RemoteTool
        // .getRemote(Constants.PROMOTION_SYSTEM_CODE, "couponService");
        int result = couponRemoteService.checkCouponGrant(userId + "", grantCode);
        logger.info("---优惠券激活调用远程接口返回结果：用户：" + userId + "激活优惠券" + grantCode + "远程返回结果" + result);
        return result;
    }

    /**
     * 时代会员登录易创后查询健康网个人优惠券信息
     */
    @Override
    public ArrayList<CouponGrant> findCouponList(String eraNo) throws SQLException {
        logger.info("开始查询我的优惠券,eraNo:" + eraNo);
        long startTime = System.currentTimeMillis();
        // 使用产品数据源

        ArrayList<CouponGrant> cgList = myCouponDaoImpl.findCouponList(eraNo);
        logger.info("查询我的优惠券列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return cgList;
    }

}
