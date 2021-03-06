package com.kmzyc.b2b.model;

import java.util.Date;

public class UserLevel {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.N_LEVEL_ID
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long levelId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.CODE
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private String code;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.LEVEL_NAME
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private String levelName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.N_CUSTOMER_TYPE_ID
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long customerTypeId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.EXPEND_MIN
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long expendMin;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.EXPEND_MAX
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long expendMax;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.SCORE_MIN
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long scoreMin;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.SCORE_MAX
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long scoreMax;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.VALID
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long valid;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.D_CREATE_DATE
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Date createDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.N_CREATED
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long created;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.D_MODIFY_DATE
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Date modifyDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.N_MODIFIED
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private Long modified;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * USER_LEVEL.REMARK
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
  private String remark;

  private Long yearMin;

  private String customerName;

  private Double expend;

  public Long getLevelId() {
    return levelId;
  }

  public void setLevelId(Long levelId) {
    this.levelId = levelId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public Long getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Long customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public Long getExpendMin() {
    return expendMin;
  }

  public void setExpendMin(Long expendMin) {
    this.expendMin = expendMin;
  }

  public Long getExpendMax() {
    return expendMax;
  }

  public void setExpendMax(Long expendMax) {
    this.expendMax = expendMax;
  }

  public Long getScoreMin() {
    return scoreMin;
  }

  public void setScoreMin(Long scoreMin) {
    this.scoreMin = scoreMin;
  }

  public Long getScoreMax() {
    return scoreMax;
  }

  public void setScoreMax(Long scoreMax) {
    this.scoreMax = scoreMax;
  }

  public Long getValid() {
    return valid;
  }

  public void setValid(Long valid) {
    this.valid = valid;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Long getModified() {
    return modified;
  }

  public void setModified(Long modified) {
    this.modified = modified;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Double getExpend() {
    return expend;
  }

  public void setExpend(Double expend) {
    this.expend = expend;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public Long getYearMin() {
    return yearMin;
  }

  public void setYearMin(Long yearMin) {
    this.yearMin = yearMin;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column USER_LEVEL.N_LEVEL_ID
   * 
   * @return the value of USER_LEVEL.N_LEVEL_ID
   * 
   * @ibatorgenerated Wed Oct 16 14:48:53 CST 2013
   */
}
