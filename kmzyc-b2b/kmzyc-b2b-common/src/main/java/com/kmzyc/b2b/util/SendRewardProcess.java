package com.kmzyc.b2b.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.constants.Constants;
import com.kmzyc.promotion.app.enums.CouponGrantDetailType;
import com.kmzyc.promotion.app.enums.CouponGrantFlowStatus;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;


/**
 * 
 * 发送奖励统一接口（优惠券、现金、产品）
 * 
 * @author KM
 *
 */

public class SendRewardProcess implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SendRewardProcess.class);
    // 用户id
    private Long registLoginId;
    // 奖励数据，优惠券id
    private String rewardDateId;

    private CouponRemoteService couponRemoteService;
    private JedisCluster jedisCluster;

    /** 缓存时间，单位秒 */
    public final static int expirse = 60 * 60 * 24 * 60;// 60天

    public SendRewardProcess() {

    }

    public SendRewardProcess(Long registLoginId, String rewardDateId,
            CouponRemoteService couponRemoteService, JedisCluster jedisCluster) {
        this.registLoginId = registLoginId;
        this.rewardDateId = rewardDateId;
        this.couponRemoteService = couponRemoteService;
        this.jedisCluster = jedisCluster;
    }

    @Override
    public void run() {
        try {
            this.sendRewardData(registLoginId, rewardDateId);
        } catch (Exception e) {
            logger.error("用户id:{},优惠券id:{},发送奖励异常", registLoginId, rewardDateId, e);
        }
    }

    /**
     * 
     * 发放奖励，（优惠券、现金、产品）
     *
     * @author Administrator
     * @param registLoginId
     * @param rewardDateId
     * @return
     */
    public boolean sendRewardData(Long registLoginId, String rewardDateId) {
        boolean rewardFlag = false;

        try {
            rewardFlag = this.sendRewardCoupon(registLoginId, rewardDateId);
            logger.info("用户loginid={}, 发送奖励：优惠券 " + (rewardFlag ? "成功" : "失败"), registLoginId);
            if (rewardFlag) {
                // 当前用户领取优惠券的数量加一
                String couponUser = Constants.COUPON_USER.concat(registLoginId.toString());
                jedisCluster.incr(couponUser);
                Date date = new Date();
                Date acdate = ConfigurationUtil.getDate(Constants.COUPON_ACTIVITY_ENDTIME);
                // 设置过期时间，活动结束时间-当前时间+60天
                jedisCluster.expire(couponUser,
                        Integer.parseInt(String.valueOf((acdate.getTime() - date.getTime()) / 1000))
                                + expirse);
            }
        } finally {
            jedisCluster.del(Constants.GRANT_COUPON_FOR_ACTIVITY + registLoginId);
        }
        return rewardFlag;
    }

    /**
     * 发送优惠券
     */
    public boolean sendRewardCoupon(Long loginId, String couponId) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("customId", loginId);
            map.put("couponIds", couponId);
            map.put("couponGrantDetailType",
                    CouponGrantDetailType.MANUAL_COUPONGRANTDETAILTYPE.getType());
            map.put("couponGrantFlowStatus", CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType());
            return couponRemoteService.generalGivenCoupon(map);
        } catch (Exception e) {
            logger.error("发送奖励：优惠券异常，loginid：" + loginId.intValue(), e);
            return false;
        }
    }

}
