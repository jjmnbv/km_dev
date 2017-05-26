package com.pltfm.app.dao;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;

@SuppressWarnings("unchecked")
public interface ExpressSubscriptionDAO {
  /**
   * 查询物流订阅
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryExpressSubscriptionList(Map<String, String> paramMap) throws Exception;

  /**
   * 根据查询物流订阅数量
   * 
   * @param paramMap
   * @return
   * @throws Exception
   */
  int queryExpressSubscriptionCount(Map<String, String> paramMap) throws Exception;

  /**
   * 根据物流公司编码和物流查询当前订阅的信息及物流跟踪信息
   * 
   * @param logisticsCode
   * @param logisticsNo
   * @return
   * @throws Exception
   */
  List queryExpressSubWithTrackDetail(String logisticsCode, String logisticsNo) throws Exception;

  /**
   * 根据主键查询订阅信息
   * 
   * @param record
   * @return
   * @throws Exception
   */
  ExpressSubscription selectSubscriptionByPrimaryKey(ExpressSubscription record) throws Exception;

  /**
   * 根据物流公司+物流单号查询订阅单据数量
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  int querySubCountByLosCodeAndNo(Map<String, String> paramMap) throws Exception;


}
