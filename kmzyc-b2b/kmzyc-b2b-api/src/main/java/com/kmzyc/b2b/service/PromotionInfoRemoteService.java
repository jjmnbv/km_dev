package com.kmzyc.b2b.service;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public interface PromotionInfoRemoteService {
  /** 获取指定金额满足活动的奖励数据 */
  public Long getPrizeDate(Long promotionInfoId, BigDecimal paidMoney);
}
