package com.kmzyc.promotion.quartz.product;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.promotion.app.service.CouponGrantService;
import com.kmzyc.promotion.sys.util.AppApplicationContextUtil;

/**
 * 优惠券过期定时器。
 * 
 * @author Administrator
 *
 */

public class MQCouponExpiredJob {

  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(MQCouponExpiredJob.class);

  public void executeInternal() {
    // CouponService couponService = (CouponService)
    // AppApplicationContextUtil.getApplicationContext().getBean("CouponService");
    CouponGrantService couponGrantService =
        (CouponGrantService) AppApplicationContextUtil.getApplicationContext().getBean(
            "CouponGrantService");
    // CouponGrantFlowService couponGrantFlowService = (CouponGrantFlowService)
    // AppApplicationContextUtil.getApplicationContext().getBean("CouponGrantFlowService");
    logger.info("==================开始进行优惠券的过期定时器任务！");
    long start = System.currentTimeMillis();
    try {
      // //查询可设置过期优惠券
      // Date date=new Date();
      // CouponGrant cgs=new CouponGrant();
      // cgs.setEndTime(date);
      // List<CouponGrant> list=couponGrantService.queryCouponGrantByTime(cgs);
      // //更新过期优惠券状态为已过期
      // for(CouponGrant cgset:list){
      // CouponGrant ltemp=new CouponGrant();
      // ltemp.setCouponGrantId(cgset.getCouponGrantId());
      // ltemp.setCouponStatus(new Long(5));//设置为已过期
      // couponGrantService.updateGrantStatus(ltemp);
      // Coupon couponc=new Coupon();
      // couponc.setCouponId(cgset.getCouponId());
      // couponc=couponService.selectCoupon(couponc);
      // //开始发放,并且录入流水
      // boolean flowResult = couponGrantFlowService.insertFlow(cgset,couponc);
      // if(flowResult==false){
      // log.error("优惠券流水信息添加异常");
      // }else{
      // log.error("优惠券流水信息添加成功");
      // }
      //
      // }

      // update by houlin 将定时任务改为一条更新的sql语句 将插入流水信息去掉
      int i = couponGrantService.updateExpiredCoupon();
      logger.info("------优惠券过期定时任务处理成功,处理时间为：" + (System.currentTimeMillis() - start) / 1000
          + "秒 ,更新优惠券状态条数：" + i + "张");
    } catch (SQLException e) {
      logger.error("优惠券更新过期状态定时器任务报错：" + e.getMessage(), e);
    } catch (Exception e) {
      logger.error("优惠券更新过期状态定时器任务报错：" + e.getMessage(), e);
    }
  }
}
