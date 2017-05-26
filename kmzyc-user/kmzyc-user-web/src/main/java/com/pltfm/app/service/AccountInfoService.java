package com.pltfm.app.service;


import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.LoginInfo;

/**
 * 账户信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-7-10
 */
public interface AccountInfoService {
  /**
   * 根据登录id查询账户信息和公司名
   * 
   * @param n_LoginId
   * @return
   * @throws SQLException
   */
  AccountInfo selectAccountAndCnameByLoginId(Integer n_LoginId) throws Exception;

  /**
   * 添加账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer addAccountInfo(AccountInfo accountInfo) throws Exception;

  /**
   * 修改账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer updateAccountInfo(AccountInfo accountInfo) throws Exception;

  /**
   * 根据主键查询单条账户信息
   * 
   * @param n_PersonalityId 账户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  AccountInfo selectByPrimaryKey(Integer nAccountId) throws Exception;

  /**
   * 根据主键进行删除单条账户信息
   * 
   * @param n_PersonalityId 账户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  Integer deleteByPrimaryKey(List<Integer> nAccountIds) throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 账户信息实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByVo(Page pageParam, AccountInfo vo) throws Exception;

  /**
   * 查询账户号
   * 
   * @param accountLogin
   * @return
   * @throws Exception
   */
  Integer checkAccountLogin(String accountLogin) throws Exception;

  /**
   * 查询全部登录信息
   * 
   * @return
   * @throws Exception
   */
  List<LoginInfo> queryLoginInfoList() throws Exception;

  /**
   * 通过账户相关信息查询账户详细信息
   * 
   * @param vo 账户实体
   * @return 账户详细信息
   * @throws Exception 异常
   */
  public AccountInfo showAccountInfo(AccountInfo vo) throws Exception;

  /**
   * 根据登录主键查询单条账户基本信息
   * 
   * @param loginId 账号主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public AccountInfo selectByPrimaryLoginInfo(Integer loginId) throws SQLException;

  public AccountInfo selectByPrimaryLoginInfo(String accountLogin, String mobileNo)
      throws SQLException;

  public int updateByLoginId(AccountInfo vo) throws Exception;
}
