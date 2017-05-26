package com.kmzyc.b2b.dao;

import java.sql.SQLException;

import com.kmzyc.b2b.model.MobileCodeInf;
import com.kmzyc.framework.exception.DaoException;

public interface MobileCodeInfDao {
  MobileCodeInf findByMobileAndNLoginId(MobileCodeInf mobileCodeInf) throws SQLException,
      DaoException;
}
