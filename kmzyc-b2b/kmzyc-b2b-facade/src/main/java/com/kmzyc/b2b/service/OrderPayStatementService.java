package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.framework.exception.ServiceException;

public interface OrderPayStatementService {

  public List<OrderPayStatement> getByOrderCode(Map<String, String> map) throws ServiceException;

  public OrderPayStatement findrefundOrderPayStatement(String orderCode) throws ServiceException;

  /**
   * 根据第三方流水号查询订单信息
   * 
   * @param batchNo
   * @return
   * @throws SQLException
   */
  public Map findOrderInfoByOuterStatementNo(String batchNo) throws ServiceException;
  
  /**
   * 
   * 修改及插入支付流水 -- 订单支付流水中支付平台与实际不一致时更正支付流水为正确的
   * @param orderCode
   * @param platformCode
   */
  public void insertOrUpdateOrderPayStatement(String orderCode,String platformCode) throws ServiceException;
  
 /**
  * 预售支付流水查询
  *
  *
  * @author KM 
  * @param orderCode
  * @param ysFlage 1:支付定金流水,2.支付尾款流水
  * @return
  * @throws ServiceException
  */
  public OrderPayStatement findrefundOrderPayStatementForYs(Map<String,String> map) throws ServiceException;
}
