package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.Date;

public class ScoreRule implements Serializable {

  private Long nScoreRuleId;

  private String code;

  private String discribe;

  private Long clientType;

  private String scoreexpress;

  private Date dCreateDate;

  private Long nCreated;

  private Date dModifyDate;

  private Long nModified;

  private String remark;

  private Long dayMax;

  private String scoreValue;

  public Long getnScoreRuleId() {
    return nScoreRuleId;
  }

  public void setnScoreRuleId(Long nScoreRuleId) {
    this.nScoreRuleId = nScoreRuleId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDiscribe() {
    return discribe;
  }

  public void setDiscribe(String discribe) {
    this.discribe = discribe;
  }

  public Long getClientType() {
    return clientType;
  }

  public void setClientType(Long clientType) {
    this.clientType = clientType;
  }

  public String getScoreexpress() {
    return scoreexpress;
  }

  public void setScoreexpress(String scoreexpress) {
    this.scoreexpress = scoreexpress;
  }

  public Date getdCreateDate() {
    return dCreateDate;
  }

  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  public Long getnCreated() {
    return nCreated;
  }

  public void setnCreated(Long nCreated) {
    this.nCreated = nCreated;
  }

  public Date getdModifyDate() {
    return dModifyDate;
  }

  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  public Long getnModified() {
    return nModified;
  }

  public void setnModified(Long nModified) {
    this.nModified = nModified;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Long getDayMax() {
    return dayMax;
  }

  public void setDayMax(Long dayMax) {
    this.dayMax = dayMax;
  }

  public String getScoreValue() {
    return scoreValue;
  }

  public void setScoreValue(String scoreValue) {
    this.scoreValue = scoreValue;
  }

}
