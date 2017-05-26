package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.Map;

import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.vo.ChangePasswordInfo;
import com.kmzyc.b2b.vo.ChangePayPasswordInfo;
import com.kmzyc.b2b.vo.SecurityCentreInfo;
import com.kmzyc.b2b.vo.VerifyMobileInfo;
import com.kmzyc.framework.exception.ServiceException;

public interface SecurityCentreService {

  /**
   * 修改密码
   * 
   * @param user
   * @param changePasswordInfo
   * @return
   * @throws Exception
   */
  boolean changePassword(User user, ChangePasswordInfo changePasswordInfo) throws Exception;

  /**
   * 验证密码
   *
   * @param password
   * @param user
   * @return
   * @throws Exception
   */
  boolean verifyPassword(String password, User user) throws Exception;

  boolean modifyPayPassword(ChangePayPasswordInfo changePayPasswordInfo, User user);

  boolean appModifyPayPassword(ChangePayPasswordInfo changePayPasswordInfo, User user);

  boolean verifyMobile(VerifyMobileInfo verifyMobileInfo, User user,
      String sessionmobileVerifyCode, String mobilePhone) throws Exception;

  SecurityCentreInfo querySecurityCentreInfo(User user) throws ServiceException;

  AccountInfo getAccountInfo(User user);

  boolean saveAccountInfoPayRange(String payRange, User user);


  /**
   * 根据用户id查询
   * 
   * @return
   */
  public AccountInfo accountByUserId(long loginid) throws Exception;

  /**
   * 判断输入的绑定手机号码是否已被使用 add by songmiao 2013-11-11
   * 
   * @throws Exception
   */
  public boolean mobileNumberIsUsed(String mobileNumber) throws Exception;

  /**
   * 根据id获取用户信息
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  public User getUserByLoginId(long loginId);

  /**
   * 登录密码验证身份
   * 
   * @param password
   * @param loginId
   * @return
   */
  public boolean verifyUserPassword(String password, Long loginId) throws Exception;

  /**
   * 登录密码验证身份APP
   * 
   * @param password
   * @param loginId
   * @return
   */
  public boolean appVerifyUserPassword(String password, Long loginId) throws Exception;


  /**
   * 判断手机是否注册
   * 
   * @param mobile
   * @return
   * @throws Exception
   */
  public boolean mobileIsUsed(String mobile) throws Exception;

  /**
   * 用户信息来源更新
   */
  public int updateSource(Map<String, Object> map) throws Exception;

  /**
   * 查询用户安全信息
   * 
   * @param loginId
   * @return
   * @throws Exception
   */
  public Map<String, String> queryUserSecurityInfo(Long loginId) throws Exception;
}
