package com.pltfm.app.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.sys.model.SysUser;


/**
 * 账户信息Action类
 * 
 * @author cjm
 * @since 2013-7-10
 */
@Component(value = "accountInfoAction")
@Scope(value = "prototype")
public class AccountInfoAction extends BaseAction implements ModelDriven {
  /**
   * 登录信息集合
   */
  private List<LoginInfo> loginInfoList;
  /**
   * 分页类
   */
  private Page page;

  /**
   * 账户信息实体
   */
  private AccountInfo accountInfo;

  /** 客户类别集合 **/
  private List customerTypeList;

  @Resource(name = "bnesCustomerTypeService")
  private BnesCustomerTypeService bnesCustomerTypeService;

  /**
   * 多条删除账户信息主键集合
   */
  private List<Integer> n_AccountIds;

  /**
   * 客户类别ID
   */
  private Integer customerId;

  /** 公共页面返回列表类型 **/
  private Integer showType;
  /**
   * 账户信息业务逻辑的接口
   */
  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;

  @Resource(name = "loginInfoService")
  private LoginInfoService loginInfoService;

  // 标记来自账户编辑列表业务处理
  public String edit;
  
  //来源页面，不同页面显示效果不一样
  private String sourcePage;

  /**
   * 查询账户号
   */
  public void checkAccountLogin() {
    try {
      super.writeJson(accountInfoService.checkAccountLogin(accountInfo.getAccountLogin()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 添加账户信息
   * 
   * @return
   */
  public String add() {
    Integer rows = 0;
    try {
      rows = accountInfoService.addAccountInfo(accountInfo);
      if (rows > 0) {
        accountInfo = new AccountInfo();
        this.addActionMessage(ConfigureUtils.getMessageConfig("accountInfo.add.success"));
        return pageList();
      }
    } catch (Exception e) {
      this.addActionMessage(ConfigureUtils.getMessageConfig("accountInfo.operate.fail"));
      e.printStackTrace();
    }

    return preAdd();
  }


  /**
   * 进入账户信息页面
   * 
   * @return
   */
  public String preAdd() {
    try {
      loginInfoList = accountInfoService.queryLoginInfoList();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preAddSuccess";
  }

  /**
   * 删除账户信息
   * 
   * @return
   */
  public String delete() {
    Integer rows = 0;
    try {
      rows = accountInfoService.deleteByPrimaryKey(n_AccountIds);
      if (rows > 0) {
        accountInfo = new AccountInfo();
        this.addActionMessage(ConfigureUtils.getMessageConfig("accountInfo.delete.success"));
        return pageList();
      }
    } catch (Exception e) {
      this.addActionMessage(ConfigureUtils.getMessageConfig("accountInfo.delete.fail"));
      e.printStackTrace();
    }

    return pageList();
  }

  /**
   * 修改账户信息
   * 
   * @return
   */
  public String update() {
    Integer rows = 0;
    try {
      // 获取当前登录人
      SysUser sysuser = (SysUser) session.get("sysUser");

      accountInfo.setN_Modified(sysuser.getUserId());
      rows = accountInfoService.updateAccountInfo(accountInfo);
      if (rows > 0) {
        // 如果来自账户编辑标志存在同时修改login_info的登录名
        if (StringUtils.isNotBlank(edit)) {
          LoginInfo loginInfo = new LoginInfo();
          loginInfo.setN_LoginId(accountInfo.getN_LoginId());
          loginInfo.setLoginAccount(accountInfo.getAccountLogin());
          loginInfoService.udpateLoginInfo(loginInfo);
        }

        accountInfo = new AccountInfo();
        this.addActionMessage(ConfigureUtils.getMessageConfig("accountInfo.update.success"));
        if (StringUtils.isNotBlank(edit)) {
          return pageEditList();
        } else {
          return pageList();
        }

      }
    } catch (Exception e) {
      this.addActionMessage(ConfigureUtils.getMessageConfig("accountInfo.operate.fail"));
      e.printStackTrace();
    }
    return preUpdate();
  }

  /**
   * 进入修改账户信息页面
   * 
   * @return
   */
  public String preUpdate() {
    try {
      // loginInfoList = accountInfoService.queryLoginInfoList();
      accountInfo = accountInfoService.selectByPrimaryKey(accountInfo.getN_AccountId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preUpdateSuccess";
  }

  /**
   * 进入账户信息页面
   * 
   * @return
   */
  public String preDetail() {
    try {
      accountInfo = accountInfoService.selectByPrimaryKey(accountInfo.getN_AccountId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preDetailSuccess";
  }

  /**
   * 查询客户类型
   */
  public void operateCustomerType() {
    try {
      customerTypeList = bnesCustomerTypeService.findParentList(Integer.valueOf("0"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 异步调用客户类别信息
   */
  public void ajaxOperateCustomerType() {
    try {
      super.writeJson(bnesCustomerTypeService.findParentList(customerId));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 账户编辑列表
   * 
   * @author lijianjun
   * @return
   */
  public String pageEditList() {
    try {
      operateCustomerType();
      // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
      if (accountInfo != null && accountInfo.getCustomer_son_id() != null) {
        customerId = accountInfo.getN_CustomerTypeId();
        accountInfo.setN_CustomerTypeId(accountInfo.getCustomer_son_id());
        page = accountInfoService.searchPageByVo(page, accountInfo);
        accountInfo.setN_CustomerTypeId(customerId);
      } else {
        page = accountInfoService.searchPageByVo(page, accountInfo);
      }
      this.setEdit("edit");
      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }

  }

  /**
   * 查询账户信息
   * 
   * @return
   */
  public String pageList() {
      HttpServletRequest request = super.getRequest();
    try {
      operateCustomerType();
      // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
      if (accountInfo != null && accountInfo.getCustomer_son_id() != null) {
        customerId = accountInfo.getN_CustomerTypeId();

        accountInfo.setN_CustomerTypeId(accountInfo.getCustomer_son_id());

        page = accountInfoService.searchPageByVo(page, accountInfo);

        accountInfo.setN_CustomerTypeId(customerId);
      } else {
        page = accountInfoService.searchPageByVo(page, accountInfo);
      }
      String sourcePage =request.getParameter("sourcePage");
      if(StringUtils.isNotEmpty(sourcePage)){
          request.setAttribute("sourcePage", sourcePage);
      }
      
      this.setEdit("");

      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }

  }

  /**
   * 弹出账户信息
   * 
   * @return
   */
  public String popUpAccount() {
    try {
      if (page == null) {
        page = new Page();
      }
      page.setPageSize(10);
      page = accountInfoService.searchPageByVo(page, accountInfo);
      return "popUpSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "pageInput";
    }
  }

  /**
   * 
   * @return 通过登录账户查询账户信息
   */
  public String showAccountInfo() {
    try {
      accountInfo = accountInfoService.showAccountInfo(accountInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "preDetailSuccess";
  }



  public String getEdit() {
    return edit;
  }

  public void setEdit(String edit) {
    this.edit = edit;
  }

  @Override
public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public AccountInfo getAccountInfo() {
    return accountInfo;
  }

  public void setAccountInfo(AccountInfo accountInfo) {
    this.accountInfo = accountInfo;
  }



  public List<Integer> getN_AccountIds() {
    return n_AccountIds;
  }

  public void setN_AccountIds(List<Integer> nAccountIds) {
    n_AccountIds = nAccountIds;
  }

  @Override
  public Object getModel() {
    accountInfo = new AccountInfo();
    return accountInfo;
  }

  public List<LoginInfo> getLoginInfoList() {
    return loginInfoList;
  }

  public void setLoginInfoList(List<LoginInfo> loginInfoList) {
    this.loginInfoList = loginInfoList;
  }


  public List getCustomerTypeList() {
    return customerTypeList;
  }

  public void setCustomerTypeList(List customerTypeList) {
    this.customerTypeList = customerTypeList;
  }



  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public Integer getShowType() {
    return showType;
  }

  public void setShowType(Integer showType) {
    this.showType = showType;
  }

  public String getSourcePage() {
    return sourcePage;
  }

  public void setSourcePage(String sourcePage) {
    this.sourcePage = sourcePage;
  }

}
