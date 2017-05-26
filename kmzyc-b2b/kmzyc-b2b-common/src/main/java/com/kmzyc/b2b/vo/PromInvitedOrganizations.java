package com.kmzyc.b2b.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 奖励机构表
 * 
 * @author Administrator
 * 
 */
public class PromInvitedOrganizations {

  // 邀请机构ID
  private BigDecimal invitedOrganizationsId;

  // 机构码
  private String organiCode;

  // 机构描述
  private String organiDes;

  // 创建日期
  private Date createDate;

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
