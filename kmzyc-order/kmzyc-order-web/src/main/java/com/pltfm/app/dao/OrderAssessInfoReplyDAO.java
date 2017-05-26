package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.OrderAssessInfoReply;
import com.pltfm.app.entities.OrderAssessInfoReplyExample;

@SuppressWarnings("unchecked")
public interface OrderAssessInfoReplyDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  int countByExample(OrderAssessInfoReplyExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  int deleteByExample(OrderAssessInfoReplyExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  int deleteByPrimaryKey(Long replyId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  Long insert(OrderAssessInfoReply record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  Long insertSelective(OrderAssessInfoReply record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  List selectByExample(OrderAssessInfoReplyExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  OrderAssessInfoReply selectByPrimaryKey(Long replyId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  int updateByExampleSelective(OrderAssessInfoReply record, OrderAssessInfoReplyExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  int updateByExample(OrderAssessInfoReply record, OrderAssessInfoReplyExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  int updateByPrimaryKeySelective(OrderAssessInfoReply record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ASSESS_INFO_REPLY
   * 
   * @ibatorgenerated Thu Aug 15 16:02:11 CST 2013
   */
  int updateByPrimaryKey(OrderAssessInfoReply record) throws SQLException;

  /**
   * 回复信息的查询
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  List selectByOrderAssessInfoId(Long assessInfoId) throws SQLException;

  /**
   * 回复信息的记录数
   * 
   * @param map
   * @return
   * @throws SQLException
   */
  int countByAssessInfoId(Long assessInfoId) throws SQLException;
}
