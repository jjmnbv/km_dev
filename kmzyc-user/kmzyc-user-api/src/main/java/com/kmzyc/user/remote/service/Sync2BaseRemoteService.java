package com.kmzyc.user.remote.service;

public interface Sync2BaseRemoteService {

  /**
   * 生成总部会员卡号
   * 
   * @return 会员卡号
   * @throws ServiceException
   */
//  String queryUserCardNumByLoginAccount(String accountLogin) throws ServiceException;

  /**
   * 同步支付完成的订单信息到总部系统
   * 
   * @param sys
   * @param type
   * @param value
   * @return
   * @throws ServiceException
   */
//  String pushOrderBaseDockData(String value) throws ServiceException;


  /**
   * 同步审核通过的退换货订单到总部系统
   * 
   * @param value
   * @return
   * @throws ServiceException
   */
//  String pushAlterOrderBaseDockData(String value) throws ServiceException;


  /**
   * 同步用户信息到总部系统
   * 
   * @param lstAccountLogin 需要同步的账户名
   * @return 1表示同步成功，否则表示失败
   * @throws ServiceException
   */
//  int pushUserInfoBaseDockData(List<String> lstAccountLogin) throws ServiceException;

}
