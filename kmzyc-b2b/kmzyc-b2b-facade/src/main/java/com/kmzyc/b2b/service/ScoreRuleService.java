package com.kmzyc.b2b.service;

import java.math.BigDecimal;
import java.util.List;

import com.kmzyc.b2b.model.ScoreRule;
import com.kmzyc.framework.exception.ServiceException;

public interface ScoreRuleService {
  List<ScoreRule> findAllRule() throws Exception;

  /**
   * 获取购物积分
   * 
   * @return
   * @throws ServiceException
   */
  public int getBuyScore(BigDecimal amount) throws ServiceException;
}
