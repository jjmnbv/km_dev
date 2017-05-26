package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.promotion.app.service.CouponGrantSetService;
import com.kmzyc.promotion.app.vobject.CouponGrantSeting;

public class StopCouponActivityJob implements Job {
  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(StopCouponActivityJob.class);
  private CouponGrantSetService couponGrantSetService;

  public CouponGrantSetService getCouponGrantSetService() {
    return couponGrantSetService;
  }

  public void setCouponGrantSetService(CouponGrantSetService couponGrantSetService) {
    this.couponGrantSetService = couponGrantSetService;
  }

  @Override
  public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
    Date date = new Date();
    CouponGrantSeting cgs = new CouponGrantSeting();
    cgs.setIssuingEndTime(date);
    couponGrantSetService =
        (CouponGrantSetService) jobCtx.getJobDetail().getJobDataMap().get("couponGrantSetService");
    try {
      List<CouponGrantSeting> list = couponGrantSetService.selectBytime(cgs);
      List<CouponGrantSeting> listcgs = new ArrayList<CouponGrantSeting>();
      for (CouponGrantSeting cgset : list) {
        CouponGrantSeting ltemp = new CouponGrantSeting();
        ltemp.setCouponIssuingId(cgset.getCouponIssuingId());
        ltemp.setIsStatus(new Long(2));// 设置为已完成
        listcgs.add(ltemp);

      }
      couponGrantSetService.batchUpdateCouponG(listcgs);

    } catch (SQLException e) {
      logger.error("更新状态失败", e);
    }

  }

}
