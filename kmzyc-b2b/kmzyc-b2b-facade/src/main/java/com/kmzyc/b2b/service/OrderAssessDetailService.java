package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.OrderAssessDetail;
import com.kmzyc.b2b.model.OrderAssessInfo;
import com.kmzyc.framework.exception.ServiceException;

/*
 * 
 * 订单详细评价
 */
public interface OrderAssessDetailService {

  /**
   * 根据ordercode查询某个订单明细的订单评分
   */
  public List<OrderAssessDetail> findOrderAssessDetailByCondition(
      OrderAssessDetail orderDetailConditon) throws Exception;

  /**
   * 判断此订单是否已经评分
   */
  public boolean orderAssessComplete(String orderCode) throws Exception;

  /**
   * 查询订单的评论信息
   * 
   * @author luoyi
   * @date 2013/12/18
   */
  public OrderAssessInfo findAssessInfoByOrderCode(String orderCode) throws Exception;

  /**
   * 查询店铺评分
   * 
   * @param supplierId
   * @return
   * @throws ServiceException
   */
  public Map<String, Object> queryShopScore(Long supplierId) throws ServiceException;
}
