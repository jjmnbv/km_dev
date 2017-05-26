package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginRoseRelQuery;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;
import com.pltfm.app.vobject.UserLevel;

/**
 * 商户信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-7-17
 */
public interface CommercialTenantBasicInfoService {
  /**
   * 添加商户信息
   * 
   * @param record 商户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer addCommercialTenantBasicInfo(CommercialTenantBasicInfo commercialTenantBasicInfo,
      LoginInfo loginInfo, PersonalityInfo personalityInfo, AccountInfo accountInfo)
      throws Exception;

  /**
   * 根据登录主键查询单条商户信息
   * 
   * @param loginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  public CommercialTenantBasicInfo selectloginId(Integer loginId) throws Exception;

  /**
   * 修改商户信息
   * 
   * @param record 商户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer updateCommercialTenantBasicInfo(CommercialTenantBasicInfo commercialTenantBasicInfo,
      LoginInfo loginInfo, PersonalityInfo personalityInfo, AccountInfo accountInfo)
      throws Exception;

  /**
   * 根据主键查询单条商户信息
   * 
   * @param n_PersonalityId 商户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  CommercialTenantBasicInfo selectByPrimaryKey(Integer n_CommercialTenantId) throws Exception;

  /**
   * 根据主键查询单条登录信息
   * 
   * @param n_PersonalityId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  LoginInfo selectByn_LoginId(Integer n_LoginId) throws Exception;

  /**
   * 根据登录主键查询单条个性信息
   * 
   * @param nLoginId
   * @return
   * @throws Exception
   */
  PersonalityInfo selectByPersonalityInfo(Integer nLoginId) throws Exception;

  /**
   * 根据主键进行删除单条商户信息
   * 
   * @param n_CommercialTenantIds 商户信息ID集合
   * @return 返回值
   * @throws Exception 异常
   */
  Integer deleteByPrimaryKey(List<Integer> n_CommercialTenantIds) throws Exception;

  /**
   * 根据登录ID查询单个账户信息
   */
  AccountInfo selectByAccountInfo(Integer nLoginId) throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 账户信息实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByVo(Page pageParam, CommercialTenantBasicInfo vo) throws Exception;

  /**
   * 查询账户号
   * 
   * @param accountLogin
   * @return
   * @throws Exception
   */
  Integer checkLoginAccount(String loginAccount) throws Exception;

  /**
   * 查询全部登录信息
   * 
   * @return
   * @throws Exception
   */
  List<LoginInfo> queryLoginInfoList() throws Exception;

  /**
   * 查询商户下的子级类别集合
   */
  List<BnesCustomerTypeQuery> queryByCommCustomer();

  /**
   * 查询商户客户的头衔
   * 
   * @return
   */
  List<Rank> queryByCommRank(Integer customerTypeId) throws Exception;

  /**
   * 查询商户客户的等级
   * 
   * @return
   */
  List<UserLevel> queryByCommUserLevel(Integer customerTypeId) throws Exception;

  CommercialTenantBasicInfo selectByPrimaryLoginInfo(Integer nLoginId) throws SQLException;

  void verifyPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException;

  void notPass(CommercialTenantBasicInfo commercialTenantBasicInfo) throws SQLException;

  LoginRoseRelQuery selectByNcustomerId(Integer nCommercialTenantId) throws SQLException;
}
