package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.UserLevel;

@Component(value = "loginInfoAction")
public class LoginInfoAction extends ActionSupport implements ModelDriven {
  @Resource(name = "loginInfoService")
  private LoginInfoService loginInfoService;
  /** 客户类别service **/
  @Resource(name = "bnesCustomerTypeService")
  private BnesCustomerTypeService bnesCustomerTypeService;
  private UserLevel userLevel;
  /**
   * 分页类
   */
  private Page page;
  /** 客户等级 **/
  public List<UserLevel> userLevelList;
  /** 客户类别集合 **/
  private List customerTypeList;
  private Integer customerId;
  private int loginId;
  private List<String> loginIds;
  private int customerTypeId;
  private LoginInfo loginInfo;
  private UserInfoDO userInfoDO;
  private String callBack;

  /**
   * 登录信息列表
   * 
   * @return
   */
  public String pageList() {
    try {
      userLevelList = loginInfoService.getUserLevellist(customerTypeId);
      loginInfo.setN_CustomerTypeId(customerTypeId);
      page = loginInfoService.searchPageByVo(page, loginInfo);
      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "INPUT";
    }
  }

  /**
   * 进入添加登录信息页面
   * 
   * @return
   */
  public String preAdd() {
    try {
      userLevelList = loginInfoService.getUserLevellist(customerTypeId);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "preAddSuccess";
  }

  public String add() {
    try {
      loginInfoService.addLoginInfo(loginInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "saveSuccess";
  }

  public String detele() {
    try {
      loginInfoService.deleteLoginInfo(loginIds);
      return "delSuccess";
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return INPUT;
  }

  public String update() {
    try {

      loginInfoService.udpateLoginInfo(loginInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "saveSuccess";
  }

  /**
   * 进入登录信息详细
   * 
   * @return
   */
  public String getRankInfoId() {
    try {
      userLevelList = loginInfoService.getUserLevellist(customerTypeId);
      loginInfo = loginInfoService.getLoginId(loginId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "updateSuccess";
  }

  /**
   * 查询客户基本信息
   * 
   * @return
   */
  public String queryPageBasicUserInfo() {
    if (page == null) {
      page = new Page();
    }
    try {
      page.setPageSize(10);
      operateCustomerType();
      // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
      if (userInfoDO != null && userInfoDO.getSonCustomerId() != null) {
        customerId = userInfoDO.getCustomerTypeId();
        userInfoDO.setCustomerTypeId(userInfoDO.getSonCustomerId());
        page = loginInfoService.queryPageBasicUserInfo(page, userInfoDO);
        userInfoDO.setCustomerTypeId(customerId);
      } else {
        page = loginInfoService.queryPageBasicUserInfo(page, userInfoDO);
      }

    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("logininfo.query.fail"));
      return "userInfo";
    }
    return "userInfo";
  }

  /**
   * 多选用户列表信息
   * 
   * @return
   */
  public String checkboxInfoList() {
    if (page == null) {
      page = new Page();
    }
    try {
      operateCustomerType();
      // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
      if (userInfoDO != null && userInfoDO.getSonCustomerId() != null) {
        customerId = userInfoDO.getCustomerTypeId();
        userInfoDO.setCustomerTypeId(userInfoDO.getSonCustomerId());
        page = loginInfoService.queryPageBasicUserInfo(page, userInfoDO);
        userInfoDO.setCustomerTypeId(customerId);
      } else {
        page = loginInfoService.queryPageBasicUserInfo(page, userInfoDO);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return "boxInfoList";
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

  public UserLevel getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(UserLevel userLevel) {
    this.userLevel = userLevel;
  }

  public List<UserLevel> getUserLevelList() {
    return userLevelList;
  }

  public void setUserLevelList(List<UserLevel> userLevelList) {
    this.userLevelList = userLevelList;
  }

  public int getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(int customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public LoginInfoService getLoginInfoService() {
    return loginInfoService;
  }

  public void setLoginInfoService(LoginInfoService loginInfoService) {
    this.loginInfoService = loginInfoService;
  }

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public int getLoginId() {
    return loginId;
  }

  public void setLoginId(int loginId) {
    this.loginId = loginId;
  }

  public List<String> getLoginIds() {
    return loginIds;
  }

  public void setLoginIds(List<String> loginIds) {
    this.loginIds = loginIds;
  }

  @Override
public Object getModel() {
    loginInfo = new LoginInfo();
    userInfoDO = new UserInfoDO();
    return loginInfo;
  }

  public UserInfoDO getUserInfoDO() {
    return userInfoDO;
  }

  public void setUserInfoDO(UserInfoDO userInfoDO) {
    this.userInfoDO = userInfoDO;
  }

  public BnesCustomerTypeService getBnesCustomerTypeService() {
    return bnesCustomerTypeService;
  }

  public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
    this.bnesCustomerTypeService = bnesCustomerTypeService;
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

  public String getCallBack() {
    return callBack;
  }

  public void setCallBack(String callBack) {
    this.callBack = callBack;
  }
}
