package com.pltfm.remote.service.impl;

import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.Sync2BaseRemoteService;

@Component(value = "sync2BaseRemoteService")
public class Sync2BaseRemoteServiceImpl implements Sync2BaseRemoteService {

 /*删除总部会员对接业务 @Resource
  BaseDockService baseDockService;

  @Resource
  private LoginInfoService loginInfoService;
 
  */ /**
   * 个人信息业务逻辑接口
   *//*
  @Resource(name = "personalBasicInfoService")
  private PersonalBasicInfoService personalBasicInfoService;

  @Override
  public String queryUserCardNumByLoginAccount(String accountLogin) throws ServiceException {
    return loginInfoService.queryUserCardNumByLoginAccount(accountLogin);
  }

  @Override
  public String pushOrderBaseDockData(String value) throws ServiceException {
    return baseDockService.pushBaseDockData(
        Integer.parseInt(ConfigurationUtil.getString("orderIbport")),
        BaseDockType.ORDER_PAID_PUSH.getValue(), value);
  }

  @Override
  public String pushAlterOrderBaseDockData(String value) throws ServiceException {
    return baseDockService.pushBaseDockData(
        Integer.parseInt(ConfigurationUtil.getString("orderIbport")),
        BaseDockType.ORDER_ALTER_PUSH.getValue(), value);
  }

  @Override
  public int pushUserInfoBaseDockData(List<String> lstAccountLogin) throws ServiceException {
    int result = -1;
    if (lstAccountLogin != null && lstAccountLogin.size() > 0) {
      result = personalBasicInfoService.syncPersonalBasicInfo2Base(lstAccountLogin);
    }
    return result;
  }*/
}
