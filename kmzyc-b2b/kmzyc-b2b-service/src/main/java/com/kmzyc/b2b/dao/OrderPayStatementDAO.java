package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.OrderPayStatement;

@SuppressWarnings("unchecked")
public interface OrderPayStatementDAO {

  public List<OrderPayStatement> queryByOrderCode(Map<String, String> map) throws SQLException;

  public OrderPayStatement findrefundOrderPayStatement(String orderCode) throws SQLException;

  /**
   * 根据第三方流水号查询订单信息
   * 
   * @param batchNo
   * @return
   * @throws SQLException
   */
  public Map findOrderInfoByOuterStatementNo(String batchNo) throws SQLException;

  /**
   * 查询订单的预支付平台编码
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public String queryReadyPayPlatCode(String orderCode) throws SQLException;
  
  /**
   * 修改订单支付流水
   * @param record
   * @return
   * @throws SQLException
   */
  public int updateByPrimaryKey(OrderPayStatement record) throws SQLException;
  
  /**
   * 新增订单支付流水
   * @param record
   * @return
   * @throws SQLException
   */
  public Long insert(OrderPayStatement record) throws SQLException;
  
  /**
   * 查询预售订单支付流水
   *
   *
   * @author KM 
   * @param orderCode
   * @param ysFlage 1：定金支付流水，2：尾款支付流水
   * @return
   * @throws SQLException
   */
  public OrderPayStatement findrefundOrderPayStatementForYs(Map<String,String> map) throws SQLException;
}
