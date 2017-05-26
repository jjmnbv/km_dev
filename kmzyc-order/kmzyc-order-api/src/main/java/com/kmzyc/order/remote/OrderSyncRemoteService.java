package com.kmzyc.order.remote;

import java.io.Serializable;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderSync;

/**
 * 各种回调订单系统的服务类接口
 * 
 * @author zengming
 * 
 */
public interface OrderSyncRemoteService extends Serializable {

  /**
   * 订单同步状态变化
   * 
   * @throws ServiceException
   */
  public String updateOrderSync(OrderSync orderSync) throws ServiceException;

  /**
   * 获取订单同步列表
   * 
   * @param paramMap 订单号 "orderCode" 下单账号 "customerAccount" 时代编号 "outCode" 同步状态 "syncFlag" 0 :成功
   *        1：失败 2：未同步
   * @return map 数据总条数 "count" 数据列表 "list" 类型 list<OrderSyncDetail>
   * @throws ServiceException
   */
  public Map<String, Object> getOrderSyncList(Map<String, Object> paramMap) throws ServiceException;

}
