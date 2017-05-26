package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户积分规则实体
 * 
 * @author zhl
 * @since 2013-07-10
 */
public class ScoreRule implements Serializable {
  /** 客户积分规则主键 **/
  private Integer n_scoreRuleId;
  /** 客户积分规则编号 **/
  private String code;
  /** 客户积分规则简述 **/
  private String discribe;
  /** 客户类别 **/
  private Integer clientType;
  /** 客户类别子类别id(只传值) **/
  private Integer customer_son_id;
  /** 客户类型名称 **/
  private String customerName;
  /** 积分表达式 **/
  private String scoreExpress;
  /** 备注 **/
  private String remark;
  /** 创建日期 **/
  private Date d_createDate;
  /** 创建人 **/
  private Integer n_created;
  /** 修改日期 **/
  private Date d_modifyDate;
  /** 修改人 **/
  private Integer n_modified;
  /** 开始条数 **/
  private Integer startIndex;
  /** 结束条数 **/
  private Integer endIndex;
  /** 每日上限 **/
  private Integer dayMax;
  /** 积分值 **/
  private String scoreValue;
  /**积分规则状态，0表示启用，1表示禁用 */
  private Integer status;
  /**积分排序顺序号 */
  private Integer ruleOrder;
  /**该积分规则是否有时间限制,0表示没有，1表示有 */
  private Integer expireLimit;
  /**积分有效开始时间 （时分秒）*/
  private String expireStartDate;
  /**积分有效结束时间  （时分秒）*/
  private String expireEndDate;
  
  public Integer getRuleOrder() {
    return ruleOrder;
  }

  public void setRuleOrder(Integer ruleOrder) {
    this.ruleOrder = ruleOrder;
  }

  public Integer getExpireLimit() {
    return expireLimit;
  }

  public void setExpireLimit(Integer expireLimit) {
    this.expireLimit = expireLimit;
  }

  public String getExpireStartDate() {
    return expireStartDate;
  }

  public void setExpireStartDate(String expireStartDate) {
    this.expireStartDate = expireStartDate;
  }

  public String getExpireEndDate() {
    return expireEndDate;
  }

  public void setExpireEndDate(String expireEndDate) {
    this.expireEndDate = expireEndDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getScoreValue() {
    return scoreValue;
  }

  public void setScoreValue(String scoreValue) {
    this.scoreValue = scoreValue;
  }

  public Integer getDayMax() {
    return dayMax;
  }

  public void setDayMax(Integer dayMax) {
    this.dayMax = dayMax;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDiscribe() {
    return discribe;
  }

  public void setDiscribe(String discribe) {
    this.discribe = discribe;
  }

  public Integer getClientType() {
    return clientType;
  }

  public void setClientType(Integer clientType) {
    this.clientType = clientType;
  }

  public Integer getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public Integer getN_scoreRuleId() {
    return n_scoreRuleId;
  }

  public void setN_scoreRuleId(Integer nScoreRuleId) {
    n_scoreRuleId = nScoreRuleId;
  }

  public Date getD_createDate() {
    return d_createDate;
  }

  public void setD_createDate(Date dCreateDate) {
    d_createDate = dCreateDate;
  }

  public Integer getN_created() {
    return n_created;
  }

  public void setN_created(Integer nCreated) {
    n_created = nCreated;
  }

  public Date getD_modifyDate() {
    return d_modifyDate;
  }

  public void setD_modifyDate(Date dModifyDate) {
    d_modifyDate = dModifyDate;
  }

  public Integer getN_modified() {
    return n_modified;
  }

  public void setN_modified(Integer nModified) {
    n_modified = nModified;
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

  public String getScoreExpress() {
    return scoreExpress;
  }

  public void setScoreExpress(String scoreExpress) {
    this.scoreExpress = scoreExpress;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
