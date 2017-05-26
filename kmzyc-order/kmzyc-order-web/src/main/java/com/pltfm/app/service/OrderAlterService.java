package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;

/**
 * 订单变更模块总体服务接口
 * 
 * @author zengming
 * @version 1.0
 * @since 2013-8-2
 */
public interface OrderAlterService {

  /**
   * 仓库拆单
   * 
   * @param orderList
   * @param orderItemMap
   * @param operator
   * @return
   * @throws ServiceException
   */
  public void splitOrderByWarehouse(List<OrderMain> orderList,
      Map<String, List<OrderItem>> orderItemMap,
      Map<String, Map<Long, List<OrderItem>>> splitOrderCodes, String operator)
      throws ServiceException;
}
