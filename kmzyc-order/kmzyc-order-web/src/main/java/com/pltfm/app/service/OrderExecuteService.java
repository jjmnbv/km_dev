package com.pltfm.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderCarry;

/**
 * 结转服务接口
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-8-7
 */
@SuppressWarnings("unchecked")
public interface OrderExecuteService {

  /**
   * 结转列表计数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Integer listCount(Map map) throws ServiceException;

  /**
   * 查处订单结转列表
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List<OrderCarry> listOrder(Map map) throws ServiceException;

  /**
   * 查询结转最近的时间
   * 
   * @param operator
   * @return
   * @throws ServiceException
   */
  public Date queryMaxCreateDate(String operator) throws ServiceException;

  /**
   * 订单结转统一入口
   * 
   * @param symbol
   * @param executeCondMap
   * @return
   */
  public OrderCarry OrderExecuteEntrance(final String symbol,
      final Map<String, Object> executeCondMap) throws ServiceException;

  /**
   * 移除结转标识
   * 
   * @param symbol
   * @throws ServiceException
   */
  public void removeExecuteSymbol(String symbol) throws ServiceException;
}
