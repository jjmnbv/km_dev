package com.pltfm.app.remote.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderSyncRemoteService;
import com.pltfm.app.dao.OrderSyncDAO;
import com.pltfm.app.entities.OrderSync;
import com.pltfm.app.maps.OrderSyncStatus;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.sys.util.ErrorCode;

@Service("orderSyncRemoteService")
public class OrderSyncRemoteServiceImpl implements OrderSyncRemoteService {

  private static final long serialVersionUID = -1418685470204715581L;

  private Logger log = Logger.getLogger(OrderSyncRemoteServiceImpl.class);

  @Resource
  private OrderSyncDAO orderSyncDAO;

  /**
   * 根据订单号变更订单的同步状态,无记录录时新增 成功返回"1" 失败返回"0"
   * 
   * @throws ServiceException
   */
  @Override
  public String updateOrderSync(OrderSync orderSync) throws ServiceException {
    try {
      if (orderSync.getOrderCode() == null) {
        return "0";
      }
      if (OrderDictionaryEnum.OrderSyncFlag.unSync.getKey() == orderSync.getSyncFlag()) {
        orderSyncDAO.insert(orderSync);
      } else {
        orderSyncDAO.updateOrderSync(orderSync);
      }
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(ErrorCode.INNER_ORDER_SYNC_REPORT_ERROR, "远程调用订单同步接口时发生异常："
          + e.getMessage());

    }
    return "1";
  }

  /**
   * 获取订单同步列表
   * 
   * @param paramMap 订单号 "orderCode" 下单账号 "customerAccount" 时代编号 "outCode" 同步状态 "syncFlag" 0 :成功
   *        1：失败 2：未同步
   * @return map 数据总条数 "count" 数据列表 "list" 类型 list<OrderSyncDetail>
   * @throws ServiceException
   */
  @Override
  public Map<String, Object> getOrderSyncList(Map<String, Object> paramMap) throws ServiceException {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    try {
      String isFailOrder = (String) paramMap.get("isFailOrder");
      if (isFailOrder != null && isFailOrder.equals("0")) {
        paramMap.put("syncFlag", OrderSyncStatus.FAIL.getKey());
        resultMap.put("list", orderSyncDAO.listByMap(paramMap));
      } else {
        resultMap.put("count", orderSyncDAO.countByMap(paramMap));
        resultMap.put("list", orderSyncDAO.listByMap(paramMap));
      }
    } catch (SQLException e) {
      log.error(e);
    }
    return resultMap;
  }
}
