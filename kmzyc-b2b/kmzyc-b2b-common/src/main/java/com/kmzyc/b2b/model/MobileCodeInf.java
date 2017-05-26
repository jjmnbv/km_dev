package com.kmzyc.b2b.model;

import java.util.Date;

public class MobileCodeInf {

  private Integer nCellPhoneTattedCodeId;

  private Integer nAccountId;

  private String tattedCode;

  private String lastSendTattedcodeTime;

  private Integer nFailureTimeValue;

  private Date dCreateDate;

  private Integer nCreated;

  private Date dModifyDate;

  private Integer nModified;

  private Integer nLoginId;

  private Short isStatus;

  private String mobile;

  public Integer getnCellPhoneTattedCodeId() {
    return nCellPhoneTattedCodeId;
  }

  public void setnCellPhoneTattedCodeId(Integer nCellPhoneTattedCodeId) {
    this.nCellPhoneTattedCodeId = nCellPhoneTattedCodeId;
  }

  public Integer getnAccountId() {
    return nAccountId;
  }

  public void setnAccountId(Integer nAccountId) {
    this.nAccountId = nAccountId;
  }

  public String getTattedCode() {
    return tattedCode;
  }

  public void setTattedCode(String tattedCode) {
    this.tattedCode = tattedCode;
  }

  public String getLastSendTattedcodeTime() {
    return lastSendTattedcodeTime;
  }

  public void setLastSendTattedcodeTime(String lastSendTattedcodeTime) {
    this.lastSendTattedcodeTime = lastSendTattedcodeTime;
  }

  public Integer getnFailureTimeValue() {
    return nFailureTimeValue;
  }

  public void setnFailureTimeValue(Integer nFailureTimeValue) {
    this.nFailureTimeValue = nFailureTimeValue;
  }

  public Date getdCreateDate() {
    return dCreateDate;
  }

  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  public Integer getnCreated() {
    return nCreated;
  }

  public void setnCreated(Integer nCreated) {
    this.nCreated = nCreated;
  }

  public Date getdModifyDate() {
    return dModifyDate;
  }

  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  public Integer getnModified() {
    return nModified;
  }

  public void setnModified(Integer nModified) {
    this.nModified = nModified;
  }

  public Integer getnLoginId() {
    return nLoginId;
  }

  public void setnLoginId(Integer nLoginId) {
    this.nLoginId = nLoginId;
  }

  public Short getIsStatus() {
    return isStatus;
  }

  public void setIsStatus(Short isStatus) {
    this.isStatus = isStatus;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

}
