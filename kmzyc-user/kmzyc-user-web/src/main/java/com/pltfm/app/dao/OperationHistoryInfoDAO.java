package com.pltfm.app.dao;

import com.pltfm.app.vobject.OperationHistoryInfo;
import com.pltfm.app.vobject.OperationHistoryInfoExample;

import java.sql.SQLException;
import java.util.List;

public interface OperationHistoryInfoDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  int countByExample(OperationHistoryInfoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  int deleteByExample(OperationHistoryInfoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  int deleteByPrimaryKey(Integer nOperationHistoryId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  void insert(OperationHistoryInfo record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  void insertSelective(OperationHistoryInfo record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  List selectByExample(OperationHistoryInfoExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  OperationHistoryInfo selectByPrimaryKey(Integer nOperationHistoryId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  int updateByExampleSelective(OperationHistoryInfo record, OperationHistoryInfoExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  int updateByExample(OperationHistoryInfo record, OperationHistoryInfoExample example)
      throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  int updateByPrimaryKeySelective(OperationHistoryInfo record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table OPERATION_HISTORY_INFO
   *
   * @ibatorgenerated Fri Jul 12 09:09:31 CST 2013
   */
  int updateByPrimaryKey(OperationHistoryInfo record) throws SQLException;
}
