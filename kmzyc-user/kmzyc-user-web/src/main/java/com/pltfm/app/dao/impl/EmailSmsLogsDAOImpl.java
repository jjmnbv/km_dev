package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.EmailSmsLogsDAO;
import com.pltfm.app.vobject.EmailSmsLogs;


@Component(value = "emailSmsLogsDao")
public class EmailSmsLogsDAOImpl implements EmailSmsLogsDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public int getCountEmailSmsLogs(EmailSmsLogs emailSmsLog) throws Exception {
    // TODO Auto-generated method stub
    Integer count =
        (Integer) sqlMapClient.queryForObject("EMAIL_SMS_LOGS.getCountEmailSmsLogs", emailSmsLog);
    return count.intValue();
  }

  @Override
  public List getListEmailSmsLogs(EmailSmsLogs emailSmsLog) throws Exception {
    // TODO Auto-generated method stub
    List pageList = null;
    try {
      pageList = sqlMapClient.queryForList("EMAIL_SMS_LOGS.getListEmailSmsLogs", emailSmsLog);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return pageList;

  }

  public void insert(EmailSmsLogs smsLogs) throws Exception {

    sqlMapClient.insert("EMAIL_SMS_LOGS.insertEmailSmsLogs", smsLogs);
  }

  @Override
  public List getEmailSmsLogs(EmailSmsLogs smsLogs) throws Exception {
    // TODO Auto-generated method stub
    List list = sqlMapClient.queryForList("EMAIL_SMS_LOGS.getEmailSmsLogs", smsLogs);
    return list;
  }

  @Override
  public int deleteByPrimaryKey(Integer selogId) throws Exception {
    // TODO Auto-generated method stub


    EmailSmsLogs key = new EmailSmsLogs();
    key.setSelogId(selogId);
    int rows = sqlMapClient.delete("EMAIL_SMS_LOGS.deleteEmailSmsLogs", key);


    return rows;
  }

  @Override
  public void delete(Integer promotionId) throws SQLException {
    // TODO Auto-generated method stub
    EmailSmsLogs key = new EmailSmsLogs();
    key.setPromotionId(promotionId);
    sqlMapClient.delete("EMAIL_SMS_LOGS.deleteEmailSms", key);
  }

  @Override
  public void updateSendStatus(EmailSmsLogs emailLogs2) throws SQLException {

    sqlMapClient.update("EMAIL_SMS_LOGS.updateSendStatus", emailLogs2);

  }



}
