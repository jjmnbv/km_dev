package com.pltfm.app.vobject;

import java.util.Date;

public class BnesAcctHandleComplaints {

  private Integer handleComplaintsId;

  // 账户ID
  private Integer accountId;

  // 处理人
  private Integer disposePersonId;
  // 账户申诉ID
  private Integer accountAppealId;


  private String appealSuggestion;


  private Date disposeDate;


  private Date createDate;


  private Integer createdId;


  private Date modifyDate;


  private Integer modifieId;


  public Integer getHandleComplaintsId() {
    return handleComplaintsId;
  }


  public void setHandleComplaintsId(Integer handleComplaintsId) {
    this.handleComplaintsId = handleComplaintsId;
  }


  public Integer getAccountId() {
    return accountId;
  }


  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }


  public Integer getDisposePersonId() {
    return disposePersonId;
  }


  public void setDisposePersonId(Integer disposePersonId) {
    this.disposePersonId = disposePersonId;
  }


  public Integer getAccountAppealId() {
    return accountAppealId;
  }


  public void setAccountAppealId(Integer accountAppealId) {
    this.accountAppealId = accountAppealId;
  }


  public String getAppealSuggestion() {
    return appealSuggestion;
  }


  public void setAppealSuggestion(String appealSuggestion) {
    this.appealSuggestion = appealSuggestion;
  }


  public Date getDisposeDate() {
    return disposeDate;
  }


  public void setDisposeDate(Date disposeDate) {
    this.disposeDate = disposeDate;
  }


  public Date getCreateDate() {
    return createDate;
  }


  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }


  public Integer getCreatedId() {
    return createdId;
  }


  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }


  public Date getModifyDate() {
    return modifyDate;
  }


  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }


  public Integer getModifieId() {
    return modifieId;
  }


  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }
}
