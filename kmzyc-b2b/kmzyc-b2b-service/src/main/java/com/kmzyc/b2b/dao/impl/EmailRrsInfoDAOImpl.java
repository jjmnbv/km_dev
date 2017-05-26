package com.kmzyc.b2b.dao.impl;

/**
 * 邮件订阅内容DAO实现类
 * 
 * @author luoyi
 * @createDate 2013/10/12
 * 
 */

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.EmailRrsInfoDAO;
import com.kmzyc.b2b.model.EmailRrsInfo;
import com.kmzyc.b2b.model.EmailRrsInfoExample;

@Repository("emailRrsInfoDAO")
public class EmailRrsInfoDAOImpl implements EmailRrsInfoDAO {
  /**
   * EMAIL_RRS_INFO
   */
  @Resource
  private SqlMapClient sqlMapClient;


  /**
   * EMAIL_RRS_INFO deleteByPrimaryKey
   */
  public int deleteByPrimaryKey(Integer emailRrsId) throws SQLException {
    EmailRrsInfo _key = new EmailRrsInfo();
    _key.setEmailRrsId(emailRrsId);
    int rows = sqlMapClient.delete("EMAIL_RRS_INFO.deleteByPrimaryKey", _key);
    return rows;
  }

  /**
   * EMAIL_RRS_INFO insert
   */
  public void insert(EmailRrsInfo record) throws SQLException {
    sqlMapClient.insert("EMAIL_RRS_INFO.insert", record);
  }

  /**
   * EMAIL_RRS_INFO insertSelective
   */
  public void insertSelective(EmailRrsInfo record) throws SQLException {
    sqlMapClient.insert("EMAIL_RRS_INFO.insertSelective", record);
  }

  /**
   * EMAIL_RRS_INFO selectByExample
   */
  @SuppressWarnings("rawtypes")
  public List selectByExample(EmailRrsInfoExample example) throws SQLException {
    List list = sqlMapClient.queryForList("EMAIL_RRS_INFO.selectByExample", example);
    return list;
  }

  /**
   * EMAIL_RRS_INFO selectByPrimaryKey
   */
  public EmailRrsInfo selectByPrimaryKey(Integer emailRrsId) throws SQLException {
    EmailRrsInfo _key = new EmailRrsInfo();
    _key.setEmailRrsId(emailRrsId);
    EmailRrsInfo record =
        (EmailRrsInfo) sqlMapClient.queryForObject("EMAIL_RRS_INFO.selectByPrimaryKey", _key);
    return record;
  }

  /**
   * EMAIL_RRS_INFO updateByPrimaryKeySelective
   */
  public int updateByPrimaryKeySelective(EmailRrsInfo record) throws SQLException {
    int rows = sqlMapClient.update("EMAIL_RRS_INFO.updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * EMAIL_RRS_INFO updateByPrimaryKey
   */
  public int updateByPrimaryKey(EmailRrsInfo record) throws SQLException {
    int rows = sqlMapClient.update("EMAIL_RRS_INFO.updateByPrimaryKey", record);
    return rows;
  }

  /**
   * EMAIL_RRS_INFO
   */
  protected static class UpdateByExampleParms extends EmailRrsInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, EmailRrsInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }
}
