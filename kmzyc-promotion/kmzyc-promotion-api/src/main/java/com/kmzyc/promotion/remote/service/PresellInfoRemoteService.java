package com.kmzyc.promotion.remote.service;

import com.kmzyc.promotion.optimization.vo.PresellProductVO;

public interface PresellInfoRemoteService {
  /**
   * 
   * CMS调促销接口 ,获取预售商品基本信息
   *
   * @author Administrator
   * @param skuId
   * @return
   */
  public PresellProductVO getPresellInfo(Long skuId);

  /**
   * 
   * 外部系统调促销接口 ,修改预售库存
   *
   * @author Administrator
   * @param presellId,skuId,count(正整数：已售数量+； 负整数：已售数量-)
   * @return
   */
  public String updatePresellStock(Long presellId, Long skuId, int count) throws Exception;

}
