package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.FareType;
import com.pltfm.app.entities.FareTypeExample;
import com.pltfm.app.entities.FareTypeWithBLOBs;

@SuppressWarnings("unchecked")
public interface FareTypeDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int countByExample(FareTypeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int deleteByExample(FareTypeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int deleteByPrimaryKey(long type_id) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  void insert(FareTypeWithBLOBs record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  void insertSelective(FareTypeWithBLOBs record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  List selectByExampleWithBLOBs(FareTypeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  List selectByExampleWithoutBLOBs(FareTypeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  FareTypeWithBLOBs selectByPrimaryKey(long type_id) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int updateByExampleSelective(FareTypeWithBLOBs record, FareTypeExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int updateByExample(FareTypeWithBLOBs record, FareTypeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int updateByExample(FareType record, FareTypeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int updateByPrimaryKeySelective(FareTypeWithBLOBs record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int updateByPrimaryKey(FareTypeWithBLOBs record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.FARE_TYPE
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  int updateByPrimaryKey(FareType record) throws SQLException;
}
