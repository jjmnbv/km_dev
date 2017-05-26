package com.kmzyc.b2b.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author 奖励信息表
 * 
 */
public class PromRewardAmountList {

  private BigDecimal rewardAmountListId;

  private BigDecimal membersInvitationListId;

  private BigDecimal rewardRuleId;

  private BigDecimal rewardAmountList;

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
