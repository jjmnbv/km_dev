/**
 * Copyright 2013 coderDream's Studio All right reserved created on 2013-8-13 上午11:35:00
 * 
 */
package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.entities.OrderMain;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public interface OrderAssessInfoService {

  public void addAssessInfo(OrderAssessInfo assessInfo) throws SQLException;

  public List list(Long orderId) throws SQLException;

  public int deleteAllAssessInfoByOrderId();

  public OrderAssessInfo selectOrderAssessInf(Long orderAssessId) throws SQLException;

  public int deleteOrderAssessInfoByKey(Long orderAssessId) throws SQLException;

  public int updateOrderAssessInfo(OrderAssessInfo orderInfo) throws SQLException;

  public List showAllAssessInfo() throws SQLException;

  public Integer countByOrderId(Long orderId);

  /*
   * 批量删除
   */
  public void deleteAllAssessInfo(List<OrderAssessInfo> list) throws SQLException;

  /*
   * 根据评价信息ID
   */
  public void deleteAssessInfoByAssessInfoId(Long assessInfoId);

  /*
   * 查询所有的订单
   */

  public List<OrderMain> getAllOrderMain() throws SQLException;

  /*
   * 根据订单ID查询评价信息
   */
  public OrderAssessInfo selectAssessInfoByOrderId(Long orderId);

  /*
   * 根据条件查询所有的数量
   */
  public Integer getOrderMainCount(Map<String, String> map) throws SQLException;

  /**
   * 
   * 根据条件查询所有的订单
   * **/

  public List getOrderList(Map<String, String> map) throws SQLException;

}
