package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.EmailRrsHis;
import com.kmzyc.b2b.model.EmailRrsHisExample;

public interface EmailRrsHisDAO {

  /**
   * EMAIL_RRS_HIS deleteByExample
   */
  int deleteByExample(EmailRrsHisExample example) throws SQLException;

  /**
   * EMAIL_RRS_HIS deleteByPrimaryKey
   */
  int deleteByPrimaryKey(Long emailRrsHisId) throws SQLException;

  /**
   * EMAIL_RRS_HIS insert
   */
  void insert(EmailRrsHis record) throws SQLException;

  /**
   * EMAIL_RRS_HIS insertSelective
   */
  void insertSelective(EmailRrsHis record) throws SQLException;

  /**
   * EMAIL_RRS_HIS selectByExample
   */
  @SuppressWarnings("rawtypes")
  List selectByExample(EmailRrsHisExample example) throws SQLException;

  /**
   * EMAIL_RRS_HIS selectByPrimaryKey
   */
  EmailRrsHis selectByPrimaryKey(Long emailRrsHisId) throws SQLException;

  /**
   * EMAIL_RRS_HIS updateByPrimaryKeySelective
   */
  int updateByPrimaryKeySelective(EmailRrsHis record) throws SQLException;

  /**
   * EMAIL_RRS_HIS updateByPrimaryKey
   */
  int updateByPrimaryKey(EmailRrsHis record) throws SQLException;

}
