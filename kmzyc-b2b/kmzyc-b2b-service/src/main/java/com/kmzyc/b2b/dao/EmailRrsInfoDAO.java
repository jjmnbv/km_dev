package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.EmailRrsInfo;
import com.kmzyc.b2b.model.EmailRrsInfoExample;

/**
 * 邮件订阅内容DAO接口
 * 
 * @author luoyi
 * @createDate 2013/10/12
 * 
 */
public interface EmailRrsInfoDAO {

  /**
   * EMAIL_RRS_INFO deleteByPrimaryKey
   */
  int deleteByPrimaryKey(Integer emailRrsId) throws SQLException;

  /**
   * EMAIL_RRS_INFO insert
   */
  void insert(EmailRrsInfo record) throws SQLException;

  /**
   * EMAIL_RRS_INFO insertSelective
   */
  void insertSelective(EmailRrsInfo record) throws SQLException;

  /**
   * EMAIL_RRS_INFO selectByExample
   */
  @SuppressWarnings("rawtypes")
  List selectByExample(EmailRrsInfoExample example) throws SQLException;

  /**
   * EMAIL_RRS_INFO selectByPrimaryKey
   */
  EmailRrsInfo selectByPrimaryKey(Integer emailRrsId) throws SQLException;

  /**
   * EMAIL_RRS_INFO updateByPrimaryKeySelective
   */
  int updateByPrimaryKeySelective(EmailRrsInfo record) throws SQLException;

  /**
   * EMAIL_RRS_INFO updateByPrimaryKey
   */
  int updateByPrimaryKey(EmailRrsInfo record) throws SQLException;
}
