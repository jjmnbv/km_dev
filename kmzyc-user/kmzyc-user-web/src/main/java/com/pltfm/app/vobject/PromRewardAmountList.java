package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.Date;

public class PromRewardAmountList {
  // 奖励金额记录ID
  private BigDecimal rewardAmountListId;
  // 会员邀请数据记录ID
  private BigDecimal membersInvitationListId;
  // 奖励规则ID
  private BigDecimal rewardRuleId;
  // 奖励记录金额
  private BigDecimal rewardAmountList;
  // 创建时间
  private Date createDate;


  public BigDecimal getRewardAmountListId() {
    return rewardAmountListId;
  }

  public void setRewardAmountListId(BigDecimal rewardAmountListId) {
    this.rewardAmountListId = rewardAmountListId;
  }

  public BigDecimal getMembersInvitationListId() {
    return membersInvitationListId;
  }

  public void setMembersInvitationListId(BigDecimal membersInvitationListId) {
    this.membersInvitationListId = membersInvitationListId;
  }

  public BigDecimal getRewardRuleId() {
    return rewardRuleId;
  }

  public void setRewardRuleId(BigDecimal rewardRuleId) {
    this.rewardRuleId = rewardRuleId;
  }

  public BigDecimal getRewardAmountList() {
    return rewardAmountList;
  }

  public void setRewardAmountList(BigDecimal rewardAmountList) {
    this.rewardAmountList = rewardAmountList;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}
