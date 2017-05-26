package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;

/**
 * 订单同步
 * 
 * @author songmiao
 * 
 */
@SuppressWarnings("unchecked")
public interface OrderSyncService {

  /**
   * 统计个数
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  public int listCount(Map<String, Object> paramMap) throws ServiceException;

  /**
   * 获取订单同步列表
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  public List listOrder(Map<String, Object> paramMap) throws ServiceException;

  /**
   * 同步失败订单
   * 
   * @return
   * @throws ServiceException
   */
  public boolean syncFailOrders() throws ServiceException;

  /**
   * 批量同步
   * 
   * @param ids
   * @return
   * @throws ServiceException
   */
  public boolean syncOrderList(String[] ids) throws ServiceException;

}
