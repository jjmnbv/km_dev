package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 维护信息
 */
public class NwesMaintenace implements Serializable {
  /***
   * 维护id
   */
  private Integer maintenaceId;
  /***
   * 
   * 系统登录名称
   */
  private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  /***
   * 维护人
   */
  private Integer loginId;

  /***
   * 客户姓名
   */
  private String loginName;

  /***
   * 客户类别
   */
  private Integer customerTypeId;
  /***
   * 反馈问题
   */
  private String question;
  /***
   * 维护信息
   */
  private String maintenaceSchedule;

  /***
   * 维护时间
   */
  private Date maintenaceDate;
  /***
   * 创建人
   */
  private Integer created;

  /***
   * 创建时间
   */
  private Date createDate;

  /***
   * 修改人
   */
  private Integer modified;

  /***
   * 修改时间
   */
  private Date modifyDate;

  public Integer getCreated() {
    return created;
  }

  public void setCreated(Integer created) {
    this.created = created;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getModified() {
    return modified;
  }

  public void setModified(Integer modified) {
    this.modified = modified;
  }

  /** 客户类别子类别id(只传值) **/
  private Integer customer_son_id;
  /** 客户类型名称 **/
  private String customerName;
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

  public Integer getMaintenaceId() {
    return maintenaceId;
  }

  public void setMaintenaceId(Integer maintenaceId) {
    this.maintenaceId = maintenaceId;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getMaintenaceSchedule() {
    return maintenaceSchedule;
  }

  public void setMaintenaceSchedule(String maintenaceSchedule) {
    this.maintenaceSchedule = maintenaceSchedule;
  }

  public Date getMaintenaceDate() {
    return maintenaceDate;
  }

  public void setMaintenaceDate(Date maintenaceDate) {
    this.maintenaceDate = maintenaceDate;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getCustomer_son_id() {
    return customer_son_id;
  }

  public void setCustomer_son_id(Integer customerSonId) {
    customer_son_id = customerSonId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }
}
