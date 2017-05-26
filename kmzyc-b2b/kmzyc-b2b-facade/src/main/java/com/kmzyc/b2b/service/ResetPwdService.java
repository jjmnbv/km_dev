package com.kmzyc.b2b.service;

import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.vo.UserBaseInfo;

/**
 * 重置密码
 * 
 * @author xiongliguang
 * 
 */
public interface ResetPwdService {

  /**
   * 忘记密码重置密码
   * 
   * @param changePasswordInfo
   */
  public boolean resetPassword(UserBaseInfo userInfo) throws Exception;

  /**
   * 根据登录ID获取用户信息
   * 
   * @param loginId
   * @return
   * @throws Exception
   */
  public User getUserByLoginId(long loginId) throws Exception;
}
