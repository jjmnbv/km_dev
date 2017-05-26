package com.kmzyc.express.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.service.ExpressSubscriptionService;
import com.kmzyc.express.util.constant.ExpressSubConstants;

/**
 * 物流信息重新订阅
 * 
 * @author xlg
 * 
 */
public class LogisticsReSub extends QuartzJobBean {
  private Logger log = Logger.getLogger(LogisticsReSub.class);
  private ExpressSubscriptionService expressSubscriptionService;

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  @Override
  public void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
    try {
      System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
      // 获取所有中止，且中止次数小于4次的订阅记录
      Map<String, String> paramMap = new HashMap<String, String>();
      Date latestPushDateEnd = new Date(new Date().getTime() - ExpressSubConstants.RE_SUB_DELAY_MS);
      paramMap.put("uselessFlag", String.valueOf(ExpressSubConstants.FLAG_USEABLE));
      paramMap.put("trackStatus", ExpressSubConstants.TrackStatus.ABORT.getKey());
      paramMap.put("maxCount", String.valueOf(ExpressSubConstants.MAX_ABORT_NUM));
      paramMap.put("latestPushDateEnd", dateFormat.format(latestPushDateEnd));

      List listData = expressSubscriptionService.queryAllAbortSubscriptionList(paramMap);

      // 自动执行订
      if (listData != null && listData.size() > 0) {
        for (Object obj : listData) {
          expressSubscriptionService.executeSubscription((ExpressSubscription) obj);
        }
      }
    } catch (Exception e) {
      log.error(e);
    }
  }

  public ExpressSubscriptionService getExpressSubscriptionService() {
    return expressSubscriptionService;
  }

  public void setExpressSubscriptionService(ExpressSubscriptionService expressSubscriptionService) {
    this.expressSubscriptionService = expressSubscriptionService;
  }

}
