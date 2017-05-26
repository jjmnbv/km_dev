package com.pltfm.app.dao;

import com.pltfm.app.vobject.MoblieEmailValidate;

import java.sql.SQLException;
import java.util.List;

public interface MoblieEmailValidateDao {

  Integer selectListMoblieEmailCount(MoblieEmailValidate moblieEmailValidate) throws SQLException;

  List selectListMoblieEmail(MoblieEmailValidate moblieEmailValidate) throws SQLException;

  void updateEmailValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException;

  void mobileValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException;

}
