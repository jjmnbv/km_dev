package com.pltfm.app.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesAuthenticationInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAuthenticationInfo;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;

/**
 * 实名认证信息Action类
 * 
 * @author cjm
 * @since 2013-8-1
 */
@Component(value = "bnesAuthenticationInfoAction")
@Scope(value = "prototype")
public class BnesAuthenticationInfoAction extends BaseAction implements ModelDriven {

  /**
   * 实名认证信息
   */
  private BnesAuthenticationInfo bnesAuthenticationInfo;

  /**
   * 个人信息
   */
  private PersonalBasicInfo personalBasicInfo;

  /**
   * 商户信息
   */
  private CommercialTenantBasicInfo commercialTenantBasicInfo;
  /**
   * 账户信息
   */
  private AccountInfo accountInfo;

  /**
   * 分页类
   */
  private Page page;

  /**
   * 实名认证信息业务逻辑接口
   */
  @Resource(name = "bnesAuthenticationInfoService")
  private BnesAuthenticationInfoService bnesAuthenticationInfoService;

  /**
   * 查询实名认证信息
   * 
   * @return
   */
  public String pageList() {
    try {
      page = bnesAuthenticationInfoService.searchPageByVo(page, bnesAuthenticationInfo);
      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }

  }

  /**
   * 进入审核实名认证信息页面
   * 
   * @return
   */
  public String preAuditing() {
    try {
      bnesAuthenticationInfo = bnesAuthenticationInfoService
          .selectByBnesAuthenticationInfo(bnesAuthenticationInfo.getAuthenticationId());
      accountInfo =
          bnesAuthenticationInfoService.selectByAccountInfo(bnesAuthenticationInfo.getAccountId());
      if (accountInfo.getN_CustomerTypeId() == 1) {
        personalBasicInfo =
            bnesAuthenticationInfoService.selectByPrimaryPersonal(accountInfo.getN_LoginId());

      } else {
        commercialTenantBasicInfo =
            bnesAuthenticationInfoService.selectByPrimaryCommercial(accountInfo.getN_LoginId());
        this.addActionMessage(
            ConfigureUtils.getMessageConfig("bnesAuthenticationInfo.auditing.fail"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preAuditing";
  }

  /**
   * 返回列表页面
   * 
   * @return
   */
  public String returnPageList() {
    bnesAuthenticationInfo = new BnesAuthenticationInfo();
    return this.pageList();
  }

  /**
   * 审核实名认证
   * 
   * @return
   */
  public String update() {
    if (1 == bnesAuthenticationInfo.getExaminationValue()) {
      try {
        bnesAuthenticationInfoService.updateBnesAuthenticationInfo(bnesAuthenticationInfo,
            commercialTenantBasicInfo, personalBasicInfo);
        this.addActionMessage(
            ConfigureUtils.getMessageConfig("bnesAuthenticationInfo.auditing.success"));
      } catch (Exception e) {
        this.addActionMessage(
            ConfigureUtils.getMessageConfig("bnesAuthenticationInfo.auditing.fail"));
        e.printStackTrace();
      }
    }
    bnesAuthenticationInfo = new BnesAuthenticationInfo();
    return this.pageList();
  }

  @Override
  public Object getModel() {
    if (bnesAuthenticationInfo == null) {
      bnesAuthenticationInfo = new BnesAuthenticationInfo();
    }
    return bnesAuthenticationInfo;
  }

  public BnesAuthenticationInfo getBnesAuthenticationInfo() {
    return bnesAuthenticationInfo;
  }

  public void setBnesAuthenticationInfo(BnesAuthenticationInfo bnesAuthenticationInfo) {
    this.bnesAuthenticationInfo = bnesAuthenticationInfo;
  }

  public PersonalBasicInfo getPersonalBasicInfo() {
    return personalBasicInfo;
  }

  public void setPersonalBasicInfo(PersonalBasicInfo personalBasicInfo) {
    this.personalBasicInfo = personalBasicInfo;
  }

  public CommercialTenantBasicInfo getCommercialTenantBasicInfo() {
    return commercialTenantBasicInfo;
  }

  public void setCommercialTenantBasicInfo(CommercialTenantBasicInfo commercialTenantBasicInfo) {
    this.commercialTenantBasicInfo = commercialTenantBasicInfo;
  }

  public AccountInfo getAccountInfo() {
    return accountInfo;
  }

  public void setAccountInfo(AccountInfo accountInfo) {
    this.accountInfo = accountInfo;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

}
