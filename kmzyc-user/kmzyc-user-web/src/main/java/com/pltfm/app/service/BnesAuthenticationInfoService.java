package com.pltfm.app.service;

import java.sql.SQLException;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAuthenticationInfo;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;

/**
 * 实名认证信息业务逻辑接口
 * 
 * @since 2013-07-26
 */
public interface BnesAuthenticationInfoService {

  /**
   * 根据登录主键查询单条商户信息
   * 
   * @param nLoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  public CommercialTenantBasicInfo selectByPrimaryCommercial(Integer nLoginId) throws Exception;

  /**
   * 跟据登录主键查询个人信息
   * 
   * @param Rank 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo selectByPrimaryPersonal(Integer nLoginId) throws Exception;


  /**
   * 根据账户主键查询单条账户信息
   * 
   * @param n_LoginId 账户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  public AccountInfo selectByAccountInfo(Integer n_AccountId) throws Exception;

  /**
   * 根据实名认证主键查询单条实名认证信息
   * 
   * @param authenticationId 实名认证信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  public BnesAuthenticationInfo selectByBnesAuthenticationInfo(Integer authenticationId)
      throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 实名认证信息实体
   * @return 返回值
   * @throws Exception
   */
  public Page searchPageByVo(Page pageParam, BnesAuthenticationInfo vo) throws Exception;

  /**
   * 审核实名认证信息
   */
  public Integer updateBnesAuthenticationInfo(BnesAuthenticationInfo bnesAuthenticationInfo,
      CommercialTenantBasicInfo commercialTenantBasicInfo, PersonalBasicInfo personalBasicInfo)
      throws Exception;
}
