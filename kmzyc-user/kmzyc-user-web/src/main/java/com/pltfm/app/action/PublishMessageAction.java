package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.BnesMessageTempService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.service.PublishMessageService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.BnesInfoPrompt;
import com.pltfm.app.vobject.BnesMessageTempQry;
import com.pltfm.sys.model.SysUser;

@Component(value = "publishMessageAction")
// @Scope(value="request")
@Scope("prototype")
public class PublishMessageAction extends BaseAction implements ModelDriven {
  @Resource(name = "publishMessageService")
  private PublishMessageService publishMessageService;
  @Resource(name = "bnesCustomerTypeService")
  BnesCustomerTypeService bnesCustomerTypeService;
  @Resource(name = "loginInfoService")
  LoginInfoService loginInfoService;
  @Resource(name = "bnesMessageTempService")
  BnesMessageTempService bnesMessageTempService;


  private BnesInfoPrompt bnesInfoPrompt;

  /**
   * 消息主键ID集合
   */
  private List<Integer> infoPromptIds;
  /**
   * 消息主键ID
   */
  private Integer infoPromptId;

  /**
   * 分页类
   */
  private Page page;
  // 客户类别
  Integer customerTypeId;
  Integer customerTypeSonId;
  // 用户信息
  String loginIds;
  String loginNames;
  /** 客户类别集合 **/
  private List customerTypeList;

  // 进入新增
  public String preAdd() {
    operateCustomerType();
    return "add";
  }

  // 发布消息
  @Token
  public String add() {
    try {
      SysUser sysuser = (SysUser) session.get("sysUser");
      bnesInfoPrompt.setCreateId(sysuser.getUserId());
      bnesInfoPrompt.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
      bnesInfoPrompt.setReleaseDate(DateTimeUtils.getCalendarInstance().getTime());
      if (customerTypeSonId != null) {
        bnesInfoPrompt.setCustomerType(customerTypeSonId);
      }
      publishMessageService.addMessage(bnesInfoPrompt);
      this.addActionMessage(ConfigureUtils.getMessageConfig("infoPrompt.add.succ"));
      bnesInfoPrompt = new BnesInfoPrompt();
      bnesInfoPrompt.setTitle("");
      bnesInfoPrompt.setType(null);
    } catch (SQLException e) {
      e.printStackTrace();
      this.addActionMessage(ConfigureUtils.getMessageConfig("infoPrompt.add.fail"));
    }
    return list();
  }

  // 进入修改
  public String preEdit() {
    try {
      operateCustomerType();
      bnesInfoPrompt = publishMessageService.queryPrompt(bnesInfoPrompt);
      // 得到客户类别
      Integer customerType = bnesInfoPrompt.getCustomerType();
      if (null != customerType) {
        BnesCustomerTypeQuery customer =
            bnesCustomerTypeService.findBnesCustomerTypeDOByPrimaryKey(customerType);
        if (customer != null) {

          if (customer.getParentId() == 0) {
            customerTypeId = customer.getCustomerTypeId();
            // 有父类
          } else {


            customerTypeId = customer.getParentId();
            customerTypeSonId = customer.getCustomerTypeId();
          }

        }
      }
      // 指定人员信息
      BnesMessageTempQry qry = new BnesMessageTempQry();
      qry.setInfoPromptId(bnesInfoPrompt.getInfoPromptId());
      List list = bnesMessageTempService.selectByExample(qry);
      if (null != list && list.size() > 0) {

        for (int i = 0; i < list.size(); i++) {
          BnesMessageTempQry bnesMessageTemp = (BnesMessageTempQry) list.get(i);
          if (null != loginIds && null != loginNames) {
            loginIds += bnesMessageTemp.getAccountId() + ",";

            loginNames += bnesMessageTemp.getAccountName() + ",";
          } else {
            loginIds = bnesMessageTemp.getAccountId() + ",";
            loginNames = bnesMessageTemp.getAccountName() + ",";
          }
        }
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
    return "edit";
  }

  // 修改
  @Token
  public String edit() {
    try {
      SysUser sysuser = (SysUser) session.get("sysUser");

      bnesInfoPrompt.setModified(sysuser.getUserId());
      bnesInfoPrompt.setModifiedDate(DateTimeUtils.getCalendarInstance().getTime());
      if (customerTypeSonId != null) {
        bnesInfoPrompt.setCustomerType(customerTypeSonId);
      }
      publishMessageService.updateMessage(bnesInfoPrompt, loginIds);
      this.addActionMessage(ConfigureUtils.getMessageConfig("infoPrompt.update.succ"));
      bnesInfoPrompt = new BnesInfoPrompt();
      bnesInfoPrompt.setTitle("");
      bnesInfoPrompt.setType(null);
    } catch (SQLException e) {
      e.printStackTrace();
      this.addActionMessage(ConfigureUtils.getMessageConfig("infoPrompt.add.fail"));
    }
    return list();

  }

  // 删除操作
  @Token
  public String delete() {
    try {
      Integer infoPromptId = bnesInfoPrompt.getInfoPromptId();
      if (null != infoPromptId) {
        publishMessageService.deleteById(infoPromptId);
      } else {

        publishMessageService.delete(infoPromptIds);

      }
      this.addActionMessage(ConfigureUtils.getMessageConfig("infoPrompt.del.succ"));
    } catch (SQLException e) {
      e.printStackTrace();
      this.addActionMessage(ConfigureUtils.getMessageConfig("infoPrompt.del.fail"));
    }
    return list();
  }

  // 消息列表
  public String list() {
    try {
      if (page == null) {
        page = new Page();
      }
      Integer count = publishMessageService.selectListCount(bnesInfoPrompt);
      page.setRecordCount(count);
      bnesInfoPrompt.setSkip(page.getStartIndex());
      bnesInfoPrompt.setMax(page.getStartIndex() + page.getPageSize());
      List list = publishMessageService.selectList(bnesInfoPrompt);
      page.setDataList(list);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "list";
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

  public PublishMessageService getPublishMessageService() {
    return publishMessageService;
  }

  public void setPublishMessageService(PublishMessageService publishMessageService) {
    this.publishMessageService = publishMessageService;
  }

  @Override
  public BnesInfoPrompt getModel() {
    bnesInfoPrompt = new BnesInfoPrompt();
    return bnesInfoPrompt;
  }

  public BnesInfoPrompt getBnesInfoPrompt() {
    return bnesInfoPrompt;
  }

  public void setBnesInfoPrompt(BnesInfoPrompt bnesInfoPrompt) {
    this.bnesInfoPrompt = bnesInfoPrompt;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public List<Integer> getInfoPromptIds() {
    return infoPromptIds;
  }

  public void setInfoPromptIds(List<Integer> infoPromptIds) {
    this.infoPromptIds = infoPromptIds;
  }



  public Integer getInfoPromptId() {
    return infoPromptId;
  }

  public void setInfoPromptId(Integer infoPromptId) {
    this.infoPromptId = infoPromptId;
  }

  public BnesCustomerTypeService getBnesCustomerTypeService() {
    return bnesCustomerTypeService;
  }

  public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
    this.bnesCustomerTypeService = bnesCustomerTypeService;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public Integer getCustomerTypeSonId() {
    return customerTypeSonId;
  }

  public void setCustomerTypeSonId(Integer customerTypeSonId) {
    this.customerTypeSonId = customerTypeSonId;
  }

  public LoginInfoService getLoginInfoService() {
    return loginInfoService;
  }

  public void setLoginInfoService(LoginInfoService loginInfoService) {
    this.loginInfoService = loginInfoService;
  }



  public String getLoginNames() {
    return loginNames.substring(0, loginNames.length() - 1);
  }

  public void setLoginNames(String loginNames) {
    this.loginNames = loginNames;
  }

  public BnesMessageTempService getBnesMessageTempService() {
    return bnesMessageTempService;
  }

  public void setBnesMessageTempService(BnesMessageTempService bnesMessageTempService) {
    this.bnesMessageTempService = bnesMessageTempService;
  }

  public String getLoginIds() {
    return loginIds.substring(0, loginIds.length() - 1);
  }

  public void setLoginIds(String loginIds) {
    this.loginIds = loginIds;
  }

  public List getCustomerTypeList() {
    return customerTypeList;
  }

  public void setCustomerTypeList(List customerTypeList) {
    this.customerTypeList = customerTypeList;
  }


}
