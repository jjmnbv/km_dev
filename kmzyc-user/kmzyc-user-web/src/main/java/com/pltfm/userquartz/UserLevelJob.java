package com.pltfm.userquartz;

import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.vobject.LoginInfo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

import javax.annotation.Resource;

public class UserLevelJob implements Job {
  @Resource
  private LoginInfoDAO loginInfoDAO;
  @Resource
  private LoginInfoService loginInfoService;


  public LoginInfoService getLoginInfoService() {
    return loginInfoService;
  }

  public void setLoginInfoService(LoginInfoService loginInfoService) {
    this.loginInfoService = loginInfoService;
  }

  @SuppressWarnings("unused")
  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
//    UserInfoDO userInfoDO = new UserInfoDO();
//    userInfoDO.setStatus(1);
//
//    try {
//      List<LoginInfo> listUserInfoDO = loginInfoService.getLoginInfo(userInfoDO);
//
//    } catch (Exception e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }

  }

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }


  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }


}
