package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-07-07
 */
public class QualificationsApplyDO implements Serializable {

  private static final long serialVersionUID = 140470180147960720L;

  /**
   * column QUALIFICATIONS_APPLY.ID
   */
  private Integer id;

  /**
   * column QUALIFICATIONS_APPLY.USER_ID
   */
  private Integer userId;

  /**
   * column QUALIFICATIONS_APPLY.APPLY_TYPE
   */
  private String applyType;

  /**
   * column QUALIFICATIONS_APPLY.TEL
   */
  private String tel;

  /**
   * column QUALIFICATIONS_APPLY.APPLY_DATE
   */
  private Date applyDate;


  // 申请开始时间

  private Date applyStartDate;

  // 申请结束时间

  private Date applyEndDate;

  /**
   * column QUALIFICATIONS_APPLY.APPLY_REASONS
   */
  private String applyReasons;

  /**
   * column QUALIFICATIONS_APPLY.STATUS
   */
  private Short status;

  /**
   * column QUALIFICATIONS_APPLY.AUDITING_DATE
   */
  private Date auditingDate;

  /**
   * column QUALIFICATIONS_APPLY.AUDITING_ID
   */
  private Integer auditingId;

  /**
   * column QUALIFICATIONS_APPLY.REMARKS
   */
  private String remarks;

  // 用户名

  private String userName;


  // 登陆名
  private String loginName;

  // USER_REAL
  private String userReal;

  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;



  public String getUserName() {
    return userName;
  }



  public void setUserName(String userName) {
    this.userName = userName;
  }



  public Integer getId() {
    return id;
  }



  public void setId(Integer id) {
    this.id = id;
  }



  public Integer getUserId() {
    return userId;
  }



  public void setUserId(Integer userId) {
    this.userId = userId;
  }



  public String getApplyType() {
    return applyType;
  }



  public void setApplyType(String applyType) {
    this.applyType = applyType;
  }



  public String getTel() {
    return tel;
  }



  public void setTel(String tel) {
    this.tel = tel;
  }



  public Date getApplyDate() {
    return applyDate;
  }



  public void setApplyDate(Date applyDate) {
    this.applyDate = applyDate;
  }



  public String getApplyReasons() {
    return applyReasons;
  }



  public void setApplyReasons(String applyReasons) {
    this.applyReasons = applyReasons;
  }



  public Short getStatus() {
    return status;
  }



  public void setStatus(Short status) {
    this.status = status;
  }



  public Date getAuditingDate() {
    return auditingDate;
  }



  public void setAuditingDate(Date auditingDate) {
    this.auditingDate = auditingDate;
  }



  public Integer getAuditingId() {
    return auditingId;
  }



  public void setAuditingId(Integer auditingId) {
    this.auditingId = auditingId;
  }



  public String getRemarks() {
    return remarks;
  }



  public void setRemarks(String remarks) {
    this.remarks = remarks;
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



  public String getLoginName() {
    return loginName;
  }



  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }



  public String getUserReal() {
    return userReal;
  }



  public void setUserReal(String userReal) {
    this.userReal = userReal;
  }



  public Date getApplyStartDate() {
    return applyStartDate;
  }



  public void setApplyStartDate(Date applyStartDate) {
    this.applyStartDate = applyStartDate;
  }



  public Date getApplyEndDate() {
    return applyEndDate;
  }



  public void setApplyEndDate(Date applyEndDate) {
    this.applyEndDate = applyEndDate;
  }



}
