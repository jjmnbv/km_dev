package com.kmzyc.b2b.service.member;

import java.sql.SQLException;

import com.kmzyc.b2b.model.LoginInfo;
import com.kmzyc.b2b.model.PersonalityInfo;
import com.kmzyc.b2b.model.UserInfo;

public interface UserInfoService {
  /**
   * 根据登录id查询个人信息
   * 
   * @param loginId
   * @return
   * @throws Exception
   */
  public UserInfo queryUserInfoById(int loginId) throws Exception;

  /**
   * 根据登录id查询当前绑定邮箱手机是否已验证
   * 
   * @param loginId
   * @return
   * @throws Exception
   */
  /* public int queryEmailStatus(int loginId) throws Exception; */
  public LoginInfo queryStatus(int loginId) throws Exception;

  /**
   * 修改个人信息
   * 
   * @param userInfo
   * @throws Exception
   */
  public void updateUserInfo(UserInfo userInfo) throws Exception;

  /**
   * 查询用户的可用积分
   * 
   * @param userId
   * @return
   * @throws Exception
   */
  public PersonalityInfo queryPersonalityByUserId(Long userId) throws Exception;

  /**
   * 更新用户的头像
   * 
   * @param userId
   * @param imgFileName
   * @throws Exception
   */
  public void updateUserImg(Long userId, String imgFileName) throws SQLException;

  /**
   * 根据用户ID 查询用户的头像
   */
  public com.kmzyc.b2b.model.PersonalityInfo selcetImagById(Long userId) throws SQLException;
}
