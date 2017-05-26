package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.entities.OrderMain;

/**
 * 订单退换货服务接口
 * 
 * @author chenwei
 * @version 1.0
 * @since 2013-8-21
 */
@SuppressWarnings("unchecked")
public interface OrderReturnService {

  /**
   * 退换货申请
   * 
   * @param string
   * @param orderCode
   * @param alterType
   * @param alterNum
   * @param evidence
   * @param alterComment
   * @param batchNo
   * @param backType
   * @param name
   * @param address
   * @param phone
   */
  public int alter(OrderAlter oa) throws ServiceException;

  /**
   * 退换货审核
   * 
   * @param type
   * @param comment
   * @param orderAlterId
   * @throws ServiceException
   */
  public void alterCK(String operator, String alterCode, Short type, BigDecimal fareSubsidy,
      BigDecimal returnMoney,BigDecimal returnFare,BigDecimal returnSum, BigDecimal preferentialAmount, String comment)
      throws ServiceException;

  /**
   * 查询退换货列表
   */
  public List listAlter(Map<String, Object> map) throws ServiceException;

  /**
   * 退换货列表计数
   */
  public Integer listCount(Map<String, Object> map) throws ServiceException;

  /**
   * 查询退换货单操作
   */
  public List listOrderAlterOperates(String orderAlterCode) throws ServiceException;

  /**
   * 查询退换货单支付
   */
  public List listOrderAlterPays(String orderAlterCode) throws ServiceException;

  Map compute(String orderCode, Long orderItemId, Long alterNum) throws ServiceException;
  
  public Map compute(OrderMain om, OrderAlter oa) throws ServiceException; 

  public int savaPhoto(OrderAlterPhoto photo) throws ServiceException;

  public List getPhotoByBatchNo(String photoBatchNo) throws ServiceException;


  /**
   * 供应商平台提供导出 退换货订单信息
   * 
   * @param params 导出查询参数
   * @return String 导出excel的路径
   * @throws ServiceException
   */
  public String exportSellerAlterOrders(Map<String, Object> params) throws ServiceException;


  /**
   * 同步退换货订单信息到总部系统
   * 
   * @param lstAlterCode
   * @return
   * @throws ServiceException
   */
 /*删除总部会员对接  public int syncAlterOrderInfo2Base(List<String> lstOrderAlterCode) throws ServiceException;
  */
  
  /**
   * 超时未发货赔付审核
   */
  public int alterCKYS(String sYS, String alterCode, Short type, String comment) throws ServiceException;



}
