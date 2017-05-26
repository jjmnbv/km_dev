package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 服务信息
 * 
 */
public class NwesCustomService implements Serializable {
  /***
   * 服务主键
   * 
   */
  private Integer customServiceId;
  /***
   * 客服人员
   * 
   */
  private Integer loginId;
  /***
   * 
   * 系统登录名称
   */
  private String userName;
  /***
   * 客户姓名
   * 
   */

  private String loginName;

  /***
   * 客户类型
   * 
   */
  private Integer customerTypeId;

  /***
   * 服务方式
   * 
   */

  private Integer customServiceType;
  /***
   * 服务类型
   * 
   */
  private Integer customServiceMode;

  /***
   * 服务内容
   * 
   */
  private String content;
  /***
   * 回复状态
   * 
   */
  private Integer replyStatus;

  /***
   * 提出时间
   * 
   */
  private Date customServiceDate;

  private Integer creaded;

  private Integer modified;

  private Date createDate;

  private Date modifieDate;

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

  public Integer getCustomServiceId() {
    return customServiceId;
  }

  public void setCustomServiceId(Integer customServiceId) {
    this.customServiceId = customServiceId;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public Integer getReplyStatus() {
    return replyStatus;
  }

  public void setReplyStatus(Integer replyStatus) {
    this.replyStatus = replyStatus;
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

  public Integer getCustomServiceType() {
    return customServiceType;
  }

  public void setCustomServiceType(Integer customServiceType) {
    this.customServiceType = customServiceType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public Integer getCustomServiceMode() {
    return customServiceMode;
  }

  public void setCustomServiceMode(Integer customServiceMode) {
    this.customServiceMode = customServiceMode;
  }

  public Date getCustomServiceDate() {
    return customServiceDate;
  }

  public void setCustomServiceDate(Date customServiceDate) {
    this.customServiceDate = customServiceDate;
  }

  public Integer getCreaded() {
    return creaded;
  }

  public void setCreaded(Integer creaded) {
    this.creaded = creaded;
  }

  public Integer getModified() {
    return modified;
  }

  public void setModified(Integer modified) {
    this.modified = modified;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getModifieDate() {
    return modifieDate;
  }

  public void setModifieDate(Date modifieDate) {
    this.modifieDate = modifieDate;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
