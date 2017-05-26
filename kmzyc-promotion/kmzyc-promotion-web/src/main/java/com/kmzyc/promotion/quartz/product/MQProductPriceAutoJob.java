package com.kmzyc.promotion.quartz.product;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.promotion.app.service.ProductPromotionMQService;
import com.kmzyc.promotion.util.RedisTemplate;

public class MQProductPriceAutoJob {
  public static Logger log = LoggerFactory.getLogger(MQProductPriceAutoJob.class);
  public static Object locker = new Object();
  public static Boolean isRunning = false;
  @Resource
  private ProductPromotionMQService productPromotionMQService;
  @Resource
  private RedisTemplate redisTemplate;

  public void execute() {
    try {
      log.info("mq 推送 start");

      productPromotionMQService.promotionOnlineNotify();

      log.info("mq 推送 end");
    } catch (Exception e) {
      log.error("mq推送产品价格信息失败" + e.getMessage(), e);
    }
  }

}
