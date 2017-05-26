package com.kmzyc.express.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
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
  List<ExpressSubscription> queryExpressSubWithTrackDetail(String logisticsCode, String logisticsNo) throws Exception;

  /**
   * 根据主键查询订阅信息
   * 
   * @param record
   * @return
   * @throws Exception
   */
  ExpressSubscription selectSubscriptionByPrimaryKey(ExpressSubscription record) throws Exception;

  /**
   * 修改订阅信息
   * 
   * @param record
   * @return
   * @throws Exception
   */
  int updateSubscriptionByPrimaryKey(ExpressSubscription record) throws Exception;

  /**
   * 新增订阅信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  BigDecimal insertSubscription(ExpressSubscription record) throws SQLException;


  /**
   * 查询所有需要重新自动订阅的中止订阅记录
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryAllAbortSubscriptionList(Map<String, String> paramMap) throws Exception;

  /**
   * 根据物流公司+物流单号查询订阅单据数量
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  int querySubCountByLosCodeAndNo(Map<String, String> paramMap) throws Exception;


  /**
   * 根据物流公司+物流单号查询订阅单据
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  List<ExpressSubscription> querySubscriptionByLosCodeAndNo(Map<String, String> paramMap) throws Exception;


  /**
   * 在redis缓存中插入订阅标识，
   * 
   * @param logisticsCode
   * @param logisticsNo
   * @throws Exception
   */
  void insertExpressSubProcessingCacheFlag(String logisticsCode, String logisticsNo)
      throws Exception;

  /**
   * 清除redis缓存中的订阅标识，
   * 
   * @param logisticsCode
   * @param logisticsNo
   * @throws Exception
   */
  void clearExpressSubProcessingCacheFlag(String logisticsCode, String logisticsNo)
      throws Exception;

  /**
   * 获取redis缓存中是否存在该订阅标识
   * 
   * @param logisticsCode
   * @param logisticsNo
   * @return
   * @throws Exception
   */
  boolean isExistExpressSubProcessingCacheFlag(String logisticsCode, String logisticsNo)
      throws Exception;
}
