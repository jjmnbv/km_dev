package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.OrderDictionary;
import com.pltfm.app.entities.OrderDictionaryExample;

@SuppressWarnings("unchecked")
public interface OrderDictionaryDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int countByExample(OrderDictionaryExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int deleteByExample(OrderDictionaryExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int deleteByPrimaryKey(long order_dictionary_id) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  void insert(OrderDictionary record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  void insertSelective(OrderDictionary record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  List selectByExample(OrderDictionaryExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  OrderDictionary selectByPrimaryKey(long order_dictionary_id) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int updateByExampleSelective(OrderDictionary record, OrderDictionaryExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int updateByExample(OrderDictionary record, OrderDictionaryExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int updateByPrimaryKeySelective(OrderDictionary record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_DICTIONARY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int updateByPrimaryKey(OrderDictionary record) throws SQLException;

  void updateList(List<OrderDictionary> records) throws SQLException;

  void deleteList(List<OrderDictionary> records) throws SQLException;

  void insertList(List<OrderDictionary> records) throws SQLException;

  public List<OrderDictionary> queryDictionaryByType(String type) throws SQLException;
}
