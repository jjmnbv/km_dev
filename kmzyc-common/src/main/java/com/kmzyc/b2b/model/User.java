package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

import com.kmzyc.b2b.vo.EraInfo;

public class User implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private Long loginId;
  private String cardNum;
  private Integer levelId;
  private Integer customerTypeId;
  private String loginAccount;
  private String loginPassword;
  private String mobile;
  private String email;
  private Integer status;
  private Date createDate;
  private Long created;
  private String modifyDate;
  private Integer modified;
  private String nickName;
  private String organCode;
  private String organDes;
  private Long promId;
  private EraInfo earInfo;
  private String headSculpture; // 头像
  private BigDecimal totalConsume; // 当前会员总积分
  private BigDecimal lastYearAmount;// 上年度消费积分
  private String levelName; // 当前会员等级
  private BigDecimal amountAvlibal;// 当前会员可余额
  private BigDecimal availableIntegral;// 当前可用积分
  private String paymentPwd; // 支付密码
  private Long instockSkuAmount;// 现货
  // 注册平台
  private Integer registerPlatfrom;
  // 注册设备
  private Integer registerDevice;

  public String getHeadSculpture() {
    return headSculpture;
  }

  public void setHeadSculpture(String headSculpture) {
    this.headSculpture = headSculpture;
  }

  public BigDecimal getTotalConsume() {
    return totalConsume;
  }

  public void setTotalConsume(BigDecimal totalConsume) {
    this.totalConsume = totalConsume;
  }

  public BigDecimal getLastYearAmount() {
    return lastYearAmount;
  }

  public void setLastYearAmount(BigDecimal lastYearAmount) {
    this.lastYearAmount = lastYearAmount;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }


  public EraInfo getEarInfo() {
    return earInfo;
  }

  public void setEarInfo(EraInfo earInfo) {
    this.earInfo = earInfo;
  }

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

  public Integer getLevelId() {
    return levelId;
  }

  public void setLevelId(Integer levelId) {
    this.levelId = levelId;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public String getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(String modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getModified() {
    return modified;
  }

  public void setModified(Integer modified) {
    this.modified = modified;
  }

  @Override
  public String toString() {
    return "User [loginId=" + loginId + ", levelId=" + levelId + ", customerTypeId="
            + customerTypeId + ", loginAccount=" + loginAccount + ", loginPassword=" + loginPassword
            + ", mobile=" + mobile + ", email=" + email + ", status=" + status + ", createDate="
            + createDate + ", created=" + created + ", modifyDate=" + modifyDate + ", modified="
            + modified + ", cardNum=" + cardNum + "]";
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getOrganCode() {
    return organCode;
  }

  public void setOrganCode(String organCode) {
    this.organCode = organCode;
  }

  public String getOrganDes() {
    return organDes;
  }

  public void setOrganDes(String organDes) {
    this.organDes = organDes;
  }

  public Long getPromId() {
    return promId;
  }

  public void setPromId(Long promId) {
    this.promId = promId;
  }

  public BigDecimal getAmountAvlibal() {
    return amountAvlibal;
  }

  public void setAmountAvlibal(BigDecimal amountAvlibal) {
    this.amountAvlibal = amountAvlibal;
  }

  public BigDecimal getAvailableIntegral() {
    return availableIntegral;
  }

  public void setAvailableIntegral(BigDecimal availableIntegral) {
    this.availableIntegral = availableIntegral;
  }

  public String getPaymentPwd() {
    return paymentPwd;
  }

  public void setPaymentPwd(String paymentPwd) {
    this.paymentPwd = paymentPwd;
  }

  public Long getInstockSkuAmount() {
    return instockSkuAmount;
  }

  public void setInstockSkuAmount(Long instockSkuAmount) {
    this.instockSkuAmount = instockSkuAmount;
  }

  public Integer getRegisterPlatfrom() {
    return registerPlatfrom;
  }

  public void setRegisterPlatfrom(Integer registerPlatfrom) {
    this.registerPlatfrom = registerPlatfrom;
  }

  public Integer getRegisterDevice() {
    return registerDevice;
  }

  public void setRegisterDevice(Integer registerDevice) {
    this.registerDevice = registerDevice;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }


}
