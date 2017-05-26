package com.pltfm.app.service;

import java.math.BigDecimal;

import com.kmzyc.commons.exception.ServiceException;

public interface FareService {

  /**
   * 运费计算
   * 
   * @param typeId 运费规则，现已作废
   * @param sum 订单金额
   * @param weight 订单重量
   * @param flag 是否为省内，true为是省内，false为是省外
   * @param site 站点信息
   * @return
   * @throws ServiceException
   */
  public BigDecimal getFare(Long typeId, BigDecimal sum, BigDecimal weight, boolean flag,
      String site) throws ServiceException;

}
