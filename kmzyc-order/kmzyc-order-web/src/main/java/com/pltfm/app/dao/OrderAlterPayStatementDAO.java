package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.OrderAlterPayStatement;
import com.pltfm.app.entities.OrderAlterPayStatementExample;

@SuppressWarnings("unchecked")
public interface OrderAlterPayStatementDAO {

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int countByExample(OrderAlterPayStatementExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int deleteByExample(OrderAlterPayStatementExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int deleteByPrimaryKey(Long payStatementNo) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  Long insert(OrderAlterPayStatement record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  Long insertSelective(OrderAlterPayStatement record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  List selectByExample(OrderAlterPayStatementExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  OrderAlterPayStatement selectByPrimaryKey(Long payStatementNo) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByExampleSelective(OrderAlterPayStatement record, OrderAlterPayStatementExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByExample(OrderAlterPayStatement record, OrderAlterPayStatementExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByPrimaryKeySelective(OrderAlterPayStatement record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PAY_STATEMENT
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByPrimaryKey(OrderAlterPayStatement record) throws SQLException;

  BigDecimal getOrderAlterPay(Map map) throws SQLException;

  Boolean checkIsAdditional(String orderAlterCode) throws SQLException;

  BigDecimal getAlreadyRefundMoneyByPayMethod(Map map) throws SQLException;

  BigDecimal needToRefund(Map<String, Object> map) throws SQLException;

  List<OrderAlterPayStatement> queryOrderAlterPayStatement(Map<String, Object> queryMap) throws SQLException;

  List selectForYs(Map queryMap)  throws SQLException;

  List<OrderAlterPayStatement> queryOrderAlterPayStatementYS(Map<String, Object> queryMap) throws SQLException;

}
