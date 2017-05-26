package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.EmailSmsLogs;

public interface EmailSmsLogsService {

  Page getEmailSmsLogs(Page page, EmailSmsLogs emailSmsLog) throws Exception;

  void deleteSmsLog(Integer promotionId) throws Exception;


  List getEmailSmsLogs(EmailSmsLogs smsLogs) throws Exception;

  void insert(EmailSmsLogs smsLogs) throws Exception;

  void updateSendStatus(EmailSmsLogs emailLogs2) throws Exception;



}
