package com.pltfm.app.remote.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderItemQryRemoteService;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.sys.util.ErrorCode;

@Service("orderItemQryRemoteService")
@SuppressWarnings("unchecked")
public class OrderItemQryRemoteServiceImpl implements OrderItemQryRemoteService {

  private static final long serialVersionUID = 1138660420257064338L;
  @Resource
  private OrderItemQryService orderItemQryservice;
  @Resource
  private OrderPayStatementDAO orderPayStatementDAO;

  @Override
  public List listOrderItems(String orderCode) throws ServiceException {
    List list = null;
    try {
      list = orderItemQryservice.listOrderItems(orderCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_PRODUCT_DETAIL_ERROR, "查询订单项发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public List listOrderOperates(String orderCode) throws ServiceException {
    List list = null;
    try {
      list = orderItemQryservice.listOrderOperates(orderCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_OPERATE_DETAIL_ERROR, "查询订单操作发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public List listOrderPays(String orderCode) throws ServiceException {
    List list = null;
    try {
      list = orderItemQryservice.listOrderPays(orderCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_PAY_DETAIL_ERROR, "查询订单支付发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public List listOrderPreferentials(String orderCode) throws ServiceException {
    List list = null;
    try {
      list = orderItemQryservice.listOrderPreferentials(orderCode, null);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_PREFERENTIAL_DETAIL_ERROR, "查询订单优惠发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public List listOrderInvoiceItems(Long InvoiceId) throws ServiceException {
    List list = null;
    try {
      list = orderItemQryservice.listOrderInvoiceItems(InvoiceId);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_ORDER_PREFERENTIAL_DETAIL_ERROR, "查询订单发票明细发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public Invoice getInvoiceById(Long invoiceId) throws ServiceException {
    try {
      return orderItemQryservice.getInvoiceById(invoiceId);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_INVOICE_DETAIL_ERROR, "发票流水号" + invoiceId
          + "查询订单发票时发生异常：" + e.getMessage());
    }
  }

  @Override
  public OrderItem getOrderItemById(Long orderItemId) throws ServiceException {
    try {
      return orderItemQryservice.getOrderItemById(orderItemId);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "查询订单项" + orderItemId
          + "时发生异常：" + e.getMessage());
    }
  }

  @Override
  public Boolean checkIsAdditional(Map map) throws ServiceException {
    try {
      return orderPayStatementDAO.checkIsAdditional(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "查询订单项是否需要补单时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public List<OrderItem> getOrderItemByOrderCode(List<String> orderCodes) throws ServiceException {
    List<OrderItem> list = null;
    try {
      list = orderItemQryservice.getOrderItemPvByOrderCodes(orderCodes);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.DB_ERROR, "获取订单项pv值发生错误：" + e.getMessage());
    }
    return list;
  }

  @Override
  public List<OrderItem> getOrderItemBySingleOrderCode(String orderCodes) throws ServiceException {
    List<OrderItem> objs = null;
    try {
      objs = orderItemQryservice.getOrderItemBySingleOrderCodes(orderCodes);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.DB_ERROR, "获取订单项pv值发生错误：" + e.getMessage());
    }
    return objs;
  }

}
