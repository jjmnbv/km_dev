package com.pltfm.app.remote.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.service.OrderExecuteService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.sys.util.ErrorCode;

@Service("orderQryRemoteService")
@SuppressWarnings("unchecked")
public class OrderQryRemoteServiceImpl implements OrderQryRemoteService {
  /**
	 * 
	 */
  private static final long serialVersionUID = 6768885819082068399L;

  @Resource
  private OrderQryService orderQryservice;
  @Resource
  private OrderExecuteService orderExecuteService;

  @Override
  public BigDecimal listCountMoney(Map<String, Object> map) throws ServiceException {
    try {
      return orderQryservice.listCountMoney(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "远程查询订单列表总金额时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public List listOrder(Map<String, Object> map) throws ServiceException {
    List list = null;
    try {
      list = orderQryservice.listOrder(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_QUERY_ERROR, "远程查询订单列表发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public Integer listCount(Map<String, Object> map) throws ServiceException {
    Integer i = null;
    try {
      i = orderQryservice.listCount(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_QUERY_ERROR, "远程查询订单列表计数发生错误："
          + e.getMessage());
    }
    return i;
  }

  @Override
  public OrderMain getOrderByCode(String order_code) throws ServiceException {
    OrderMain om = null;
    try {
      om = orderQryservice.getOrderByCode(order_code);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_DETAIL_ERROR, "远程查询订单发生错误：" + e.getMessage());
    }
    return om;
  }

  @Override
  public Map countSKU(Date begin, Date end, List SKU) throws ServiceException {
    Map map = null;
    try {
      map = orderQryservice.countSKU(begin, end, SKU);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_QUERY_ERROR, "远程查询sku出货量发生错误："
          + e.getMessage());
    }
    return map;
  }

  @Override
  public BigDecimal getPersonalConsume(Map map) throws ServiceException {
    BigDecimal bd = null;
    try {
      bd = orderQryservice.getPersonalConsume(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_QUERY_ERROR, "远程查询用户指定时间的消费情况发生错误："
          + e.getMessage());
    }
    return bd;
  }

  @Override
  public OrderMain getRootOrderByCode(String orderCode) throws ServiceException {
    try {
      return orderQryservice.getRootOrderByCode(orderCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "远程查询根订单异常：" + e.getMessage());
    }
  }

  @Override
  public Integer getOrderExecutCount(Map map) throws ServiceException {
    try {
      return orderExecuteService.listCount(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "远程查询已结转订单数量异常："
          + e.getMessage());
    }
  }

  @Override
  public List getOrderExecutList(Map map) throws ServiceException {
    if (null == map.get("operator")) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "远程查询已结转订单列表异常：无操作人.");
    }
    try {
      return orderExecuteService.listOrder(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "远程查询已结转订单列表异常："
          + e.getMessage());
    }
  }

  /**
   * 查询订单集合，提供给咨询工具
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  public Map queryOrderListForConsultation(Map map) throws ServiceException {
    try {
      return orderQryservice.listOrderData(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "远程查询订单数据列表异常："
          + e.getMessage());
    }
  }

  /**
   * 根据订单号查询出库信息
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  @Override
public List queryCarryStockOutVOByOrderCodes(List<String> orderCodes) throws ServiceException {
    try {
      return orderQryservice.queryCarryStockOutVOByOrderCodes(orderCodes);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "根据订单号查询出库信息异常："
          + e.getMessage());
    }
  }

  @Override
  public String exportSellerOrders(Map map) throws ServiceException {
    try {
      return orderQryservice.exportSellerOrders(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出商家订单异常：" + e.getMessage());
    }
  }

/*删除同步总部会员  @Override
  public int syncOrderInfo2Base(List<String> lstOrderCode) throws ServiceException {
    try {
      return orderQryservice.syncOrderInfo2Base(lstOrderCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出商家订单异常：" + e.getMessage());
    }

  }*/
}
