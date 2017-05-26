package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.vo.UserBaseInfo;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AccountInfoExample;

/**
 * 账户信息处理接口
 * 
 * @author cjm
 * @since 2013-7-10
 */
public interface AccountInfoDAO {


  /**
   * 按条件查询账户信息总数量
   * 
   * @param vo 账户信息实体
   * @return 返回值
   */
  int selectCountByVo(AccountInfo vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectPageByVo(AccountInfo vo) throws SQLException;

  /**
   * 添加账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(AccountInfo record) throws SQLException;

  /**
   * 修改账户信息 abatorgenerated_updateByLoginIdKeySelective
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(AccountInfo record) throws SQLException;

  /**
   * 修改账户信息 abatorgenerated_updateByLoginIdKeySelective
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByLoginId(AccountInfo record) throws SQLException;

  /**
   * 动态修改账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(AccountInfo record) throws SQLException;

  /**
   * 按账户信息条件查询
   * 
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(AccountInfoExample example) throws SQLException;

  /**
   * 根据账户主键查询单条账户基本信息
   * 
   * @param nCommercialTenantId 账户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  AccountInfo selectByPrimaryKey(Integer nAccountId) throws SQLException;

  /**
   * 根据登录主键查询单条账户基本信息
   * 
   * @param nCommercialTenantId 账户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  AccountInfo selectByPrimaryLoginInfo(Integer n_LoginId) throws SQLException;

  AccountInfo selectByPrimaryLoginInfo(String accountLogin, String mobileNo) throws SQLException;

  /**
   * 根据登录id查询账户信息和公司名
   * 
   * @param n_LoginId
   * @return
   * @throws SQLException
   */
  AccountInfo selectAccountAndCnameByLoginId(Integer n_LoginId) throws SQLException;

  /**
   * 按账户信息条件进行删除
   * 
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(AccountInfoExample example) throws SQLException;

  /**
   * 根据账户主键删除商户基本信息
   * 
   * @param nCommercialTenantId 账户主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer nAccountId) throws SQLException;

  /**
   * 按账户信息条件查询总条数
   * 
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(AccountInfoExample example) throws SQLException;

  /**
   * 动态按账户信息条件进行修改
   * 
   * @param record 账户信息实体
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(AccountInfo record, AccountInfoExample example) throws SQLException;

  /**
   * 按账户信息条件进行修改
   * 
   * @param record 账户信息实体
   * @param example 账户信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(AccountInfo record, AccountInfoExample example) throws SQLException;

  /**
   * 通过账户相关信息查询账户详细信息
   * 
   * @param vo 账户实体
   * @return 账户详细信息
   * @throws Exception 异常
   */
  public AccountInfo showAccountInfo(AccountInfo vo) throws Exception;

  /**
   * 修改用户余额--订单余额支付专用 必须传入修改前的金额值
   * 
   * @param vo
   * @return
   * @throws SQLException
   */
  public int updateAccountBalance(Map<String, String> params) throws SQLException;

  /**
   * 根据条件查询用户支付密码
   * @param userInfoVo
   * @return
   * @throws SQLException
   */
  public UserBaseInfo queryUserPayPassword(UserBaseInfo userInfoVo) throws  SQLException;
  
  /**
   * 更新或增加账户信息
   * @param record
   * @return
   * @throws SQLException
   */
  public Integer insertOrUpdateAccountInfo (AccountInfo record) throws  SQLException;
}
