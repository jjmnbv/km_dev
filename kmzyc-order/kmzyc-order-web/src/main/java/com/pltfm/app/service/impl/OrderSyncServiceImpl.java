package com.pltfm.app.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.service.OrderPvSyncRemoteService;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderSyncDAO;
import com.pltfm.app.maps.OrderSyncStatus;
import com.pltfm.app.service.OrderSyncService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.sys.util.ErrorCode;

@Service("orderSyncService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class OrderSyncServiceImpl extends BaseService implements OrderSyncService {
  private static Logger logger = Logger.getLogger(OrderSyncServiceImpl.class);

  @Resource
  private OrderSyncDAO orderSyncDAO;
  
  @Resource
  private OrderPvSyncRemoteService orderPvSyncRemoteService;

  /**
   * 获取订单同步计数
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  @Override
  public int listCount(Map<String, Object> paramMap) throws ServiceException {
    try {
      return orderSyncDAO.countByMap(paramMap);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单同步列表计数时发生异常："
          + e.getMessage());
    }
  }

  /**
   * 获取订单同步列表
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  @Override
  public List listOrder(Map<String, Object> paramMap) throws ServiceException {
    try {
      return orderSyncDAO.listByMap(paramMap);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单同步列表发生异常："
          + e.getMessage());
    }
  }

  /**
   * 同步失败订单
   * 
   * @return
   * @throws ServiceException
   */
  @Override
  public boolean syncFailOrders() throws ServiceException {
    boolean res = false;
    try {
      List<String> orderCodeList =
          orderSyncDAO.querySyncOrderCode(OrderDictionaryEnum.OrderSyncFlag.failed.getKey());
      if (null != orderCodeList && !orderCodeList.isEmpty()) {
        Map result = orderPvSyncRemoteService.orderPvSync(orderCodeList);
        
        if (null != result && !result.isEmpty()) {
//          List<OrderSync> osList = new ArrayList<OrderSync>();
          for (Object obj : result.keySet()) {
            Object rsObj = result.get(obj);
            int rs = 0;
            if (null != rsObj) {
              rs = Integer.parseInt(String.valueOf(rsObj));
            }
//            OrderSync os = new OrderSync();
//            os.setOrderCode(String.valueOf(obj));
            if (rs == OrderSyncStatus.SUCCESS.getKey() || rs == OrderSyncStatus.REPEAT.getKey()) {
//              os.setSyncFlag(OrderDictionaryEnum.OrderSyncFlag.success.getKey());
                res = true;
            } 
//            else {
//              os.setSyncFlag(OrderDictionaryEnum.OrderSyncFlag.failed.getKey());
//            }
//            osList.add(os);
          }
//          orderSyncDAO.updateOrderSync(osList);
        }
      }
    } catch (Exception e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "订单同步同步所有失败订单时发生异常："
          + e.getMessage());
    }
    return res;
  }

  /**
   * 批量同步
   * 
   * @param ids
   * @return
   * @throws ServiceException
   */
  @Override
  public boolean syncOrderList(String[] ids) throws ServiceException {
    boolean res = false;
    try {
      List<String> orderCodeList = Arrays.asList(ids);
      Map result = orderPvSyncRemoteService.orderPvSync(orderCodeList);
      if (null != result && !result.isEmpty()) {
//        List<OrderSync> osList = new ArrayList<OrderSync>();
        for (Object obj : result.keySet()) {
          Object rsObj = result.get(obj);
          int rs = 0;
          if (null != rsObj) {
            rs = Integer.parseInt(String.valueOf(rsObj));
          }
//          OrderSync os = new OrderSync();
//          os.setOrderCode(String.valueOf(obj));
          if (rs == OrderSyncStatus.SUCCESS.getKey() || rs == OrderSyncStatus.REPEAT.getKey()) {
//            os.setSyncFlag(OrderDictionaryEnum.OrderSyncFlag.success.getKey());
              res = true;
          } 
//          else {
//            os.setSyncFlag(OrderDictionaryEnum.OrderSyncFlag.failed.getKey());
//          }
//          osList.add(os);
        }
//        orderSyncDAO.updateOrderSync(osList);
//        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "订单同步发生异常：" + e.getMessage());
    }
    return res;
  }
}
