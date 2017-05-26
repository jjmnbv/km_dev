package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

public class PersonalityInfo {
  private Long personalityId;

  private Long loginId;

  private Long rankId;

  private String nickname;

  private String personalityAutograph;

  private String character;

  private String headSculpture;

  private String interest;

  private String microblogAddress;

  private String qqNumber;

  private BigDecimal individualCreditValue;

  private BigDecimal empiricalValue;

  private Date createDate;

  private Long created;

  private Date modifyDate;

  private Long modified;

  private BigDecimal amountConsume;

  private Long totalIntegral;

  private Long availableIntegral;

  private BigDecimal lastYearAmount;

  public Long getPersonalityId() {
    return personalityId;
  }

  public void setPersonalityId(Long personalityId) {
    this.personalityId = personalityId;
  }

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

  public Long getRankId() {
    return rankId;
  }

  public void setRankId(Long rankId) {
    this.rankId = rankId;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getPersonalityAutograph() {
    return personalityAutograph;
  }

  public void setPersonalityAutograph(String personalityAutograph) {
    this.personalityAutograph = personalityAutograph;
  }

  public String getCharacter() {
    return character;
  }

  public void setCharacter(String character) {
    this.character = character;
  }

  public String getHeadSculpture() {
    return headSculpture;
  }

  public void setHeadSculpture(String headSculpture) {
    this.headSculpture = headSculpture;
  }

  public String getInterest() {
    return interest;
  }

  public void setInterest(String interest) {
    this.interest = interest;
  }

  public String getMicroblogAddress() {
    return microblogAddress;
  }

  public void setMicroblogAddress(String microblogAddress) {
    this.microblogAddress = microblogAddress;
  }

  public String getQqNumber() {
    return qqNumber;
  }

  public void setQqNumber(String qqNumber) {
    this.qqNumber = qqNumber;
  }

  public BigDecimal getIndividualCreditValue() {
    return individualCreditValue;
  }

  public void setIndividualCreditValue(BigDecimal individualCreditValue) {
    this.individualCreditValue = individualCreditValue;
  }

  public BigDecimal getEmpiricalValue() {
    return empiricalValue;
  }

  public void setEmpiricalValue(BigDecimal empiricalValue) {
    this.empiricalValue = empiricalValue;
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

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Long getModified() {
    return modified;
  }

  public void setModified(Long modified) {
    this.modified = modified;
  }

  public BigDecimal getAmountConsume() {
    return amountConsume;
  }

  public void setAmountConsume(BigDecimal amountConsume) {
    this.amountConsume = amountConsume;
  }

  public Long getTotalIntegral() {
    return totalIntegral;
  }

  public void setTotalIntegral(Long totalIntegral) {
    this.totalIntegral = totalIntegral;
  }

  public Long getAvailableIntegral() {
    return availableIntegral;
  }

  public void setAvailableIntegral(Long availableIntegral) {
    this.availableIntegral = availableIntegral;
  }

  public BigDecimal getLastYearAmount() {
    return lastYearAmount;
  }

  public void setLastYearAmount(BigDecimal lastYearAmount) {
    this.lastYearAmount = lastYearAmount;
  }

  @Override
  public String toString() {
    return "PersonalityInfo{" + "personalityId=" + personalityId + ", loginId=" + loginId
        + ", rankId=" + rankId + ", nickname='" + nickname + '\'' + ", personalityAutograph='"
        + personalityAutograph + '\'' + ", character='" + character + '\'' + ", headSculpture='"
        + headSculpture + '\'' + ", interest='" + interest + '\'' + ", microblogAddress='"
        + microblogAddress + '\'' + ", qqNumber='" + qqNumber + '\'' + ", individualCreditValue="
        + individualCreditValue + ", empiricalValue=" + empiricalValue + '}';
  }
}
