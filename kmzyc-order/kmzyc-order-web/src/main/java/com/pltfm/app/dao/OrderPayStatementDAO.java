package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPayStatementExample;

@SuppressWarnings("unchecked")
public interface OrderPayStatementDAO {
  int countByExample(OrderPayStatementExample example) throws SQLException;

  int deleteByExample(OrderPayStatementExample example) throws SQLException;

  int deleteByPrimaryKey(Long payStatementNo) throws SQLException;

  Long insert(OrderPayStatement record) throws SQLException;

  Long insertSelective(OrderPayStatement record) throws SQLException;

  List selectByExample(OrderPayStatementExample example) throws SQLException;

  OrderPayStatement selectByPrimaryKey(Long payStatementNo) throws SQLException;

  int updateByExampleSelective(OrderPayStatement record, OrderPayStatementExample example)
      throws SQLException;

  int updateByExample(OrderPayStatement record, OrderPayStatementExample example)
      throws SQLException;

  int updateByPrimaryKeySelective(OrderPayStatement record) throws SQLException;

  int updateByPrimaryKey(OrderPayStatement record) throws SQLException;

  /**
   * 获得已支付金额(各种方式、全部)
   * 
   * @param orderId
   * @return
   * @throws SQLException
   */
  BigDecimal getOrderPay(Map map) throws SQLException;

  /**
   * 获得订单实付金额(余额支付+在线支付)
   * 
   * @param orderId
   * @return
   * @throws SQLException
   */
  BigDecimal getOrderRealPay(Map map) throws SQLException;

  void updateList(List<OrderPayStatement> records) throws SQLException;

  void deleteList(List<OrderPayStatement> records) throws SQLException;

  void insertList(List<OrderPayStatement> records) throws SQLException;

  BigDecimal getPayPreferentialNoByOrderCode(String orderCode) throws SQLException;

  List<OrderPayStatement> getPayPreferentialByOrderCode(String orderCode) throws SQLException;

  Boolean checkIsAdditional(Map map) throws SQLException;

  List<OrderPayStatement> selectAdditionalList(Map admap) throws SQLException;

  /**
   * 销售报表数量
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  Integer querySaleReportCount(Map<String, String> map) throws SQLException;

  /**
   * 导出销售报表
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> querySaleReport(Map<String, String> map) throws SQLException;

  /**
   * 销售报表
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  List<Map<String, Object>> saleReportExportExcel(Map<String, String> map) throws SQLException;

  List<OrderPayStatement> selectFreezeList(String orderCode) throws SQLException;

  /**
   * 根据订单号、状态查询支付流水
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<OrderPayStatement> queryOrderPayStatement(Map<String, Object> params)
      throws SQLException;

  /**
   * 获取订单支付信息
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  public Map<String, BigDecimal> getOrderPayInfo(String orderCode) throws SQLException;

  /**
   * 查询第三方准备支付流水
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public OrderPayStatement queryOrderTHDReadyPayStatement(String orderCode) throws SQLException;
  
  /**
   * 查询订单orderCode康美通已支付金额
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public BigDecimal getOrderPayByOrderCode(String orderCode) throws SQLException;
  
  /**
   * 查找订单已完成的支付信息
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<OrderPayStatement> getPayInfoForKJ(String orderCode) throws SQLException;
  
  /**
   * 查询预售补单支付流水
   * TODO 描述这个方法的作用<br/> 
   *
   * @author KM 
   * @param admap
   * @return
   * @throws SQLException
   */
  public List<OrderPayStatement> selectAdditionalListForYs(Map admap) throws SQLException;
}
