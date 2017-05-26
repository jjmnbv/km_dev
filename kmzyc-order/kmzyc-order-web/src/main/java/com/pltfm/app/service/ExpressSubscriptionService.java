package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;

@SuppressWarnings("unchecked")
public interface ExpressSubscriptionService {
  /**
   * 获取物流订阅信息列表
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  List queryExpressSubscriptionListByPage(Map<String, String> paramMap) throws ServiceException;

  /**
   * 获取物流订阅信息数量
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  int queryExpressSubscriptionCount(Map<String, String> paramMap) throws ServiceException;

  /**
   * 根据物流公司+物流单号查询订阅单据数量
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  int querySubCountByLosCodeAndNo(Map<String, String> paramMap) throws ServiceException;

  /**
   * 查询订阅的信息及物流跟踪信息
   * 
   * @param logisticsCode
   * @param logisticsNo
   * @return
   * @throws ServiceException
   */
  List queryExpressSubWithTrackDetail(String logisticsCode, String logisticsNo)
      throws ServiceException;

  /**
   * 根据主键查询订阅信息
   * 
   * @param record
   * @return
   * @throws Exception
   */
  ExpressSubscription selectSubscriptionByPrimaryKey(ExpressSubscription record)
      throws ServiceException;
}
