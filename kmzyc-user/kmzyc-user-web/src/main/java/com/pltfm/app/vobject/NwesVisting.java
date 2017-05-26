package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 拜访信息
 */
public class NwesVisting implements Serializable {
  /***
   * 拜访主键
   */
  private Integer vistingId;
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
   * 拜访者_登录id
   */
  private Integer loginId;
  /***
   * 拜访者姓名
   */
  private Integer vistingName;
  /***
   * 客户姓名
   */
  private String loginName;

  /***
   * 客户类别
   */
  private Integer customerTypeId;

  /***
   * 拜访内容
   */
  private String content;

  /***
   * 拜访时间
   */
  private Date vistingDate;

  /***
   * 客户建议
   */
  private String advice;

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

  public Integer getVistingName() {
    return vistingName;
  }

  public void setVistingName(Integer vistingName) {
    this.vistingName = vistingName;
  }

  public Integer getVistingId() {
    return vistingId;
  }

  public void setVistingId(Integer vistingId) {
    this.vistingId = vistingId;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getVistingDate() {
    return vistingDate;
  }

  public void setVistingDate(Date vistingDate) {
    this.vistingDate = vistingDate;
  }

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

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
