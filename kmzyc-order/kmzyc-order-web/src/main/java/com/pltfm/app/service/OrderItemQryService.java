package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.OrderItem;

@SuppressWarnings("unchecked")
public interface OrderItemQryService {

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
  public List listOrderPreferentials(String orderCode, Long type) throws ServiceException;

  /**
   * 查询订单发票明细
   */
  public List listOrderInvoiceItems(Long InvoiceId) throws ServiceException;

  /**
   * 根据发票流水号查询订单发票
   */
  public Invoice getInvoiceById(Long invoiceId) throws ServiceException;

  /**
   * 
   * @param orderItemId
   * @return
   * @throws ServiceException
   */
  public OrderItem getOrderItemById(Long orderItemId) throws ServiceException;

  public List listOrderTree(String orderCode) throws ServiceException;

  public Integer queryGoodsCount(Map<String, String> map) throws ServiceException;

  public List<Map<String, Object>> queryGoodsReport(Map<String, String> map)
      throws ServiceException;

  public String goodsReportExportExcel(Map<String, String> map, Integer userId)
      throws ServiceException;

  public Boolean isSuit(String string) throws ServiceException;

  public Long selectOverplusNum(Long itemId) throws ServiceException;

  /**
   * 提供订单项查询接口
   * 
   * @param orderCodes
   * @return
   * @throws ServiceException
   */
  public List<OrderItem> getOrderItemPvByOrderCodes(List<String> orderCodes)
      throws ServiceException;

  public List<OrderItem> getOrderItemBySingleOrderCodes(String orderCodes) throws ServiceException;

  /**
   * 查询物流信息
   * 
   * @param logisticsCode
   * @param logisticsOrderNo
   * @return
   * @throws ServiceException
   */
  ExpressSubscription queryOrderItemLogisticsInfo(String logisticsCode, String logisticsOrderNo)
      throws ServiceException;

  public List listOrderItemsOut(String orderCode) throws ServiceException;

  public Map<String, Object> listGoodsMoney(Map<String, String> map) throws ServiceException;

}
