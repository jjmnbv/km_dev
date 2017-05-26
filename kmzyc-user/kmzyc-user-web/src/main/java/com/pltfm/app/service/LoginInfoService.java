package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.UserLevel;

public interface LoginInfoService {
  /**
   * 添加登录信息
   * 
   * @param 登录实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer addLoginInfo(LoginInfo l) throws SQLException;

  /**
   * 按等级id查询是否存在头衔
   * 
   * @param levelId 等级id
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countLevel(Integer levelId) throws Exception;

  /**
   * 删除登录信息
   * 
   * @param 登录信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer deleteLoginInfo(List<String> n_LoginIds) throws SQLException;

  /**
   * 分页查询登录信息
   * 
   * @param 登录信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, LoginInfo l) throws Exception;

  /**
   * 动态修改登录信息
   * 
   * @param 登录信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer udpateLoginInfo(LoginInfo l) throws SQLException;

  /**
   * 跟据个人id查登录信息
   * 
   * @param MdicalExcusieInfo 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public LoginInfo getLoginId(Integer n_LoginId) throws SQLException;

  /**
   * 获取客户等级列表信息
   * 
   * @param userLevel 客户等级vo
   * @return 返回值
   * @throws Exception 异常
   */
  public List<UserLevel> getUserLevellist(int nCustomerTypeId) throws Exception;

  /**
   * 获取客户基本信息
   * 
   * @param userInfoDO 客户数据方位实体
   * @return
   * @throws Exception 异常
   */
  public Page queryPageBasicUserInfo(Page page, UserInfoDO userInfoDO) throws Exception;

  /**
   * 获取客户基本信息
   * 
   * @param userInfoDO 客户数据方位实体
   * @return
   * @throws Exception 异常
   */
  public List<LoginInfo> getLoginInfo(UserInfoDO userInfoDO) throws Exception;

  public List getLoginAll() throws Exception;

  public List getAllMobileUser() throws Exception;

  /**
   * 生成会员卡号
   * 
   * @return
   * @throws ServiceException
   */
  String generateUserCardNum() throws ServiceException;

  /**
   * 根据登录账号获取用户卡号
   * 
   * @param accountLogin
   * @return
   * @throws ServiceException
   */
  String queryUserCardNumByLoginAccount(String accountLogin) throws ServiceException;


}
