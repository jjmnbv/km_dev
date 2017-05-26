package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.dao.CouponDao;
import com.kmzyc.b2b.model.Coupon;
import com.kmzyc.b2b.model.CouponGrant;
import com.kmzyc.b2b.model.CouponGrantFlow;
import com.kmzyc.b2b.model.CouponIssuingSetting;
import com.kmzyc.b2b.model.CouponLoginInfo;
import com.kmzyc.b2b.service.CouponService;
import com.kmzyc.b2b.threadhandler.CouponHandler;
import com.kmzyc.b2b.util.SendRewardProcess;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.enums.CouponGrantFlowStatus;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;


@Service("couponService")
public class CouponServiceImpl implements CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
    private static final String BJ_COUNT =
            ConfigurationUtil.getString("b2b_km_activity_autoprize_count");
    private static int conBjCount =
            Integer.parseInt(StringUtil.isEmpty(BJ_COUNT) ? "100" : BJ_COUNT);

    private static final ExecutorService exeutor = Executors.newScheduledThreadPool(conBjCount);

    @Resource(name = "couponDaoImpl")
    private CouponDao couponDao;

    @Resource(name = "couponHandler")
    private CouponHandler couponHandler;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    public static AtomicInteger count = new AtomicInteger(0);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "emailMsg")
    private Destination destination;

    @Resource
    private CouponRemoteService couponRemoteService;

    @Override
    public Coupon sendCoupons(CouponLoginInfo loginInfo) throws ServiceException {
        if (null == loginInfo || StringUtil.isEmpty(loginInfo.getLoginId())) {
            return null;
        }
        Coupon coupon = null;
        try {
            int a = count.incrementAndGet();
            logger.info("砸金蛋并发数为" + a);
            System.out.println("砸金蛋并发数为" + a);
            if (a > ConfigurationUtil.getIntValue("b2b_km_zd_coupon_count")) {
                coupon = new Coupon();
                coupon.setCouponId(-1L);
                // System.out.println(a);
                logger.info("砸金蛋并发数超过了设置数量");
            } else {
                coupon = couponHandler.insertSendCoupons(loginInfo);
            }
        } catch (Exception e) {
            logger.error("------------->发送优惠券失败,系统异常", e);
        } finally {
            count.decrementAndGet();
        }
        return coupon;
    }

    @Override
    public void insertCouponInfo(CouponLoginInfo loginInfo, Coupon coupon) throws SQLException {
        // 插入优惠券设置表
        Long settingId = this.insertCouponIssuingSetting(loginInfo, coupon);
        // 插入优惠券发放表
        Long couponGrantId = this.insertCouponGrant(loginInfo, coupon, settingId);
        // 插入优惠券操作流水表
        // throw new SQLException();
        this.insertCouponGrantFlow(loginInfo, coupon, couponGrantId);
    }

    /**
     * 插入优惠劵发放设置
     */
    private Long insertCouponIssuingSetting(CouponLoginInfo loginInfo, Coupon coupon)
            throws SQLException {

        CouponIssuingSetting setting = new CouponIssuingSetting();
        setting.setCouponId(coupon.getCouponId());
        setting.setIssuingStartTime(coupon.getStarttime());
        setting.setIssuingEndTime(coupon.getEndtime());
        setting.setCreateTime(new Date());
        setting.setIsStatus(2L);// 设置状态为已完成
        setting.setIssuingCount(1L); // 发送数量
        setting.setCustomLeveId(loginInfo.getLeveId());
        setting.setCustomId(loginInfo.getLoginId());
        setting.setCouponGivetypeId(1L); // 手工发放
        setting.setCouponDesc("砸金蛋活动自动发放");

        return this.couponDao.insertCouponIssuingSetting(setting);
    }

    /**
     * 插入优惠券发放表
     */
    private long insertCouponGrant(CouponLoginInfo loginInfo, Coupon coupon, Long settingId)
            throws SQLException {

        Date date = new Date();

        CouponGrant cgrant = new CouponGrant();
        cgrant.setCouponId(coupon.getCouponId());
        cgrant.setCouponStatus(3L);// 未使用
        cgrant.setCustomId(Integer.valueOf(loginInfo.getLoginId()));
        cgrant.setGrantType(11L);

        cgrant.setStartTime(coupon.getStarttime());
        cgrant.setEndTime(coupon.getEndtime());

        cgrant.setActTime(date);
        cgrant.setCouponIssuingId(settingId);// 设置发放设置ID

        return couponDao.insertCouponGrant(cgrant);
    }

    private void insertCouponGrantFlow(CouponLoginInfo loginInfo, Coupon coupon, Long couponGrantId)
            throws SQLException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 开始发放,并且录入流水
        CouponGrantFlow record = new CouponGrantFlow();
        record.setCouponGrantFlowType(
                Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));

        try {
            record.setCreateDate(dateFormat.parse(dateFormat.format(new Date())));
        } catch (ParseException e) {
            logger.error("", e);
        }

        record.setCouponGrantId(couponGrantId);
        record.setCouponId(coupon.getCouponId());
        record.setCouponName(coupon.getCouponName());
        record.setCustomId(Long.valueOf(loginInfo.getLoginId()));
        record.setCustomer(loginInfo.getLoginAccount());
        record.setOperatingPersonName("system");
        record.setRemark("砸金蛋活动自动发放,优惠券编号：" + coupon.getCouponId() + ",优惠券发放id：" + couponGrantId
                + ", 操作人： " + record.getOperatingPersonName());

        this.couponDao.insertCouponGrantFlow(record);

    }


    @Override
    public JSONArray queryCouponList(long start, long end) {
        List<String> list;

        list = jedisCluster.lrange(CouponHandler.couponSortSetKey, start, end);

        if (list == null) {
            return null;
        }
        JSONArray o = new JSONArray();
        for (String s : list) {
            if (s != null) {
                o.add(JSONObject.parseObject(s));
            }
        }
        return o;
    }

    @Override
    public String grantCouponForActivity(Long usrId) throws ServiceException {
        if (!this.checkActivityRuleFromPropertiesFile()) {
            return "配置文件错误";
        }


        // 同用户并发限制
        Long synflag = jedisCluster.setnx(Constants.GRANT_COUPON_FOR_ACTIVITY + usrId, "1");

        if (synflag > 0) {
            try {
                jedisCluster.expire(Constants.GRANT_COUPON_FOR_ACTIVITY + usrId, 60 * 60); // 缓存过期
                // 1小时
                // 检查当前用户是否已发放过优惠券
                String couponCount =
                        jedisCluster.get(Constants.COUPON_USER.concat(usrId.toString()));
                String couponLimt = ConfigurationUtil.getString(Constants.COUPON_LIMIT_FOR_USER);
                if (couponCount != null && couponLimt != null
                        && Integer.parseInt(couponCount) >= Integer.parseInt(couponLimt)) {
                    jedisCluster.del(Constants.GRANT_COUPON_FOR_ACTIVITY + usrId);
                    return "您已领取过优惠券，无法再次领取！";
                }
                // 获取优惠券id
                String couponGrantId = ConfigurationUtil.getString(Constants.COUPON_GRANT_ID);
                exeutor.execute(new SendRewardProcess(usrId, couponGrantId, couponRemoteService,
                        jedisCluster));
            } catch (Exception e) {
                logger.error("发放优惠券失败,系统异常", e);
                jedisCluster.del(Constants.GRANT_COUPON_FOR_ACTIVITY + usrId);
                return "领取失败！系统异常！";
            }
        } else {
            return "请等待领取结果！";
        }

        return "";
    }

    /**
     * 从activity.properties校验活动规则，true:有效，false:活动无效
     *
     * @author Administrator
     */
    private boolean checkActivityRuleFromPropertiesFile() {
        // logger.info("从activity.properties配置文件中校验活动规则");
        StringBuilder sb = new StringBuilder();
        // 校验活动开始时间，start
        Date activityDate;
        ConfigurationUtil.getString("queryRefundReqURL");
        activityDate = ConfigurationUtil.getDate(Constants.COUPON_ACTIVITY_STARTTIME);
        if (activityDate == null) {
            sb.append("活动开始时间(").append(Constants.COUPON_ACTIVITY_STARTTIME).append("):空");
        }
        activityDate = ConfigurationUtil.getDate(Constants.COUPON_ACTIVITY_ENDTIME);
        if (activityDate == null) {
            sb.append("活动结束时间(").append(Constants.COUPON_ACTIVITY_STARTTIME).append("):空");
        }
        if (StringUtil.isEmpty(sb.toString())) {
            Integer time = this.getActivityStartTime(null);
            if (time > 0) {
                sb.append("活动开始时间(").append(Constants.COUPON_ACTIVITY_STARTTIME).append("):活动未开始;");
            }
            time = this.getActivityEndTime(null);
            if (time <= 0) {
                sb.append("活动开始时间(").append(Constants.COUPON_ACTIVITY_ENDTIME).append("):活动已过期;");
            }

        }
        if (StringUtil.isEmpty(sb.toString())) {
            // 优惠券id
            String couponGrantId = ConfigurationUtil.getString(Constants.COUPON_GRANT_ID);
            if (StringUtil.isEmpty(couponGrantId)) {
                sb.append("优惠券id(").append(Constants.COUPON_GRANT_ID).append("):为空;");
            }
        }
        // 校验活动开始时间，end
        if (!StringUtil.isEmpty(sb.toString())) {
            logger.error("活动配置文件activity.properties设置错误" + sb.toString());
            return false;
        }
        return true;
    }

    /**
     * 查询离活动开始还剩多少秒
     *
     * @param date 时间,用来判断活动的开始及结束,如果为空，则取系统当前时间
     */
    private Integer getActivityStartTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        Date acdate = ConfigurationUtil.getDate(Constants.COUPON_ACTIVITY_STARTTIME);
        return Integer.valueOf(String.valueOf((acdate.getTime() - date.getTime()) / 1000));

    }

    /**
     * 查询离活动结束还剩多少秒
     *
     * @param date 时间,用来判断活动的开始及结束,如果为空，则取系统当前时间
     */
    private Integer getActivityEndTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        Date acdate = ConfigurationUtil.getDate(Constants.COUPON_ACTIVITY_ENDTIME);
        return Integer.valueOf(String.valueOf((acdate.getTime() - date.getTime()) / 1000));

    }

}
