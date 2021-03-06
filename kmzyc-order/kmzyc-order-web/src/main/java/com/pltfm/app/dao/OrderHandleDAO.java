package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.OrderHandle;
import com.pltfm.app.entities.OrderHandleExample;

@SuppressWarnings("unchecked")
public interface OrderHandleDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  int countByExample(OrderHandleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  int deleteByExample(OrderHandleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  int deleteByPrimaryKey(Long handleNo) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  Long insert(OrderHandle record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  Long insertSelective(OrderHandle record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  List selectByExample(OrderHandleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  OrderHandle selectByPrimaryKey(Long handleNo) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  int updateByExampleSelective(OrderHandle record, OrderHandleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  int updateByExample(OrderHandle record, OrderHandleExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  int updateByPrimaryKeySelective(OrderHandle record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_HANDLE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  int updateByPrimaryKey(OrderHandle record) throws SQLException;
}
