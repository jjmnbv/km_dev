package com.kmzyc.b2b.vo;

public class ScoreView {
  /**
   * 登录Id
   */
  private Integer loginId;

  /**
   * 当前积分
   */
  private String curScore;

  /**
   * 当前等级
   */
  private String curLevel;

  /**
   * 消费总额
   */
  private String totalConsume;

  /**
   * 再消费多少金额，达到下一级别
   */
  private String nextConsume;

  /**
   * 下一级别的名称
   */
  private String nextLevel;

  /**
   * 用户卡号
   */
  private String cardNum;
  /**
   * 
   */
  private String curLevelCode;
  /**
   * 下一级别的名称
   */
  private Long nextLevelId;

  private String lastYearConsume;

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public String getCurLevelCode() {
    return curLevelCode;
  }

  public void setCurLevelCode(String curLevelCode) {
    this.curLevelCode = curLevelCode;
  }

  public String getCurScore() {
    return curScore;
  }

  public void setCurScore(Integer curScore) {
    this.curScore = String.valueOf(curScore);;
  }

  public String getCurLevel() {
    return curLevel;
  }

  public void setCurLevel(String curLevel) {
    this.curLevel = curLevel;
  }

  public String getTotalConsume() {
    return totalConsume;
  }

  public void setTotalConsume(Double totalConsume) {
    this.totalConsume = String.valueOf(Math.abs(totalConsume));
  }

  public String getNextConsume() {
    return nextConsume;
  }

  public void setNextConsume(Double nextConsume) {
    this.nextConsume = String.valueOf(Math.abs(nextConsume));;
  }

  public String getNextLevel() {
    return nextLevel;
  }

  public void setNextLevel(String nextLevel) {
    this.nextLevel = nextLevel;
  }

  public Long getNextLevelId() {
    return nextLevelId;
  }

  public void setNextLevelId(Long nextLevelId) {
    this.nextLevelId = nextLevelId;
  }

  public String getLastYearConsume() {
    return lastYearConsume;
  }

  public void setLastYearConsume(Double lastYearConsume) {
    this.lastYearConsume = String.valueOf(Math.abs(lastYearConsume));;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

}
