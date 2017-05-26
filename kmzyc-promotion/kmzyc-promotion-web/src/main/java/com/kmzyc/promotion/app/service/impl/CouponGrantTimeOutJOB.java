package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.promotion.app.dao.CouponGrantFlowDAO;
import com.kmzyc.promotion.app.enums.CouponGrantFlowStatus;
import com.kmzyc.promotion.app.service.CouponGrantService;
import com.kmzyc.promotion.app.service.CouponGrantSetService;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantFlow;

public class CouponGrantTimeOutJOB implements Job {

  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(CouponGrantTimeOutJOB.class);
  private CouponGrantSetService couponGrantSetService;
  private CouponGrantService couponGrantService;
  private CouponService couponService;
  private CouponGrantFlowDAO couponGrantFlowDAO;

  public CouponGrantFlowDAO getCouponGrantFlowDAO() {
    return couponGrantFlowDAO;
  }

  public void setCouponGrantFlowDAO(CouponGrantFlowDAO couponGrantFlowDAO) {
    this.couponGrantFlowDAO = couponGrantFlowDAO;
  }

  public CouponService getCouponService() {
    return couponService;
  }

  public void setCouponService(CouponService couponService) {
    this.couponService = couponService;
  }

  public CouponGrantService getCouponGrantService() {
    return couponGrantService;
  }

  public void setCouponGrantService(CouponGrantService couponGrantService) {
    this.couponGrantService = couponGrantService;
  }

  public CouponGrantSetService getCouponGrantSetService() {
    return couponGrantSetService;
  }

  public void setCouponGrantSetService(CouponGrantSetService couponGrantSetService) {
    this.couponGrantSetService = couponGrantSetService;
  }

  @Override
  public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
    Date date = new Date();
    CouponGrant cgs = new CouponGrant();
    cgs.setEndTime(date);
    couponGrantService =
        (CouponGrantService) jobCtx.getJobDetail().getJobDataMap().get("couponGrantService");
    couponService = (CouponService) jobCtx.getJobDetail().getJobDataMap().get("couponService");
    couponGrantFlowDAO =
        (CouponGrantFlowDAO) jobCtx.getJobDetail().getJobDataMap().get("couponGrantFlowDAO");

    try {
      List<CouponGrant> list = couponGrantService.queryCouponGrantByTime(cgs);
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date now = new Date();
      for (CouponGrant cgset : list) {
        CouponGrant ltemp = new CouponGrant();
        ltemp.setCouponGrantId(cgset.getCouponGrantId());
        ltemp.setCouponStatus(new Long(5));// 设置为已过期
        couponGrantService.updateGrantStatus(ltemp);
        Coupon couponc = new Coupon();
        couponc.setCouponId(cgset.getCouponId());
        couponc = couponService.selectCoupon(couponc);
        // 开始发放,并且录入流水
        CouponGrantFlow record = new CouponGrantFlow();
        record.setCouponGrantFlowType(Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS
            .getType()));
        record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
        record.setCouponGrantId(ltemp.getCouponGrantId());
        record.setCouponId(cgset.getCouponId());
        record.setCouponGrantId(ltemp.getCouponGrantId());
        record.setCouponName(couponc.getCouponName());
        record.setOperatingPersonName("系统自动设置过期的优惠券");
        record.setRemark("自动设置过期的优惠券 ，优惠券操作类型为:已过期，    优惠券编号：" + cgset.getCouponId()
            + "，     优惠券发放id：" + cgset.getCouponGrantId() + "，   操作人： "
            + record.getOperatingPersonName());
        couponGrantFlowDAO.insert(record);
      }

    } catch (SQLException e) {
      logger.error("更新状态失败", e);
    } catch (Exception e1) {
      logger.error("更新状态失败", e1);

    }

  }


}
