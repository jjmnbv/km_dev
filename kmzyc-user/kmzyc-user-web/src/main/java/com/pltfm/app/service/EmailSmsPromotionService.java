package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.EmailSmsPromotion;



public interface EmailSmsPromotionService {


  void addPromotion(EmailSmsPromotion emailSmsPromotion) throws Exception;

  void deleteById(Integer promotionId) throws SQLException;

  void delete(List<Integer> promotionIds) throws Exception;

  EmailSmsPromotion queryemailSmsPromotion(EmailSmsPromotion emailSmsPromotion) throws SQLException;

  void updateEmailSmsPromotion(EmailSmsPromotion emailSmsPromotion, String loginIds)
      throws Exception;

  void updateStatus(EmailSmsPromotion emailSmsPromotion) throws Exception;

  Integer selectListPromotionCount(EmailSmsPromotion emailSmsPromotion) throws Exception;

  List selectListPromotion(EmailSmsPromotion emailSmsPromotion) throws Exception;

  List<EmailSmsPromotion> getListEmailSmsIsTime(EmailSmsPromotion emailSmsPromotion)
      throws Exception;


}
