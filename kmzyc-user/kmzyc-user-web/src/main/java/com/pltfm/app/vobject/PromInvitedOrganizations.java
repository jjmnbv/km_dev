package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.Date;

public class PromInvitedOrganizations {
  // 邀请机构ID
  private BigDecimal invitedOrganizationsId;
  // 机构码
  private String organiCode;
  // 机构描述
  private String organiDes;
  // 机构创建日期
  private Date createDate;
  // 机构状态
  private String organtState;



  public String getOrgantState() {
    return organtState;
  }

  public void setOrgantState(String organtState) {
    this.organtState = organtState;
  }

  public BigDecimal getInvitedOrganizationsId() {
    return invitedOrganizationsId;
  }

  public void setInvitedOrganizationsId(BigDecimal invitedOrganizationsId) {
    this.invitedOrganizationsId = invitedOrganizationsId;
  }

  public String getOrganiCode() {
    return organiCode;
  }

  public void setOrganiCode(String organiCode) {
    this.organiCode = organiCode;
  }

  public String getOrganiDes() {
    return organiDes;
  }

  public void setOrganiDes(String organiDes) {
    this.organiDes = organiDes;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}
