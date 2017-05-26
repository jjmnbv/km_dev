package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.NwesCsReplyService;
import com.pltfm.app.service.NwesCustomServiceService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.NwesCsReply;
import com.pltfm.app.vobject.NwesCustomService;
import com.pltfm.sys.model.SysUser;

/***
 * 服务信息Action
 */
@Component(value = "nwesCustomServiceAction")
@Scope(value = "prototype")
public class NwesCustomServiceAction extends BaseAction implements ModelDriven {
  /***
   * 服务信息的Service接口
   */
  @Resource(name = "nwesCustomServiceService")
  private NwesCustomServiceService nwesCustomServiceService;
  /***
   * 回复服务信息nwesCsReplyService
   */
  @Resource(name = "nwesCsReplyService")
  private NwesCsReplyService nwesCsReplyService;
  @Resource(name = "bnesCustomerTypeService")
  private BnesCustomerTypeService bnesCustomerTypeService;
  /***
   * 服务信息
   */
  private NwesCustomService nwesCustomService;
  /***
   * 回复信息
   */
  private NwesCsReply nwesCsReply;

  /**
   * 服务主键
   */
  private Integer customService_Id;
  /**
   * 服务主键集合
   */
  private List<Integer> customServiceIds;
  /**
   * 分页类
   */
  private Page page;
  /** 客户类别集合 **/
  private List customerTypeList;

  /** 客户类别主键 **/
  private String customerId;

  /**
   * 服务信息列表
   * 
   * @return
   */
  public String pageList() {
    try {
      if (page == null) {
        page = new Page();
      }
      operateCustomerType();
      // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
      if (nwesCustomService != null && nwesCustomService.getCustomer_son_id() != null) {
        customerId = String.valueOf(nwesCustomService.getCustomerTypeId());
        nwesCustomService.setCustomerTypeId(nwesCustomService.getCustomer_son_id());
        page = nwesCustomServiceService.searchPageByVo(page, nwesCustomService);
        nwesCustomService.setCustomerTypeId(Integer.valueOf(customerId));
      } else {
        page = nwesCustomServiceService.searchPageByVo(page, nwesCustomService);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("customService.query.fail"));
      return "queryFail";
    }
    return "pageSuccess";
  }


  /**
   * 进入添加服务信息页面
   * 
   * @return
   */
  public String preAdd() {
    return "preAddSuccess";
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
      super.writeJson(bnesCustomerTypeService.findParentList(Integer.valueOf(customerId)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /****
   * 添加服务记录
   */
  @Token
  public String add() {
    try {
      if (nwesCustomService.getCustomer_son_id() != null) {
        nwesCustomService.setCustomerTypeId(nwesCustomService.getCustomer_son_id());
      }
      SysUser sysuser = (SysUser) session.get("sysUser");
      nwesCustomService.setLoginId(sysuser.getUserId());
      nwesCustomService.setCreaded(sysuser.getUserId());
      nwesCustomService.setCreateDate(new Date());

      nwesCustomServiceService.insert(nwesCustomService);
      this.addActionMessage(ConfigureUtils.getMessageConfig("customService.add.success"));
      nwesCustomService = new NwesCustomService();
      return this.pageList();
    } catch (SQLException e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("customService.add.fail"));
      return this.preAdd();
    }
  }

  /****
   * 添加回复服务记录
   */
  @Token
  public String addReply() {
    try {

      SysUser sysuser = (SysUser) session.get("sysUser");
      nwesCustomService.setLoginId(sysuser.getUserId());
      nwesCustomService.setReplyStatus(1);// 1回复状态改为已回复
      nwesCustomServiceService.update(nwesCustomService);
      int count = nwesCsReplyService.getReplyCounts(nwesCustomService.getCustomServiceId());
      if (count < 1) {
        nwesCsReply.setReplyRdate(new Date());
        nwesCsReply.setCustomerSurveyId(nwesCustomService.getCustomServiceId());
        nwesCsReplyService.insert(nwesCsReply);
      }
      this.addActionMessage(ConfigureUtils.getMessageConfig("reply.update.success"));
      nwesCustomService = new NwesCustomService();
      return this.pageList();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("reply.update.fail"));
      return this.pageList();
    }
  }

  /***
   * 删除服务记录
   */
  @Token
  public String detele() {
    try {
      nwesCustomServiceService.delete(customServiceIds);
      this.addActionMessage(ConfigureUtils.getMessageConfig("customService.delete.success"));
      return this.pageList();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("customService.delete.fail"));
      return this.pageList();
    }
  }

  /***
   * 更新服务记录
   */
  public String update() {
    try {
      if (nwesCustomService.getCustomer_son_id() != null) {
        nwesCustomService.setCustomerTypeId(nwesCustomService.getCustomer_son_id());
      }
      SysUser sysuser = (SysUser) session.get("sysUser");
      nwesCustomService.setModified(sysuser.getUserId());
      nwesCustomService.setLoginId(sysuser.getUserId());
      nwesCustomService.setModifieDate(new Date());
      nwesCustomServiceService.update(nwesCustomService);
      this.addActionMessage(ConfigureUtils.getMessageConfig("customService.update.success"));
      nwesCustomService = new NwesCustomService();
      return this.pageList();
    } catch (SQLException e) {
      this.addActionError(ConfigureUtils.getMessageConfig("customService.update.fail"));
      return this.pageList();
    }
  }

  /**
   * 进入回复服务信息
   * 
   * @return
   */
  public String preUpdate() {
    try {
      nwesCustomService = nwesCustomServiceService.selectByPrimaryKey(customService_Id);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return "INPUT";
    }
    return "preaddReply";
  }


  /**
   * 查询详细信息
   * 
   * @return
   */
  public String preDetail() {
    try {
      nwesCustomService = nwesCustomServiceService.selectByPrimaryKey(customService_Id);
      nwesCsReply = nwesCsReplyService.getCustomerSurveyId(customService_Id);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      this.addActionError(ConfigureUtils.getMessageConfig("customService.show.fail"));
      return this.pageList();
    }
    return "preDetailSuccess";
  }



  public NwesCustomServiceService getNwesCustomServiceService() {
    return nwesCustomServiceService;
  }

  public void setNwesCustomServiceService(NwesCustomServiceService nwesCustomServiceService) {
    this.nwesCustomServiceService = nwesCustomServiceService;
  }

  public NwesCustomService getNwesCustomService() {
    return nwesCustomService;
  }

  public void setNwesCustomService(NwesCustomService nwesCustomService) {
    this.nwesCustomService = nwesCustomService;
  }

  public Integer getCustomService_Id() {
    return customService_Id;
  }

  public void setCustomService_Id(Integer customServiceId) {
    customService_Id = customServiceId;
  }

  public List<Integer> getCustomServiceIds() {
    return customServiceIds;
  }

  public void setCustomServiceIds(List<Integer> customServiceIds) {
    this.customServiceIds = customServiceIds;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public List getCustomerTypeList() {
    return customerTypeList;
  }

  public void setCustomerTypeList(List customerTypeList) {
    this.customerTypeList = customerTypeList;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public BnesCustomerTypeService getBnesCustomerTypeService() {
    return bnesCustomerTypeService;
  }


  public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
    this.bnesCustomerTypeService = bnesCustomerTypeService;
  }

  @Override
public Object getModel() {
    nwesCustomService = new NwesCustomService();
    return nwesCustomService;
  }

  public NwesCsReplyService getNwesCsReplyService() {
    return nwesCsReplyService;
  }


  public void setNwesCsReplyService(NwesCsReplyService nwesCsReplyService) {
    this.nwesCsReplyService = nwesCsReplyService;
  }


  public NwesCsReply getNwesCsReply() {
    return nwesCsReply;
  }


  public void setNwesCsReply(NwesCsReply nwesCsReply) {
    this.nwesCsReply = nwesCsReply;
  }

}
