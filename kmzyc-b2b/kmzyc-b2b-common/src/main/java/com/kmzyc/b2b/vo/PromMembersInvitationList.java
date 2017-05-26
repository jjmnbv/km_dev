package com.kmzyc.b2b.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员邀请记录表
 * 
 * @author Administrator
 * 
 */
public class PromMembersInvitationList {

  // 会员邀请数据记录ID
  private BigDecimal membersInvitationListId;

  // 邀请机构ID
  private BigDecimal invitedOrganizationsId;

  // 邀请者ID--登录账号ID
  private BigDecimal invitationId;

  // 受邀者ID--登录账号ID
  private BigDecimal inviteesId;

  // 验证手机号码
  private String verificationMobile;

  // 发生时间
  private Date createDate;

  // 注册奖励
  private BigDecimal registMoney;

  // 手机注册
  private BigDecimal telRegistMoney;

  // 首次购物
  private BigDecimal fristByMoney;

  // 小计
  private BigDecimal amount;

  private String accountLogin;

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getRegistMoney() {
    return registMoney;
  }

  public void setRegistMoney(BigDecimal registMoney) {
    this.registMoney = registMoney;
  }

  public BigDecimal getTelRegistMoney() {
    return telRegistMoney;
  }

  public void setTelRegistMoney(BigDecimal telRegistMoney) {
    this.telRegistMoney = telRegistMoney;
  }

  public BigDecimal getFristByMoney() {
    return fristByMoney;
  }

  public void setFristByMoney(BigDecimal fristByMoney) {
    this.fristByMoney = fristByMoney;
  }

  public BigDecimal getMembersInvitationListId() {
    return membersInvitationListId;
  }

  public void setMembersInvitationListId(BigDecimal membersInvitationListId) {
    this.membersInvitationListId = membersInvitationListId;
  }

  public BigDecimal getInvitedOrganizationsId() {
    return invitedOrganizationsId;
  }

  public void setInvitedOrganizationsId(BigDecimal invitedOrganizationsId) {
    this.invitedOrganizationsId = invitedOrganizationsId;
  }

  public BigDecimal getInvitationId() {
    return invitationId;
  }

  public void setInvitationId(BigDecimal invitationId) {
    this.invitationId = invitationId;
  }

  public BigDecimal getInviteesId() {
    return inviteesId;
  }

  public void setInviteesId(BigDecimal inviteesId) {
    this.inviteesId = inviteesId;
  }

  public String getVerificationMobile() {
    return verificationMobile;
  }

  public void setVerificationMobile(String verificationMobile) {
    this.verificationMobile = verificationMobile;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}
