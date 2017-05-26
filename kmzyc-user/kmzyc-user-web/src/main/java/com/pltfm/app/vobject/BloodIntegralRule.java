package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 经验值规则
 **/
public class BloodIntegralRule implements Serializable {
  /**
   * 经验值规则 ID
   **/
  private Integer integralRuleId;

  /**
   * 专家类型
   **/
  private Integer clientType;
  /**
   * 类型名称
   */
  private String customerName;
  /**
   * 经验值规则
   **/
  private String code;

  /**
   * 规则简述
   **/
  private String discribe;

  /**
   * 经验值比例
   **/
  private Integer integralscale;

  /**
   * 经验值数量
   **/
  private Integer integralnumber;

  /**
   * 创建日期
   **/
  private Date createDate;

  /**
   * 创建人
   **/
  private Integer created;

  /**
   * 修改日期
   **/
  private Date modifyDate;

  /**
   * 修改人
   **/
  private Integer modified;
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

  public Integer getIntegralRuleId() {
    return integralRuleId;
  }

  public void setIntegralRuleId(Integer integralRuleId) {
    this.integralRuleId = integralRuleId;
  }

  public Integer getClientType() {
    return clientType;
  }

  public void setClientType(Integer clientType) {
    this.clientType = clientType;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
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

  public Integer getIntegralscale() {
    return integralscale;
  }

  public void setIntegralscale(Integer integralscale) {
    this.integralscale = integralscale;
  }

  public Integer getIntegralnumber() {
    return integralnumber;
  }

  public void setIntegralnumber(Integer integralnumber) {
    this.integralnumber = integralnumber;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getCreated() {
    return created;
  }

  public void setCreated(Integer created) {
    this.created = created;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getModified() {
    return modified;
  }

  public void setModified(Integer modified) {
    this.modified = modified;
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
