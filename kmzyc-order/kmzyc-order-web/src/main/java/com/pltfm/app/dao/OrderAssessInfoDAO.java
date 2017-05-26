package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.entities.OrderAssessInfoExample;

@SuppressWarnings("unchecked")
public interface OrderAssessInfoDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  int countByExample(OrderAssessInfoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  int deleteByExample(OrderAssessInfoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  int deleteByPrimaryKey(Long assessInfoId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  Long insert(OrderAssessInfo record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  Long insertSelective(OrderAssessInfo record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  List selectByExample(OrderAssessInfoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  OrderAssessInfo selectByPrimaryKey(Long assessInfoId) throws SQLException;

  /**
   * 通过roderCode查订单评价信息
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  OrderAssessInfo selectByOrderCode(String orderCode) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  int updateByExampleSelective(OrderAssessInfo record, OrderAssessInfoExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  int updateByExample(OrderAssessInfo record, OrderAssessInfoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  int updateByPrimaryKeySelective(OrderAssessInfo record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO
   * 
   * @ibatorgenerated Mon Sep 09 19:13:09 CST 2013
   */
  int updateByPrimaryKey(OrderAssessInfo record) throws SQLException;

  /**
   * 查询评价列表计数
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public Integer countByMap(Map<String, String> map) throws SQLException;

  /**
   * 查询出评价列表
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  public List<OrderAssessInfo> selectByMap(Map<String, String> map) throws SQLException;

  /**
   * 根据orderCode删除订单评价
   * 
   * @param orderCode
   * @throws SQLException
   */
  public void deleteByOrderCode(String orderCode) throws SQLException;

  public int bathInsertOrderAssessInfo(List<OrderAssessInfo> infoList) throws SQLException;
}
