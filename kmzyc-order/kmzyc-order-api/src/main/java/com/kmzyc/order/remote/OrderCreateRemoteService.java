package com.kmzyc.order.remote;

import java.io.Serializable;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.vobject.OrderPreferentialVO;

/**
 * 订单生成接口
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-9-22
 */
public interface OrderCreateRemoteService extends Serializable {
  /**
   * 生成订单接口,针对普通商品
   * 
   * @param order 订单实体
   * @param orderItemList 订单明细列表
   * @param orderPreferentialVOList 订单优惠VO列表
   * @param orderPayStatementList 支付流水列表 
   * @return
   * @throws ServiceException
   */
  String createOrder(OrderMain order, List<OrderItem> orderItemList,
      List<OrderPreferentialVO> orderPreferentialVOList,
      List<OrderPayStatement> orderPayStatementList) throws ServiceException;

  /**
   * 生成订单接口,针对奖品
   * 
   * @param order 订单实体
   * @param orderItemList 订单明细列表
   * @param orderPreferentialVOList 订单优惠VO列表
   * @param orderPayStatementList 支付流水列表
   * @return
   * @throws ServiceException
   */
  String createOrderForPrize(OrderMain order, List<OrderItem> orderItemList,
      List<OrderPreferentialVO> orderPreferentialVOList,
      List<OrderPayStatement> orderPayStatementList) throws ServiceException;
  
  /**
   * 砍价活动 -- 拷贝数据到订单系统相关表中
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public String copyDateToOrder(String orderCode) throws ServiceException;
  /**
   * 砍价活动-- 退款接口
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public String returnFund(String orderCode) throws ServiceException;
  
  /**
   * 砍价活动 -- 拷贝支付流水数据到订单系统相关表中
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public String copyDateToOrderPayStatement(String orderCode) throws ServiceException;
}
