package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReserverApplyInfo implements Serializable {

  private static final long serialVersionUID = 4007395265278913241L;
  /** 预备金申请Id */
  private BigDecimal applyNotesId;
  /** 预备金账户Id */
  private BigDecimal reserveId;
  /** 用户登陆Id */
  private BigDecimal loginId;
  /** 申请类型 1开通 2变更 */
  private Short applyType;
  /** 申请额度 */
  private BigDecimal applyLimit;
  /** 原本额度 */
  private BigDecimal originalLimit;
  /** 结算周期 1按月2季度3半年4年 */
  private Short settlementType;
  /** 联系人 */
  private String contact;
  /** 联系方式 */
  private String phone;
  /** 申请原因用途描述 */
  private String description;
  /** 申请时间 */
  private Date applyDate;
  /** 审核时间 */
  private Date auditingDate;
  /** 审核状态1待审核2审核通过3审核不通过 */
  private Short status;
  /** 审核人 */
  private BigDecimal auditingId;
  /** 审核备注描述 */
  private String auditingRemark;
  /** 开始时间 */
  private Date startDate;
  /** 结束时间 */
  private Date endDate;
  /** 分页索引最小值 */
  private Integer minNum;
  /** 分页索引最大值 */
  private Integer maxNum;
  /** 用户名 */
  private String accountLogin;
  /** 公司名 */
  private String corporateName;
  /** 邮箱 */
  private String email;


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public BigDecimal getApplyNotesId() {
    return applyNotesId;
  }

  public void setApplyNotesId(BigDecimal applyNotesId) {
    this.applyNotesId = applyNotesId;
  }

  public BigDecimal getReserveId() {
    return reserveId;
  }

  public void setReserveId(BigDecimal reserveId) {
    this.reserveId = reserveId;
  }

  public BigDecimal getLoginId() {
    return loginId;
  }

  public void setLoginId(BigDecimal loginId) {
    this.loginId = loginId;
  }

  public Short getApplyType() {
    return applyType;
  }

  public void setApplyType(Short applyType) {
    this.applyType = applyType;
  }

  public BigDecimal getApplyLimit() {
    return applyLimit;
  }

  public void setApplyLimit(BigDecimal applyLimit) {
    this.applyLimit = applyLimit;
  }

  public BigDecimal getOriginalLimit() {
    if (originalLimit == null) {
      originalLimit = new BigDecimal(0.00);
    }
    return originalLimit;
  }

  public void setOriginalLimit(BigDecimal originalLimit) {
    this.originalLimit = originalLimit;
  }

  public Short getSettlementType() {
    return settlementType;
  }

  public void setSettlementType(Short settlementType) {
    this.settlementType = settlementType;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getApplyDate() {
    return applyDate;
  }

  public void setApplyDate(Date applyDate) {
    this.applyDate = applyDate;
  }

  public Date getAuditingDate() {
    return auditingDate;
  }

  public void setAuditingDate(Date auditingDate) {
    this.auditingDate = auditingDate;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public BigDecimal getAuditingId() {
    return auditingId;
  }

  public void setAuditingId(BigDecimal auditingId) {
    this.auditingId = auditingId;
  }

  public String getAuditingRemark() {
    return auditingRemark;
  }

  public void setAuditingRemark(String auditingRemark) {
    this.auditingRemark = auditingRemark;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Integer getMinNum() {
    return minNum;
  }

  public void setMinNum(Integer minNum) {
    this.minNum = minNum;
  }

  public Integer getMaxNum() {
    return maxNum;
  }

  public void setMaxNum(Integer maxNum) {
    this.maxNum = maxNum;
  }

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  @Override
  public String toString() {
    return "ReserverApplyInfo [applyNotesId=" + applyNotesId + ", reserveId=" + reserveId
        + ", loginId=" + loginId + ", applyType=" + applyType + ", applyLimit=" + applyLimit
        + ", originalLimit=" + originalLimit + ", settlementType=" + settlementType + ", contact="
        + contact + ", phone=" + phone + ", description=" + description + ", applyDate=" + applyDate
        + ", auditingDate=" + auditingDate + ", status=" + status + ", auditingId=" + auditingId
        + ", auditingRemark=" + auditingRemark + ", startDate=" + startDate + ", endDate=" + endDate
        + ", minNum=" + minNum + ", maxNum=" + maxNum + ", accountLogin=" + accountLogin
        + ", corporateName=" + corporateName + ", email=" + email + "]";
  }



}
