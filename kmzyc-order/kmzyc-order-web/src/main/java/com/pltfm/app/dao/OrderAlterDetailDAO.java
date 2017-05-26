package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.OrderAlterDetail;
import com.pltfm.app.entities.OrderAlterDetailExample;

@SuppressWarnings("unchecked")
public interface OrderAlterDetailDAO {

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int countByExample(OrderAlterDetailExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int deleteByExample(OrderAlterDetailExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int deleteByPrimaryKey(Long alterDetailId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  Long insert(OrderAlterDetail record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  Long insertSelective(OrderAlterDetail record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  List selectByExample(OrderAlterDetailExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  OrderAlterDetail selectByPrimaryKey(Long alterDetailId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByExampleSelective(OrderAlterDetail record, OrderAlterDetailExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByExample(OrderAlterDetail record, OrderAlterDetailExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByPrimaryKeySelective(OrderAlterDetail record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_DETAIL
   * 
   * @ibatorgenerated Thu Sep 05 18:38:07 CST 2013
   */
  int updateByPrimaryKey(OrderAlterDetail record) throws SQLException;

}
