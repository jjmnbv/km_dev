package com.kmzyc.b2b.threadhandler;

import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.CouponLoginInfo;
import com.kmzyc.b2b.service.CouponService;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;



@Component("couponHandler")
public class CouponHandler {

    private static Logger logger = LoggerFactory.getLogger(CouponHandler.class);


    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource(name = "couponService")
    private CouponService couponService;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;


    public static final String couponSortSetKey = "r_coupon";
    public static final String errorCouponMapKey = "r_coupon_error";

    public static Coupon generatCoupon() {

        Random random = new Random();
        int rand = random.nextInt(10) + 1;
        String json = "";
        if (rand <= 5) {
            // %50中奖率
            json = JSONArray.parseArray(ConfigurationUtil.getString("b2b_km_zd_coupon_ids"))
                    .getString(0);
        } else if (rand <= 8) {
            // %30中奖率
            json = JSONArray.parseArray(ConfigurationUtil.getString("b2b_km_zd_coupon_ids"))
                    .getString(1);
        } else {
            // %20中奖率
            json = JSONArray.parseArray(ConfigurationUtil.getString("b2b_km_zd_coupon_ids"))
                    .getString(2);
        }

        return JSONObject.toJavaObject(JSONObject.parseObject(json), Coupon.class);
    }

    public Coupon insertSendCoupons(final CouponLoginInfo loginInfo) {

        // 随机选券
        final Coupon coupon = generatCoupon();
        // 发送
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String loginId = loginInfo.getLoginId();
                try {
                    logger.info("砸金蛋活动:" + loginId + "发券" + coupon.getCouponId() + "开始");
                    // 插入优惠券信息

                    couponService.insertCouponInfo(loginInfo, coupon);
                    logger.info("砸金蛋活动:" + loginId + "发券" + coupon.getCouponId() + "成功");
                    // 新增redis
                    String couponStr = bulidCouponString(loginInfo.getLoginAccount(), coupon);
                    jedisCluster.lpush(couponSortSetKey, couponStr);
                    jedisCluster.srem(ConfigurationUtil.getString("b2b_xhw_resource_uid"), loginId);
                    // System.out.println("发送耗时：" + (System.currentTimeMillis() - aa));
                } catch (Exception e) {
                    // 保存redis
                    logger.error("砸金蛋活动:" + loginId + "发券" + coupon.getCouponId() + "异常", e);
                    jedisCluster.hset(errorCouponMapKey, loginId, coupon.getCouponId().toString());
                }
            }

            private String bulidCouponString(String loginAccount, Coupon coupon) {
                JSONObject json = new JSONObject();
                json.put("userName", getFuzzyName(loginAccount));
                json.put("couponName", coupon.getCouponName());
                return json.toJSONString();
            }

            /**
             * 模糊用户名
             * 
             * @param uName
             * @return
             */
            private String getFuzzyName(String uName) {
                return uName.substring(0, uName.length() - 3) + "***";
            }

        });
        return coupon;
    }
}
