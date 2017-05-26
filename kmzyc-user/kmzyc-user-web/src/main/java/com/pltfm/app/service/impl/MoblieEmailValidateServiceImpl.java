package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.MoblieEmailValidateDao;
import com.pltfm.app.service.MoblieEmailValidateService;
import com.pltfm.app.vobject.MoblieEmailValidate;

@Component(value = "moblieEmailValidateService")
public class MoblieEmailValidateServiceImpl implements MoblieEmailValidateService {


  /**
   * 手机邮箱验证Dao
   */
  @Resource(name = "moblieEmailValidateDao")
  private MoblieEmailValidateDao moblieEmailValidateDao;



  public void setMoblieEmailValidateDao(MoblieEmailValidateDao moblieEmailValidateDao) {
    this.moblieEmailValidateDao = moblieEmailValidateDao;
  }

  @Override
  public List selectListMoblieEmail(MoblieEmailValidate moblieEmailValidate) throws SQLException {

    return moblieEmailValidateDao.selectListMoblieEmail(moblieEmailValidate);
  }

  @Override
  public Integer selectListMoblieEmailCount(MoblieEmailValidate moblieEmailValidate)
      throws SQLException {
    // TODO Auto-generated method stub
    return moblieEmailValidateDao.selectListMoblieEmailCount(moblieEmailValidate);
  }

  @Override
  public void updateEmailValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException {

    moblieEmailValidateDao.updateEmailValidate(moblieEmailValidate);
  }

  @Override
  public void mobileValidate(MoblieEmailValidate moblieEmailValidate) throws SQLException {
    moblieEmailValidateDao.mobileValidate(moblieEmailValidate);

  }



}
