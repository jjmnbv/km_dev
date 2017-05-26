package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * 调查记录
 */
public class NewsCustomerSurvey implements Serializable {
  /***
   * 
   * 主键ID
   */
  private Integer customerSurveyId;
  /***
   * 
   * 登录ID
   */
  private Integer loginId;
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
   * 
   * 登录名
   */
  private Integer loginName;
  /***
   * 
   * 客户名
   */
  private String name;

  /***
   * 
   * 客户类型
   */
  private Integer customerTypeId;
  /***
   * 
   * 内容
   */
  private String content;
  /***
   * 
   * 使用情况
   */
  private String condition;

  /***
   * 
   * 调查时间
   */
  private Date recordDate;
  /***
   * 
   * 创建人
   */

  private Integer created;

  /***
   * 
   * 创建时间
   */
  private Date createDate;
  /***
   * 
   * 修改人
   */
  private Integer modified;

  /***
   * 
   * 修改时间
   */
  private Date modifyDate;

  /** 客户类别子类别id(只传值) **/
  private Integer customer_son_id;
  /** 客户类型名称 **/
  private String customerName;

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

  public Integer getCustomerSurveyId() {
    return customerSurveyId;
  }

  public void setCustomerSurveyId(Integer customerSurveyId) {
    this.customerSurveyId = customerSurveyId;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public Date getRecordDate() {
    return recordDate;
  }

  public void setRecordDate(Date recordDate) {
    this.recordDate = recordDate;
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

  public Integer getLoginName() {
    return loginName;
  }

  public void setLoginName(Integer loginName) {
    this.loginName = loginName;
  }

  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;
}
