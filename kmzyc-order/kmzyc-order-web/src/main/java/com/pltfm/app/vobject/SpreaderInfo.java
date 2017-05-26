package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 微商业务实体类
 * 
 * @author lijainjun
 */
public class SpreaderInfo implements Serializable {
  private static final long serialVersionUID = 7291178886682966276L;

  // 微商基本信息Id
  private BigDecimal sid;
  // 微商号
  private BigDecimal vsNumber;
  // 会员ID
  private BigDecimal loginId;
  // 引荐人Id
  private BigDecimal recommenders;
  // 所属机构
  private String affiliation;
  // 微信账号
  private String vxAccount;
  // 银行账号
  private String bankAccount;
  // 开户行
  private String bankName;
  // 银行账户名
  private String bankUname;
  // 创建时间
  private Date createTime;
  // 审核备注
  private String auditRemark;
  // 是否有效,0否，1是
  private Short isValid;
  // 最后修改人
  private BigDecimal lastModifier;
  // 最后更新时间
  private Date lastModifiedDate;

  // 手机
  private String mobile;
  // 邮箱
  private String email;
  // 登录名
  private String loginAccount;
  // 商品名
  private String procuctName;
  // 商品sku
  private String commoditySku;



  public String getCommoditySku() {
    return commoditySku;
  }


  public void setCommoditySku(String commoditySku) {
    this.commoditySku = commoditySku;
  }


  public String getProcuctName() {
    return procuctName;
  }


  public void setProcuctName(String procuctName) {
    this.procuctName = procuctName;
  }


  public String getMobile() {
    return mobile;
  }


  public void setMobile(String mobile) {
    this.mobile = mobile;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public String getLoginAccount() {
    return loginAccount;
  }


  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }


  public BigDecimal getSid() {
    return sid;
  }


  public void setSid(BigDecimal sid) {
    this.sid = sid;
  }

  public BigDecimal getVsNumber() {
    return vsNumber;
  }

  public void setVsNumber(BigDecimal vsNumber) {
    this.vsNumber = vsNumber;
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

  public String getVxAccount() {
    return vxAccount;
  }

  public void setVxAccount(String vxAccount) {
    this.vxAccount = vxAccount;
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


  public Date getCreateTime() {
    return createTime;
  }


  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public String getAuditRemark() {
    return auditRemark;
  }

  public void setAuditRemark(String auditRemark) {
    this.auditRemark = auditRemark;
  }

  public Short getIsValid() {
    return isValid;
  }

  public void setIsValid(Short isValid) {
    this.isValid = isValid;
  }

  public BigDecimal getLastModifier() {
    return lastModifier;
  }

  public void setLastModifier(BigDecimal lastModifier) {
    this.lastModifier = lastModifier;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }


  @Override
  public String toString() {
    return "SpreaderInfo [sid=" + sid + ", vsNumber=" + vsNumber + ", loginId=" + loginId
        + ", recommenders=" + recommenders + ", affiliation=" + affiliation + ", vxAccount="
        + vxAccount + ", bankAccount=" + bankAccount + ", bankName=" + bankName + ", bankUname="
        + bankUname + ", createTime=" + createTime + ", auditRemark=" + auditRemark + ", isValid="
        + isValid + ", lastModifier=" + lastModifier + ", lastModifiedDate=" + lastModifiedDate
        + "]";
  }


}
