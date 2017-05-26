package com.kmzyc.b2b.vo;

import java.math.BigDecimal;

/**
 * 奖励规则信息表
 * 
 * @author houlin
 * 
 */
public class PromRewardRule {

  // 奖励规则ID
  private BigDecimal rewardRuleId;

  // 规则码
  private String ruleCode;

  // 奖励规则描述
  private String ruleDescription;

  // 奖励金额
  private BigDecimal rewardAmount;

  public BigDecimal getRewardRuleId() {
    return rewardRuleId;
  }

  public void setRewardRuleId(BigDecimal rewardRuleId) {
    this.rewardRuleId = rewardRuleId;
  }

  public String getRuleCode() {
    return ruleCode;
  }

  public void setRuleCode(String ruleCode) {
    this.ruleCode = ruleCode;
  }

  public String getRuleDescription() {
    return ruleDescription;
  }

  public void setRuleDescription(String ruleDescription) {
    this.ruleDescription = ruleDescription;
  }

  public BigDecimal getRewardAmount() {
    return rewardAmount;
  }

  public void setRewardAmount(BigDecimal rewardAmount) {
    this.rewardAmount = rewardAmount;
  }
}
