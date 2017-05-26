package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.EmailSmsLogsDAO;
import com.pltfm.app.service.EmailSmsLogsService;
import com.pltfm.app.vobject.EmailSmsLogs;


@Component(value = "emailSmsLogsService")
public class EmailSmsLogsServiceImpl implements EmailSmsLogsService {
  @Resource(name = "emailSmsLogsDao")
  private EmailSmsLogsDAO emailSmsLogsDao;

  public EmailSmsLogsDAO getEmailSmsLogsDao() {
    return emailSmsLogsDao;
  }

  public void setEmailSmsLogsDao(EmailSmsLogsDAO emailSmsLogsDao) {
    this.emailSmsLogsDao = emailSmsLogsDao;
  }

  @Override
  public Page getEmailSmsLogs(Page page, EmailSmsLogs emailSmsLog) throws Exception {
    if (page == null) {
      page = new Page();
    }
    if (emailSmsLog == null) {
      emailSmsLog = new EmailSmsLogs();
    }
    // 获取推广邮件总数
    int totalNum = emailSmsLogsDao.getCountEmailSmsLogs(emailSmsLog);
    page.setRecordCount(totalNum);
    // 设置查询开始结束索引
    emailSmsLog.setSkip(page.getStartIndex());
    emailSmsLog.setMax(page.getStartIndex() + page.getPageSize());
    page.setDataList(emailSmsLogsDao.getListEmailSmsLogs(emailSmsLog));
    return page;
  }

  @Override
  public void deleteSmsLog(Integer promotionId) throws Exception {
    // TODO Auto-generated method stub
    emailSmsLogsDao.delete(promotionId);
  }

  @Override
  public List getEmailSmsLogs(EmailSmsLogs smsLogs) throws Exception {
    // TODO Auto-generated method stub
    return emailSmsLogsDao.getEmailSmsLogs(smsLogs);
  }

  @Override
  public void insert(EmailSmsLogs smsLogs) throws Exception {
    emailSmsLogsDao.insert(smsLogs);
  }

  @Override
  public void updateSendStatus(EmailSmsLogs emailLogs2) throws Exception {
    // TODO Auto-generated method stub
    emailSmsLogsDao.updateSendStatus(emailLogs2);
  }

}
