package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.Date;

public class PromMembersInvitationList {
  // 会员邀请记录ID
  private BigDecimal membersInvitationListId;
  // 邀请机构ID
  private BigDecimal invitedOrganizationsId;
  // 邀请者登陆ID
  private BigDecimal invitationId;
  // 受邀者登陆ID
  private BigDecimal inviteesId;
  // 验证手机号码
  private String verificationMobile;
  // 创建日期
  private Date createDate;

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
