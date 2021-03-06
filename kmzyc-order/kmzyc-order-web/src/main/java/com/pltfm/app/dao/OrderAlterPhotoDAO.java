package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.entities.OrderAlterPhotoExample;

@SuppressWarnings("unchecked")
public interface OrderAlterPhotoDAO {

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  int countByExample(OrderAlterPhotoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  int deleteByExample(OrderAlterPhotoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  int deleteByPrimaryKey(Long photoId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  Long insert(OrderAlterPhoto record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  Long insertSelective(OrderAlterPhoto record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  List selectByExample(OrderAlterPhotoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  OrderAlterPhoto selectByPrimaryKey(Long photoId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  int updateByExampleSelective(OrderAlterPhoto record, OrderAlterPhotoExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  int updateByExample(OrderAlterPhoto record, OrderAlterPhotoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  int updateByPrimaryKeySelective(OrderAlterPhoto record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.ORDER_ALTER_PHOTO
   * 
   * @ibatorgenerated Fri Sep 27 11:28:08 CST 2013
   */
  int updateByPrimaryKey(OrderAlterPhoto record) throws SQLException;
}
