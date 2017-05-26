package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginInfoExample;

/**
 * 登录信息处理接口
 * 
 * @author cjm
 * @since 2013-7-9
 */
@SuppressWarnings("unchecked")
public interface LoginInfoDAO {
  /**
   * 添加登录信息
   * 
   * @param record 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(LoginInfo record) throws SQLException;

  /**
   * 按等级id查询是否存在头衔
   * 
   * @param levelId 等级id
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countLevel(Integer levelId) throws SQLException;

  /**
   * 修改登录信息
   * 
   * @param record 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(LoginInfo record) throws SQLException;

  /**
   * 动态修改登录信息
   * 
   * @param record 登录信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(LoginInfo record) throws SQLException;

  /**
   * 按登录信息条件查询
   * 
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(LoginInfoExample example) throws SQLException;

  /**
   * 按【客户类型信息查询
   * 
   * @param example 客户类型信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectCustomerTypeId(Integer customerTypeId) throws SQLException;

  /**
   * 根据登录主键查询单条登录信息
   * 
   * @param nCommercialTenantId 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  LoginInfo selectByPrimaryKey(Integer n_LoginId) throws SQLException;

  /**
   * 按登录信息条件进行删除
   * 
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(LoginInfoExample example) throws SQLException;

  /**
   * 根据登录主键删除商户基本信息
   * 
   * @param nCommercialTenantId 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer n_LoginId) throws SQLException;

  /**
   * 按登录信息条件查询总条数
   * 
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(LoginInfoExample example) throws SQLException;

  /**
   * 动态按登录信息条件进行修改
   * 
   * @param record 登录信息实体
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(LoginInfo record, LoginInfoExample example) throws SQLException;

  /**
   * 按登录信息条件进行修改
   * 
   * @param record 登录信息实体
   * @param example 登录信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(LoginInfo record, LoginInfoExample example) throws SQLException;

  /**
   * 按条件查询登录信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public int selectCountByVo(LoginInfo record) throws SQLException;

  /**
   * 根据vo条件查询登录信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(LoginInfo record) throws SQLException;

  /**
   * 取得登录名 姓名
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List getLoginAll() throws SQLException;

  /**
   * 通过客户基本信息访问类查询客户信息
   * 
   * @param userInfoDO 客户基本信息数据访问实体
   * @return
   * @throws SQLException 异常
   */
  public List queryBasicUserInfo(UserInfoDO userInfoDO) throws SQLException;

  /**
   * 通过客户基本信息访问类查询客户信息总数
   * 
   * @param userInfoDO 客户基本信息数据访问实体
   * @return
   * @throws SQLException 异常
   */
  public Integer byCountBasicUserInfo(UserInfoDO userInfoDO) throws SQLException;

  /**
   * 通过登录id 查询客户基本信息
   * 
   * @param logInId 登录主键
   * @return
   * @throws SQLException 异常信息
   */
  public UserInfoDO selectByLoginId(UserInfoDO userInfoDO) throws SQLException;

  /**
   * 通过登录UserInfoDO查询客户基本信息
   * 
   * @param UserInfoDO
   * @return List<UserInfoDO>
   * @throws SQLException 异常信息
   */

  public List<UserInfoDO> selectByUserInfoDO(UserInfoDO userInfoDO) throws SQLException;

  public List<UserInfoDO> selectUserInfoDOByLoginId(UserInfoDO userInfoDO) throws SQLException;

  public Integer countCheckLoginEmail(UserInfoDO userInfoDO) throws SQLException;

  /**
   * 通过客户等级主键查询客户集合信息
   * 
   * @param levelId 客户等级主键
   * @return 客户信息集合
   * @throws SQLException 异常
   */
  public List selectByUserLevelId(UserInfoDO userInfoDO) throws SQLException;

  /**
   * 获取客户基本信息
   * 
   * @param userInfoDO 客户数据方位实体
   * @return
   * @throws Exception 异常
   */
  public List<LoginInfo> getLoginInfo(UserInfoDO userInfoDO) throws Exception;

  List getAllMobileUser() throws Exception;

  /**
   * 根据登录名查询用户信息
   * 
   * @return
   * @throws Exception
   */
  public LoginInfo queryUserInfoByLoginAccount(String loginAccount) throws Exception;

  /**
   * 根据用户loginId查询用户信息
   * 
   * @param LoginId
   * @return
   * @throws SQLException
   */
  public UserInfoDO queryUserByLoginId(Long LoginId) throws SQLException;

  /**
   * 生成商城用户卡号
   * 
   * @return
   * @throws SQLException
   */
  String generateUserCardNum() throws SQLException;

  /**
   * 根据登录账号获取用户卡号
   * 
   * @param accountLogin
   * @return
   */
  String queryUserCardNumByLoginAccount(String accountLogin) throws SQLException;

  /**
   * 根据卡号查询用户相关信息的主键信息
   */
  Map<String, BigDecimal> queryUserInfoPrimaryKeyByCardNum(String cardNum) throws SQLException;
  
  Integer countCheckLoginMobile(UserInfoDO userInfoDO) throws SQLException ;

  /**
   * 根据条件查询用户登录密码
   * @param userInfoVo
   * @return
   * @throws SQLException
   */
  public UserBaseInfo queryUserLoginPassword(UserBaseInfo userInfoVo) throws  SQLException;
  
  public Integer insertOrUpdate(LoginInfo info) throws  SQLException;

}
