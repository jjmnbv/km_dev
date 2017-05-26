package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.EmailSmsPromotionDAO;
import com.pltfm.app.vobject.EmailSmsPromotion;


@Component(value = "emailSmsPromotionDAO")
public class EmailSmsPromotionDAOImpl implements EmailSmsPromotionDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  @Override
  public Integer selectCountEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion)
      throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("EMAIL_SMS_PROMOTION.getEmailSmsPromotionCount", emailSmsPromotion);
    return count;
  }

  @Override
  public List selectPageEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException {
    List pageList =
        sqlMapClient.queryForList("EMAIL_SMS_PROMOTION.searchPageSmsPromotion", emailSmsPromotion);
    return pageList;
  }

  @Override
  public void addPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException {
    sqlMapClient.insert("EMAIL_SMS_PROMOTION.insertPromotion", emailSmsPromotion);
  }

  @Override
  public void delete(EmailSmsPromotion emailSmsPromotion) throws SQLException {
    // TODO Auto-generated method stub
    sqlMapClient.delete("EMAIL_SMS_PROMOTION.deletePromotion", emailSmsPromotion);
  }

  @Override
  public EmailSmsPromotion queryemailSmsPromotion(EmailSmsPromotion emailSmsPromotion)
      throws SQLException {
    // TODO Auto-generated method stub
    return (EmailSmsPromotion) sqlMapClient
        .queryForObject("EMAIL_SMS_PROMOTION.getEmailSmsPromotion", emailSmsPromotion);
  }

  @Override
  public void updateStatus(EmailSmsPromotion emailSmsPromotion) throws SQLException {

    sqlMapClient.update("EMAIL_SMS_PROMOTION.updateStatus", emailSmsPromotion);
  }

  @Override
  public void updatEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException {
    // TODO Auto-generated method stub
    sqlMapClient.update("EMAIL_SMS_PROMOTION.updatEmailSmsPromotion", emailSmsPromotion);
  }

  @Override
  public List<EmailSmsPromotion> getListEmailSmsIsTime(EmailSmsPromotion emailSmsPromotion)
      throws SQLException {
    List<EmailSmsPromotion> pageList =
        sqlMapClient.queryForList("EMAIL_SMS_PROMOTION.getListEmailSmsIsTime", emailSmsPromotion);
    return pageList;
  }



}
