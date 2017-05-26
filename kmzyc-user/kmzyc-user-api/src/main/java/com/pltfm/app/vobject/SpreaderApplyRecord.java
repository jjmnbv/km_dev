package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SpreaderApplyRecord implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -2270802424497804906L;
  // 申请ID
  private BigDecimal aid;
  // 会员ID
  private BigDecimal loginId;
  // 引荐人ID
  private BigDecimal recommenders;
  // 所属机构
  private String affiliation;
  // 申请类型,1开通|2修改信息|3重新开通
  private Short applyType;
  // 审核状态,1待审核|2审核通过|3审核拒绝
  private Short auditStatus;
  // 微信账号
  private String vxAccount;
  // 证件类型：0.身份证1.护照2.回乡证3.选择证件类型
  private Short certificateType;
  // 证件号码
  private String certificateNumber;
  // 银行账号
  private String bankAccount;
  // 开户行
  private String bankName;
  // 银行户名
  private String bankUname;
  // 审核时间
  private Date applyTime;
  // 审核备注
  private String auditRemark;
  // 创建时间
  private Date createDate;
  // 真实姓名
  private String name;
  // 银行分之行
  private String branchName;
  // 提现类型
  private BigDecimal enchashmentType;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getAid() {
    return aid;
  }

  public void setAid(BigDecimal aid) {
    this.aid = aid;
  }

  public BigDecimal getLoginId() {
    return loginId;
  }

  public void setLoginId(BigDecimal loginId) {
    this.loginId = loginId;
  }

  public BigDecimal getRecommenders() {
    return recommenders;
  }


  public void setRecommenders(BigDecimal recommenders) {
    this.recommenders = recommenders;
  }

  public String getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(String affiliation) {
    this.affiliation = affiliation;
  }

  public Short getApplyType() {
    return applyType;
  }

  public void setApplyType(Short applyType) {
    this.applyType = applyType;
  }

  public Short getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(Short auditStatus) {
    this.auditStatus = auditStatus;
  }

  public String getVxAccount() {
    return vxAccount;
  }

  public void setVxAccount(String vxAccount) {
    this.vxAccount = vxAccount;
  }

  public Short getCertificateType() {
    return certificateType;
  }

  public void setCertificateType(Short certificateType) {
    this.certificateType = certificateType;
  }

  public String getCertificateNumber() {
    return certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public String getBankAccount() {
    return bankAccount;
  }

  public void setBankAccount(String bankAccount) {
    this.bankAccount = bankAccount;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankUname() {
    return bankUname;
  }

  public void setBankUname(String bankUname) {
    this.bankUname = bankUname;
  }

  public Date getApplyTime() {
    return applyTime;
  }

  public void setApplyTime(Date applyTime) {
    this.applyTime = applyTime;
  }

  public String getAuditRemark() {
    return auditRemark;
  }

  public void setAuditRemark(String auditRemark) {
    this.auditRemark = auditRemark;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    return "SpreaderApplyRecord [aid=" + aid + ", loginId=" + loginId + ", recommenders="
        + recommenders + ", affiliation=" + affiliation + ", applyType=" + applyType
        + ", auditStatus=" + auditStatus + ", vxAccount=" + vxAccount + ", certificateType="
        + certificateType + ", certificateNumber=" + certificateNumber + ", bankAccount="
        + bankAccount + ", bankName=" + bankName + ", bankUname=" + bankUname + ", applyTime="
        + applyTime + ", auditRemark=" + auditRemark + ", createDate=" + createDate + ", name="
        + name + "]";
  }

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public BigDecimal getEnchashmentType() {
    return enchashmentType;
  }

  public void setEnchashmentType(BigDecimal enchashmentType) {
    this.enchashmentType = enchashmentType;
  }



}
