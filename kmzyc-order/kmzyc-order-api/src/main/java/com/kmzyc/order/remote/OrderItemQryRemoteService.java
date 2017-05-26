package com.kmzyc.order.remote;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderItem;

/**
 * 订单查询接口
 * 
 * @author chenwei
 * @date 2013.08.09
 */
@SuppressWarnings("unchecked")
public interface OrderItemQryRemoteService extends Serializable {
  /**
   * 查询订单商品
   */
  public List listOrderItems(String orderCode) throws ServiceException;

  /**
   * 查询订单操作
   */
  public List listOrderOperates(String orderCode) throws ServiceException;

  /**
   * 查询订单支付
   */
  public List listOrderPays(String orderCode) throws ServiceException;

  /**
   * 查询订单优惠
   */
  public List listOrderPreferentials(String orderCode) throws ServiceException;

  /**
   * 查询发票
   */
  public Invoice getInvoiceById(Long invoiceId) throws ServiceException;

  /**
   * 查询发票明细
   */
  public List listOrderInvoiceItems(Long InvoiceId) throws ServiceException;

  /**
   * 查询订单项
   */
  public OrderItem getOrderItemById(Long orderItemId) throws ServiceException;

  /**
   * 是否需要补单
   */
  public Boolean checkIsAdditional(Map map) throws ServiceException;

  /**
   * 根据订单号获取订单项明细
   * 
   * @param orderCodes
   * @return
   * @throws ServiceException
   */
  public List<OrderItem> getOrderItemByOrderCode(List<String> orderCodes) throws ServiceException;

  public List<OrderItem> getOrderItemBySingleOrderCode(String orderCodes) throws ServiceException;

}
