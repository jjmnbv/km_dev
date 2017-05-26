package com.kmzyc.promotion.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.promotion.app.dao.CouponDAO;
import com.kmzyc.promotion.app.enums.CouponStatus;
import com.kmzyc.promotion.app.vobject.Coupon;

public class StopJoinActivityJob implements Job {
  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(StopJoinActivityJob.class);

  private CouponDAO coupondao;


  @Override
  public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Coupon coupon = new Coupon();
      coupon.setEndtime(sdf.parse(sdf.format(new Date())));
      coupon.setTimeType((short) 1);
      coupondao = (CouponDAO) jobCtx.getJobDetail().getJobDataMap().get("coupondao");
      // 获取所有超过此时的规则
      List<Coupon> couponList = coupondao.selectEffectiveCoupon(coupon);
      for (Coupon coupon2 : couponList) {
        // 设置规则为已过期
        coupon2.setStatus(Long.parseLong(CouponStatus.HAVEPASSDATE_COUPONSTATUS.getType()));
        // 修改优惠券规则
        coupondao.updateCoupon(coupon2);
      }
    } catch (Exception e) {
      logger.error("定时任务优惠券规则异常" + e.getMessage(), e);
    }
  }


  public CouponDAO getCoupondao() {
    return coupondao;
  }

  public void setCoupondao(CouponDAO coupondao) {
    this.coupondao = coupondao;
  }

}
