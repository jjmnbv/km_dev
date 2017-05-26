package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.Map;

import com.kmzyc.b2b.model.User;
import com.kmzyc.framework.exception.ServiceException;

public interface RegistService {
  /*
   * 注册用户
   * 
   * @param entity
   * 
   * @return
   * 
   * @throws SQLException
   */
  public Map<String, String> regist(User user) throws Exception;



  public Map<String, String> regist(boolean isMobile, User user) throws Exception;

  /**
   * 注册接口
   * 
   * @param user
   * @param invitationId
   * @param invitedOrganizatton
   * @return
   * @throws Exception
   */
  public Map<String, String> registReward(boolean isMobile, User user, String invitationId,
      String invitedOrganizatton, int Device, int Platfrom) throws Exception;

  /**
   * 验证邮箱是否已经被注册
   * 
   * @param email
   * @return
   * @throws Exception
   */
  public Boolean verilyEmail(String email) throws SQLException;

  /**
   * 验证用户名是否存在
   * 
   * @param name
   * @return
   * @throws Exception
   */
  public int verilyName(String name) throws Exception;


  /**
   * 查询临时用户
   * 
   * @param user
   * @return
   * @throws SQLException
   */
  public User queryTempUser(User user) throws SQLException;

  /**
   * 更新临时用户
   * 
   * @param user
   * @return
   * @throws SQLException
   */
  public boolean updateTempUserInfo(User user) throws Exception;

  /**
   * 用户类型
   * 
   * @param email
   * @return
   * @throws SQLException
   */
  public long queryCustomerType(User user) throws SQLException;

  /**
   * 验证用户是否存在：注册、修改用户名、手机号、邮箱
   * 
   * @param user
   * @return
   * @throws SQLException
   */
  public int checkUserExists(User user) throws ServiceException;

  /**
   * 验证用户是否存在 1存在 0 不存在 -1存在申请记录
   * 
   * @param user
   * @return
   * @throws SQLException
   */
  public int checkUserExistsForVshop(User user) throws ServiceException;

  /**
   * 时代用户绑定普通用户后进行发送优惠券以及积分的方法
   */
  public Map<String, String> sdBindMember(User user) throws Exception;

  /* PC端手机注册 */

  public Map<String, String> registPCPhone(User userR, int device, int platfrom) throws Exception;


  /* PC端浮动框手机注册 */
  public Map<String, String> registDivPhone(User userRs) throws Exception;

  /**
   * 注册后事件
   * 
   * @param userType
   * @param loginId
   * @param loginName
   * @param mobile
   * @param email
   * @throws ServiceException
   */
  public void doAfterRegist(int userType, Long loginId, String loginName, String mobile,
      String email) throws ServiceException;
}
