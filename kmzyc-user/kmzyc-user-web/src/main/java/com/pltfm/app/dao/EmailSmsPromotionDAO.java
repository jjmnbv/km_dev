package com.pltfm.app.dao;

import com.pltfm.app.vobject.EmailSmsPromotion;

import java.sql.SQLException;
import java.util.List;

public interface EmailSmsPromotionDAO {

  Integer selectCountEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  List selectPageEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  void addPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  void delete(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  EmailSmsPromotion queryemailSmsPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  void updateStatus(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  void updatEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  List<EmailSmsPromotion> getListEmailSmsIsTime(EmailSmsPromotion emailSmsPromotion)
      throws SQLException;



}
