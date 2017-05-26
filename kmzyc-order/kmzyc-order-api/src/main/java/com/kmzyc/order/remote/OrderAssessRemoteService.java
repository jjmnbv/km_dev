package com.kmzyc.order.remote;

import java.io.Serializable;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAssessInfo;

/**
 * 订单生成接口
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-9-22
 */
public interface OrderAssessRemoteService extends Serializable {
  /**
   * 生成评价
   * 
   * @param orderAssessInfo
   * @param assessType
   * @param assessName
   * @param state
   * @param om
   * @return
   * @throws ServiceException 返回值1：成功；0：失败
   */
  int createAssessInfo(OrderAssessInfo orderAssessInfo, String[] assessType, String[] assessName,
      String[] state, String orderCode) throws ServiceException;

  /**
   * 查询订单评价
   * 
   * @return Map<String, Object> assessInfo:评价信息 assessDetailList评价明细集合
   * @throws ServiceException
   */
  public Map<String, Object> queryAssessByOrderCode(String orderCode) throws ServiceException;
}
