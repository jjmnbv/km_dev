package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.EmailSmsLogsDAO;
import com.pltfm.app.dao.EmailSmsPromotionDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.service.EmailSmsPromotionService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.EmailSmsLogs;
import com.pltfm.app.vobject.EmailSmsPromotion;
import com.pltfm.app.vobject.LoginInfo;

@Component(value = "emailSmsPromotionService")

public class EmailSmsPromotionServiceImpl implements EmailSmsPromotionService {
  /**
   * 邮件推广DAO接口
   */
  @Resource(name = "emailSmsPromotionDAO")
  private EmailSmsPromotionDAO emailSmsPromotionDAO;
  @Resource(name = "emailSmsLogsDao")
  private EmailSmsLogsDAO emailSmsLogsDao;
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  public EmailSmsPromotionDAO getEmailSmsPromotionDAO() {
    return emailSmsPromotionDAO;
  }

  public void setEmailSmsPromotionDAO(EmailSmsPromotionDAO emailSmsPromotionDAO) {
    this.emailSmsPromotionDAO = emailSmsPromotionDAO;
  }

  public EmailSmsLogsDAO getEmailSmsLogsDao() {
    return emailSmsLogsDao;
  }

  public void setEmailSmsLogsDao(EmailSmsLogsDAO emailSmsLogsDao) {
    this.emailSmsLogsDao = emailSmsLogsDao;
  }

  public void addPromotion(EmailSmsPromotion emailSmsPromotion) throws Exception {
    emailSmsPromotionDAO.addPromotion(emailSmsPromotion);
    // 指定个人的情况
    if (emailSmsPromotion.getPublicType() == 2) {
      String loginId = emailSmsPromotion.getLoginId();
      if (null != loginId && !loginId.equals("")) {
        String[] args = loginId.split(",");
        for (int i = 0; i < args.length; i++) {
          EmailSmsLogs smsLogs = new EmailSmsLogs();
          LoginInfo loginInfo =
              loginInfoDAO.selectByPrimaryKey(Integer.parseInt(args[i].toString().trim()));
          smsLogs.setEmail(loginInfo.getEmail());
          smsLogs.setMobile(loginInfo.getMobile());
          smsLogs.setLoginId(Integer.parseInt(args[i].toString().trim()));
          smsLogs.setCreatedId(emailSmsPromotion.getCreateId());
          smsLogs.setCreatedDate(new Date());
          smsLogs.setPromotionId(emailSmsPromotion.getPromotionId());
          emailSmsLogsDao.insert(smsLogs);
        }
      }
    }

  }

  // 删除单条
  public void deleteById(Integer promotionId) throws SQLException {
    EmailSmsPromotion emailSmsPromotion = new EmailSmsPromotion();
    emailSmsPromotion.setPromotionId(promotionId);
    emailSmsPromotionDAO.delete(emailSmsPromotion);

  }

  // 删除多条
  public void delete(List<Integer> promotionIds) throws Exception {
    // TODO Auto-generated method stub
    if (ListUtils.isNotEmpty(promotionIds)) {
      for (Integer id : promotionIds) {
        EmailSmsPromotion emailSmsPromotion = new EmailSmsPromotion();
        emailSmsPromotion.setPromotionId(id);
        emailSmsPromotionDAO.delete(emailSmsPromotion);
        emailSmsLogsDao.delete(emailSmsPromotion.getPromotionId());
      }
    }
  }

  @Override
  public EmailSmsPromotion queryemailSmsPromotion(EmailSmsPromotion emailSmsPromotion)
      throws SQLException {
    // TODO Auto-generated method stub
    return emailSmsPromotionDAO.queryemailSmsPromotion(emailSmsPromotion);
  }

  @Override
  public void updateEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion, String loginIds)
      throws Exception {
    // 指定人有更新
    if (null != loginIds && !loginIds.equals("")) {
      EmailSmsLogs smsLogs = new EmailSmsLogs();
      smsLogs.setPromotionId(emailSmsPromotion.getPromotionId());
      List list = emailSmsLogsDao.getEmailSmsLogs(smsLogs);
      if (null != list && list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          smsLogs = (EmailSmsLogs) list.get(i);
          emailSmsLogsDao.deleteByPrimaryKey(smsLogs.getSelogId());
        }
      }
      if (emailSmsPromotion.getPublicType() == 2) {
        String[] args = loginIds.split(",");
        for (int i = 0; i < args.length; i++) {
          LoginInfo loginInfo =
              loginInfoDAO.selectByPrimaryKey(Integer.parseInt(args[i].toString().trim()));
          EmailSmsLogs smsLogs1 = new EmailSmsLogs();
          smsLogs1.setEmail(loginInfo.getEmail());
          smsLogs1.setMobile(loginInfo.getMobile());
          smsLogs1.setLoginId(Integer.parseInt(args[i].toString().trim()));
          smsLogs1.setCreatedId(emailSmsPromotion.getCreateId());
          smsLogs1.setCreatedDate(new Date());
          smsLogs1.setModifyDate(new Date());
          smsLogs1.setModifyId(emailSmsPromotion.getModifyId());
          smsLogs1.setPromotionId(emailSmsPromotion.getPromotionId());
          emailSmsLogsDao.insert(smsLogs1);
        }
      }
    }
    emailSmsPromotionDAO.updatEmailSmsPromotion(emailSmsPromotion);
  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

  @Override
  public void updateStatus(EmailSmsPromotion emailSmsPromotion) throws Exception {
    emailSmsPromotionDAO.updateStatus(emailSmsPromotion);

  }

  @Override
  public List selectListPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException {
    // TODO Auto-generated method stub
    return emailSmsPromotionDAO.selectPageEmailSmsPromotion(emailSmsPromotion);
  }

  public Integer selectListPromotionCount(EmailSmsPromotion emailSmsPromotion) throws Exception {
    return emailSmsPromotionDAO.selectCountEmailSmsPromotion(emailSmsPromotion);
  }

  @Override
  public List<EmailSmsPromotion> getListEmailSmsIsTime(EmailSmsPromotion emailSmsPromotion)
      throws Exception {
    return emailSmsPromotionDAO.getListEmailSmsIsTime(emailSmsPromotion);
  }

}
