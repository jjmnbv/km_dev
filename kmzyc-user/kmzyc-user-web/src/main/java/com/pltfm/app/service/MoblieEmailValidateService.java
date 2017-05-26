package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.MoblieEmailValidate;

public interface MoblieEmailValidateService {
  Integer selectListMoblieEmailCount(MoblieEmailValidate moblieEmailValidate) throws SQLException;

  List selectListMoblieEmail(MoblieEmailValidate moblieEmailValidate) throws SQLException;

  void updateEmailValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException;

  void mobileValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException;

}
