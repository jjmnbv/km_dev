package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.OrderPayStatementDAO;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.entities.OrderPayStatementExample;

@Repository("orderPayStatementDAO")
@SuppressWarnings("unchecked")
public class OrderPayStatementDAOImpl extends BaseDAO implements OrderPayStatementDAO {
  @Override
public int countByExample(OrderPayStatementExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject(
            "KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public int deleteByExample(OrderPayStatementExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public int deleteByPrimaryKey(Long payStatementNo) throws SQLException {
    OrderPayStatement key = new OrderPayStatement();
    key.setPayStatementNo(payStatementNo);
    int rows =
        sqlMapClient.delete("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public Long insert(OrderPayStatement record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_insert", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public Long insertSelective(OrderPayStatement record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_insertSelective", record);
    return (Long) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public List selectByExample(OrderPayStatementExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_selectByExample",
            example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public OrderPayStatement selectByPrimaryKey(Long payStatementNo) throws SQLException {
    OrderPayStatement key = new OrderPayStatement();
    key.setPayStatementNo(payStatementNo);
    OrderPayStatement record =
        (OrderPayStatement) sqlMapClient.queryForObject(
            "KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public int updateByExampleSelective(OrderPayStatement record, OrderPayStatementExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_updateByExampleSelective",
            parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public int updateByExample(OrderPayStatement record, OrderPayStatementExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public int updateByPrimaryKeySelective(OrderPayStatement record) throws SQLException {
    int rows =
        sqlMapClient.update(
            "KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  @Override
public int updateByPrimaryKey(OrderPayStatement record) throws SQLException {
    int rows =
        sqlMapClient.update("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_updateByPrimaryKey",
            record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * KMORDER.ORDER_PAY_STATEMENT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  private static class UpdateByExampleParms extends OrderPayStatementExample {
    private static final long serialVersionUID = 1L;
    private Object record;

    public UpdateByExampleParms(Object record, OrderPayStatementExample example) {
      super(example);
      this.record = record;
    }

    @SuppressWarnings("unused")
    public Object getRecord() {
      return record;
    }
  }

  @Override
  public BigDecimal getOrderPay(Map map) throws SQLException {
    BigDecimal pay =
        (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_PAY_STATEMENT.getOrderPay", map);
    return null == pay ? BigDecimal.ZERO : pay;
  }

  @Override
  public BigDecimal getOrderRealPay(Map map) throws SQLException {
    BigDecimal pay =
        (BigDecimal) sqlMapClient
            .queryForObject("KMORDER_ORDER_PAY_STATEMENT.getOrderRealPay", map);
    return null == pay ? BigDecimal.ZERO : pay;
  }

  @Override
public void updateList(List<OrderPayStatement> records) throws SQLException {
    super.batchUpdateData(
        "KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_updateByPrimaryKeySelective", records);
  }

  @Override
public void deleteList(List<OrderPayStatement> records) throws SQLException {
    super
        .batchDeleteData("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_deleteByPrimaryKey", records);
  }

  @Override
public void insertList(List<OrderPayStatement> records) throws SQLException {
    super.batchInsertData("KMORDER_ORDER_PAY_STATEMENT.ibatorgenerated_insert", records);
  }

  @Override
  public BigDecimal getPayPreferentialNoByOrderCode(String orderCode) throws SQLException {
    return (BigDecimal) sqlMapClient.queryForObject(
        "KMORDER_ORDER_PAY_STATEMENT.getPayPreferentialNoByOrderCode", orderCode);
  }

  @Override
  public List<OrderPayStatement> getPayPreferentialByOrderCode(String orderCode)
      throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_ORDER_PAY_STATEMENT.getPayPreferentialByOrderCode", orderCode);
  }

  @Override
  public Boolean checkIsAdditional(Map map) throws SQLException {
    Integer result =
        (Integer) sqlMapClient.queryForObject("KMORDER_ORDER_PAY_STATEMENT.checkIsAdditional", map);
    return null == result ? Boolean.FALSE : (0 != result.intValue());
  }

  @Override
  public List<OrderPayStatement> selectAdditionalList(Map map) throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_ORDER_PAY_STATEMENT.getAdditionalList", map);
  }

  @Override
  public List<OrderPayStatement> selectFreezeList(String orderCode) throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_ORDER_PAY_STATEMENT.getFreezeList", orderCode);
  }

  @Override
  public Integer querySaleReportCount(Map<String, String> map) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("KMREPORT.SQL_QUERY_SALE_REPORT_COUNT", map);
  }

  @Override
  public List<Map<String, Object>> querySaleReport(Map<String, String> map) throws SQLException {
    return sqlMapClient.queryForList("KMREPORT.SQL_QUERY_SALE_REPORT",
        map);
  }

  @Override
  public List<Map<String, Object>> saleReportExportExcel(Map<String, String> map)
      throws SQLException {
    return sqlMapClient.queryForList("KMREPORT.SQL_SALE_REPORT_EXPORT",
        map);
  }

  /**
   * 根据订单号、状态查询支付流水
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
public List<OrderPayStatement> queryOrderPayStatement(Map<String, Object> params)
      throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_ORDER_PAY_STATEMENT.SQL_QUERY_ORDER_PAY_STATEMENT", params);
  }

  /**
   * 获取订单支付信息
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  @Override
public Map<String, BigDecimal> getOrderPayInfo(String orderCode) throws SQLException {
    return (Map<String, BigDecimal>) sqlMapClient.queryForObject(
        "KMORDER_ORDER_PAY_STATEMENT.SQL_QUERY_ORDER_PAY_INFO", orderCode);
  }

  /**
   * 查询第三方准备支付流水
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  @Override
  public OrderPayStatement queryOrderTHDReadyPayStatement(String orderCode) throws SQLException {
    return (OrderPayStatement) sqlMapClient.queryForObject(
        "KMORDER_ORDER_PAY_STATEMENT.SQL_QUERY_ORDER_THD_READY_PAY_STATEMENT", orderCode);
  }

  @Override
  public BigDecimal getOrderPayByOrderCode(String orderCode) throws SQLException {
    BigDecimal pay =
        (BigDecimal) sqlMapClient.queryForObject("KMORDER_ORDER_ALTER_PAY_STATEMENT.getOrderPayByOrderCode", orderCode);
    return null == pay ? BigDecimal.ZERO : pay;
  }

  @Override
  public List<OrderPayStatement> getPayInfoForKJ(String orderCode) throws SQLException {
    List<OrderPayStatement> payInfoList = new ArrayList<OrderPayStatement>();
    payInfoList = sqlMapClient.queryForList("KMORDER_ORDER_PAY_STATEMENT.getPayInfo", orderCode);
    return payInfoList;
  }

  @Override
  public List<OrderPayStatement> selectAdditionalListForYs(Map admap) throws SQLException {
    return sqlMapClient.queryForList(
        "KMORDER_ORDER_PAY_STATEMENT.getAdditionalListForYs", admap);
  }
}
