package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.vo.CpsTrackInfo;
import com.kmzyc.b2b.vo.RequestInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.pltfm.app.entities.OrderOutSideExtInfo;

/**
 * 推广统计Service
 * 
 * @author xiongliguang
 * 
 */
public interface PromotionStatisticsService {
  /**
   * 根据标识查询请求次数
   * 
   * @param flag
   * @return
   */
  public Integer queryRequestCount(RequestInfo requestInfo) throws ServiceException;

  /**
   * 根据下单时间查询某时间段内创建的订单数据
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderInfoByCreateDate(Map<String, Object> params) throws ServiceException;

  /**
   * 根据更新时间查询某时间段内更新过的订单信息
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderInfoByUpdateDate(Map<String, Object> params) throws ServiceException;

  /**
   * 根据订单号查询订单明细
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderDetailInfoByOrderCode(String orderCode) throws ServiceException;

  /**
   * 新增数据接口查询信息
   * 
   * @param params
   * @return
   * @throws ServiceException
   */
  public int insertRequestInfo(RequestInfo requestInfo) throws ServiceException;

  /**
   * 新增订单标识数据
   * 
   * @param orderInfo
   * @return
   * @throws ServiceException
   */
  public int insertOrderFlagInfo(OrderOutSideExtInfo orderInfo) throws ServiceException;

  /**
   * 新增cps跳转数据
   * 
   * @param cpsTrackInfo
   * @return
   * @throws ServiceException
   */
  public int insertCpsTrackInfo(CpsTrackInfo cpsTrackInfo) throws ServiceException;

  /**
   * 根据订单号查询订单明细,并根据产品编号对价格和数量进行合并 add by songmiao 2016-3-4
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderDetailInfoByOrderCodeAndProduct(String orderCode)
      throws ServiceException;
}
