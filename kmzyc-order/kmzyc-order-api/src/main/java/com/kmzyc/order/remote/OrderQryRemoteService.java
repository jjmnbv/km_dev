package com.kmzyc.order.remote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderMain;

/**
 * 订单查询接口
 * 
 * @author chenwei
 * @date 2013.08.09
 */
@SuppressWarnings("unchecked")
public interface OrderQryRemoteService extends Serializable {
  /**
   * 查询sku出货量
   */
  public Map countSKU(Date begin, Date end, List SKU) throws ServiceException;

  /**
   * 查询订单列表
   */
  public List listOrder(Map<String, Object> map) throws ServiceException;

  /**
   * 订单列表计数
   */
  public Integer listCount(Map<String, Object> map) throws ServiceException;

  /**
   * 按订单号查询
   */
  public OrderMain getOrderByCode(String order_code) throws ServiceException;

  /**
   * 按客户账户查询时间范围内的消费情况 入参的Map包含以下键值对
   * 
   * @param startDate Date 订单完成时间的下限
   * @param endDate Date 订单完成时间的上限
   * @param account String 客户账户
   * @param status Long 6为“已完成”
   */
  public BigDecimal getPersonalConsume(Map map) throws ServiceException;

  /**
   * 查询订单总金额
   */
  public BigDecimal listCountMoney(Map<String, Object> map) throws ServiceException;

  /**
   * 按订单号查询根订单
   */
  public OrderMain getRootOrderByCode(String orderCode) throws ServiceException;

  /**
   * 查询已结转订单数量
   */
  public Integer getOrderExecutCount(Map map) throws ServiceException;

  /**
   * 查询已结转订单列表
   */
  public List getOrderExecutList(Map map) throws ServiceException;

  /**
   * 查询订单集合，提供给咨询工具
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map queryOrderListForConsultation(Map map) throws ServiceException;

  /**
   * 根据订单号查询出库信息
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public List queryCarryStockOutVOByOrderCodes(List<String> orderCode) throws ServiceException;


  /**
   * 供应商导出所有订单
   * 
   * @param 查询参数
   * @return 导出的excel路径
   * @throws ServiceException
   */
  public String exportSellerOrders(Map map) throws ServiceException;

  /**
   * 同步订单信息到总部会员系统
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
 /*删除同步总部会员功能  public int syncOrderInfo2Base(List<String> lstOrderCode) throws ServiceException;*/

}
