package com.pltfm.app.dao;

import com.pltfm.app.vobject.EmailSmsLogs;

import java.util.List;

public interface EmailSmsLogsDAO {

  int getCountEmailSmsLogs(EmailSmsLogs emailSmsLog) throws Exception;

  List getListEmailSmsLogs(EmailSmsLogs emailSmsLog) throws Exception;

  void insert(EmailSmsLogs smsLogs) throws Exception;

  List getEmailSmsLogs(EmailSmsLogs smsLogs) throws Exception;

  int deleteByPrimaryKey(Integer selogId) throws Exception;

  void delete(Integer promotionId) throws Exception;

  void updateSendStatus(EmailSmsLogs emailLogs2) throws Exception;



}
