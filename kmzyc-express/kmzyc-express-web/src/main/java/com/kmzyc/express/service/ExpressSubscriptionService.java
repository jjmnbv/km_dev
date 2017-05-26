package com.kmzyc.express.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.vobject.ExpressNoticeRequestVO;

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
   * 根据物流公司+物流单号查询订阅单据
   * 
   * @param paramMap
   * @return
   * @throws ServiceException
   */
  List<ExpressSubscription> querySubscriptionByLosCodeAndNo(Map<String, String> paramMap) throws Exception;


  /**
   * 查询订阅的信息及物流跟踪信息
   * 
   * @param logisticsCode
   * @param logisticsNo
   * @return
   * @throws ServiceException
   */
  List<ExpressSubscription> queryExpressSubWithTrackDetail(String logisticsCode, String logisticsNo)
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

  /**
   * 修改订阅信息
   * 
   * @param record
   * @return
   * @throws Exception
   */
  int updateSubscription(ExpressSubscription record) throws ServiceException;

  /**
   * 新增订阅信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  BigDecimal insertSubscription(ExpressSubscription record) throws ServiceException;

  /**
   * 执行订阅请求的操作(多线程)
   * 
   * @param record
   * @throws ServiceException
   */
  void executeSubscription(ExpressSubscription record) throws ServiceException;

  /**
   * 内部系统订阅到中间件
   * 
   * @param record
   * @return 返回订阅的订阅记录的ID
   * @throws ServiceException
   */
  BigDecimal executeInnerSubscription(ExpressSubscription record) throws ServiceException;

  /**
   * 执行快递100的订阅操作
   * 
   * @param record
   * @throws ServiceException
   */
  public void executeOutersubscription(ExpressSubscription record) throws ServiceException;

  /**
   * 处理快递100推送的信息
   * 
   * @param requestVO
   */
  int processExpressSubPushInfo(ExpressNoticeRequestVO requestVO) throws ServiceException;

  /**
   * 查询所有需要重新自动订阅的中止订阅记录
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryAllAbortSubscriptionList(Map<String, String> paramMap) throws Exception;

}
