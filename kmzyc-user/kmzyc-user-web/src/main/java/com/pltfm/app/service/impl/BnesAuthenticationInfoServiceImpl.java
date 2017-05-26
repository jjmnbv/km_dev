package com.pltfm.app.service.impl;


import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.BnesAuthenticationInfoDAO;
import com.pltfm.app.dao.CommercialTenantBasicInfoDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.service.BnesAuthenticationInfoService;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAuthenticationInfo;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;

/**
 * 实名认证信息业务逻辑类
 * 
 * @since 2013-07-26
 */
@Component(value = "bnesAuthenticationInfoService")
public class BnesAuthenticationInfoServiceImpl implements BnesAuthenticationInfoService {
  /**
   * 实名认证DAO接口
   */
  @Resource(name = "bnesAuthenticationInfoDAO")
  private BnesAuthenticationInfoDAO bnesAuthenticationInfoDAO;

  /**
   * 商户信息DAO接口
   */
  @Resource(name = "commercialTenantBasicInfoDAO")
  private CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO;

  /**
   * 个人信息DAO接口
   */
  @Resource(name = "personalBasicInfoDAO")
  private PersonalBasicInfoDAO personalBasicInfoDAO;
  /**
   * 账户信息DAO接口
   */
  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;


  /**
   * 分页查询账户信息
   * 
   * @param pageParam 分页实体
   * @param vo 实名认证实体
   * @return
   * @throws Exception 异常
   */
  @Override
  public Page searchPageByVo(Page pageParam, BnesAuthenticationInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new BnesAuthenticationInfo();
    }
    // 获取客户积分规则总数
    int totalNum = bnesAuthenticationInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(bnesAuthenticationInfoDAO.selectPageByVo(vo));
    return pageParam;
  }

  /**
   * 根据账户主键查询单条账户信息
   * 
   * @param n_LoginId 账户信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public AccountInfo selectByAccountInfo(Integer nAccountId) throws Exception {
    return accountInfoDAO.selectByPrimaryKey(nAccountId);
  }

  /**
   * 根据实名认证主键查询单条实名认证信息
   * 
   * @param authenticationId 实名认证信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public BnesAuthenticationInfo selectByBnesAuthenticationInfo(Integer authenticationId)
      throws Exception {
    return bnesAuthenticationInfoDAO.selectByPrimaryKey(authenticationId);
  }

  /**
   * 根据登录主键查询单条商户信息
   * 
   * @param nLoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public CommercialTenantBasicInfo selectByPrimaryCommercial(Integer nLoginId) throws Exception {
    return commercialTenantBasicInfoDAO.selectByPrimaryLoginInfo(nLoginId);
  }

  /**
   * 跟据登录主键查询个人信息
   * 
   * @param Rank 登录主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public PersonalBasicInfo selectByPrimaryPersonal(Integer nLoginId) throws Exception {
    return personalBasicInfoDAO.getLogin(nLoginId);
  }

  /**
   * 审核实名认证信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer updateBnesAuthenticationInfo(BnesAuthenticationInfo bnesAuthenticationInfo,
      CommercialTenantBasicInfo commercialTenantBasicInfo, PersonalBasicInfo personalBasicInfo)
      throws Exception {


    if (personalBasicInfo.getN_PersonalId() == null) {
      commercialTenantBasicInfo.setAuthenticationStatus(1);
      commercialTenantBasicInfoDAO.updateByPrimaryKeySelective(commercialTenantBasicInfo);
    } else {
      personalBasicInfo.setAuthenticationStatus(1);
      personalBasicInfoDAO.udpatePersonalBasicInfo(personalBasicInfo);
    }
    return bnesAuthenticationInfoDAO.updateByPrimaryKeySelective(bnesAuthenticationInfo);
  }



  public BnesAuthenticationInfoDAO getBnesAuthenticationInfoDAO() {
    return bnesAuthenticationInfoDAO;
  }

  public void setBnesAuthenticationInfoDAO(BnesAuthenticationInfoDAO bnesAuthenticationInfoDAO) {
    this.bnesAuthenticationInfoDAO = bnesAuthenticationInfoDAO;
  }

  public CommercialTenantBasicInfoDAO getCommercialTenantBasicInfoDAO() {
    return commercialTenantBasicInfoDAO;
  }

  public void setCommercialTenantBasicInfoDAO(
      CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO) {
    this.commercialTenantBasicInfoDAO = commercialTenantBasicInfoDAO;
  }

  public PersonalBasicInfoDAO getPersonalBasicInfoDAO() {
    return personalBasicInfoDAO;
  }

  public void setPersonalBasicInfoDAO(PersonalBasicInfoDAO personalBasicInfoDAO) {
    this.personalBasicInfoDAO = personalBasicInfoDAO;
  }

  public AccountInfoDAO getAccountInfoDAO() {
    return accountInfoDAO;
  }

  public void setAccountInfoDAO(AccountInfoDAO accountInfoDAO) {
    this.accountInfoDAO = accountInfoDAO;
  }



}
