package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.LoginInfo;
import com.kmzyc.b2b.model.PersonalityInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.model.UserInfo;

public interface UserInfoDao extends Dao {
  /**
   * 根据登录id查询对应的个人信息
   * 
   * @param id
   * @return
   * @throws SQLException
   */
  public UserInfo queryUserInfoById(int id) throws SQLException;

  /**
   * 根据登录id查询当前绑定邮箱和手机的验证状态
   * 
   * @param id
   * @return
   * @throws SQLException
   */
  /* public int queryStatus(int id) throws SQLException; */
  public LoginInfo queryStatus(int id) throws SQLException;

  /**
   * 根据ID，查询对应的昵称和等级
   */
  public User queryLeveAndNickName(int loginId) throws SQLException;

  /**
   * 根据ID查询用户的头像
   */
  public PersonalityInfo queryPersonalImageByUserId(Long userId) throws Exception;
}
