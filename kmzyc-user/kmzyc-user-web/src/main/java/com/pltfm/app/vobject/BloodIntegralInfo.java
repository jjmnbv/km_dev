package com.pltfm.app.vobject;

import java.util.Date;

/****
 * 
 * 经验明细
 */
public class BloodIntegralInfo {
  /****
   * 
   * 经验明细id
   */
  private Integer integralInfoId;
  /****
   * 
   * 登录id
   */
  private Integer loginId;
  /****
   * 
   * 登录名
   */
  private String loginAccount;
  /****
   * 
   * 经验数量
   */
  private Integer integralNumber;

  /****
   * 
   * 经验明细
   */
  private String discribe;

  /****
   * 
   * 创建日期
   */
  private Date createDate;

  /****
   * 
   * 创建人
   */
  private Integer created;

  /****
   * 
   * 修改日期
   */
  private Date modifyDate;
  /****
   * 
   * 修改人
   */
  private Integer modified;
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

  public Integer getIntegralInfoId() {
    return integralInfoId;
  }

  public void setIntegralInfoId(Integer integralInfoId) {
    this.integralInfoId = integralInfoId;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public Integer getIntegralNumber() {
    return integralNumber;
  }

  public void setIntegralNumber(Integer integralNumber) {
    this.integralNumber = integralNumber;
  }

  public String getDiscribe() {
    return discribe;
  }

  public void setDiscribe(String discribe) {
    this.discribe = discribe;
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
