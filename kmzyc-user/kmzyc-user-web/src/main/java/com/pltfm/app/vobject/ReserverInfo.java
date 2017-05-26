package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReserverInfo implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 4538766433815964609L;
  // 预备金账户id
  private BigDecimal reserveId;
  // 预备金用户登陆id
  private BigDecimal loginId;
  // 总信用额度
  private BigDecimal totalLimit;
  // 可用额度
  private BigDecimal remainLimit;
  // 结算周期（1：月结 2：季结 3：半年 4：年结 ）
  private Short payType;
  // 申请原因用途描述
  private String description;
  // 联系人
  private String contact;
  // 联系号码
  private String phone;
  // 开通时间
  private Date openDate;
  // 开始时间
  private Date startDate;
  // 结束时间
  private Date endDate;
  // 是否有效（1：有效 2：停用）
  private Short isAvailable;
  /** 分页索引最小值 */
  private Integer minNum;
  /** 分页索引最大值 */
  private Integer maxNum;
  // 用户名
  private String accountLogin;
  // 注册邮箱
  private String email;
  // 用户登陆id
  private BigDecimal userLoginId;
  // 账户id
  private BigDecimal accountId;
  // 公司名
  private String corporateName;



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

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public BigDecimal getAccountId() {
    return accountId;
  }

  public void setAccountId(BigDecimal accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getUserLoginId() {
    return userLoginId;
  }

  public void setUserLoginId(BigDecimal userLoginId) {
    this.userLoginId = userLoginId;
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

  public BigDecimal getTotalLimit() {
    return totalLimit;
  }

  public void setTotalLimit(BigDecimal totalLimit) {
    this.totalLimit = totalLimit;
  }

  public BigDecimal getRemainLimit() {
    return remainLimit;
  }

  public void setRemainLimit(BigDecimal remainLimit) {
    this.remainLimit = remainLimit;
  }

  public Short getPayType() {
    return payType;
  }

  public void setPayType(Short payType) {
    this.payType = payType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Date getOpenDate() {
    return openDate;
  }

  public void setOpenDate(Date openDate) {
    this.openDate = openDate;
  }

  public Short getIsAvailable() {
    return isAvailable;
  }

  public void setIsAvailable(Short isAvailable) {
    this.isAvailable = isAvailable;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "ReserverInfo [reserveId=" + reserveId + ", loginId=" + loginId + ", totalLimit="
        + totalLimit + ", remainLimit=" + remainLimit + ", payType=" + payType + ", description="
        + description + ", contact=" + contact + ", phone=" + phone + ", openDate=" + openDate
        + ", startDate=" + startDate + ", endDate=" + endDate + ", isAvailable=" + isAvailable
        + ", minNum=" + minNum + ", maxNum=" + maxNum + ", accountLogin=" + accountLogin
        + ", email=" + email + ", userLoginId=" + userLoginId + ", accountId=" + accountId
        + ", corporateName=" + corporateName + "]";
  }



}
