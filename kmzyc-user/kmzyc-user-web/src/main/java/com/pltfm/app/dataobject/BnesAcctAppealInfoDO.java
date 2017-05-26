package com.pltfm.app.dataobject;



import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-26
 */
public class BnesAcctAppealInfoDO implements Serializable {



  /**
   * 
   */
  private static final long serialVersionUID = -1641811032740917525L;

  /**
   * column BNES_ACCT_APPEAL_INFO.ACCOUNT_APPEAL_ID
   */
  private Integer accountAppealId;

  /**
   * column BNES_ACCT_APPEAL_INFO.ACCOUNT_ID
   */
  private Integer accountId;

  /**
   * column BNES_ACCT_APPEAL_INFO.DISPOSE_PERSON
   */
  private Integer disposePerson;

  /**
   * column BNES_ACCT_APPEAL_INFO.APPEAL_TITLE
   */
  private String appealTitle;

  /**
   * column BNES_ACCT_APPEAL_INFO.APPEAL_CONTENT
   */
  private String appealContent;

  /**
   * column BNES_ACCT_APPEAL_INFO.APPEAL_CREATE_DATE
   */
  private Date appealCreateDate;

  /**
   * column BNES_ACCT_APPEAL_INFO.APPEAL_DISPOSE_SUGGESTION
   */
  private String appealDisposeSuggestion;

  /**
   * column BNES_ACCT_APPEAL_INFO.DISPOSE_DATE
   */
  private Date disposeDate;

  /**
   * column BNES_ACCT_APPEAL_INFO.CREATE_DATE
   */
  private Date createDate;

  /**
   * column BNES_ACCT_APPEAL_INFO.CREATED_ID
   */
  private Integer createdId;

  /**
   * column BNES_ACCT_APPEAL_INFO.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * column BNES_ACCT_APPEAL_INFO.MODIFIE_ID
   */
  private Integer modifieId;

  public BnesAcctAppealInfoDO() {
    super();
  }

  public BnesAcctAppealInfoDO(Integer accountAppealId, Integer accountId, Integer disposePerson,
      String appealTitle, String appealContent, Date appealCreateDate,
      String appealDisposeSuggestion, Date disposeDate, Date createDate, Integer createdId,
      Date modifyDate, Integer modifieId) {
    this.accountAppealId = accountAppealId;
    this.accountId = accountId;
    this.disposePerson = disposePerson;
    this.appealTitle = appealTitle;
    this.appealContent = appealContent;
    this.appealCreateDate = appealCreateDate;
    this.appealDisposeSuggestion = appealDisposeSuggestion;
    this.disposeDate = disposeDate;
    this.createDate = createDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.ACCOUNT_APPEAL_ID
   */
  public Integer getAccountAppealId() {
    return accountAppealId;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.ACCOUNT_APPEAL_ID
   * 
   * @param accountAppealId
   */
  public void setAccountAppealId(Integer accountAppealId) {
    this.accountAppealId = accountAppealId;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.ACCOUNT_ID
   */
  public Integer getAccountId() {
    return accountId;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.ACCOUNT_ID
   * 
   * @param accountId
   */
  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.DISPOSE_PERSON
   */
  public Integer getDisposePerson() {
    return disposePerson;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.DISPOSE_PERSON
   * 
   * @param disposePerson
   */
  public void setDisposePerson(Integer disposePerson) {
    this.disposePerson = disposePerson;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.APPEAL_TITLE
   */
  public String getAppealTitle() {
    return appealTitle;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.APPEAL_TITLE
   * 
   * @param appealTitle
   */
  public void setAppealTitle(String appealTitle) {
    this.appealTitle = appealTitle;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.APPEAL_CONTENT
   */
  public String getAppealContent() {
    return appealContent;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.APPEAL_CONTENT
   * 
   * @param appealContent
   */
  public void setAppealContent(String appealContent) {
    this.appealContent = appealContent;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.APPEAL_CREATE_DATE
   */
  public Date getAppealCreateDate() {
    return appealCreateDate;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.APPEAL_CREATE_DATE
   * 
   * @param appealCreateDate
   */
  public void setAppealCreateDate(Date appealCreateDate) {
    this.appealCreateDate = appealCreateDate;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.APPEAL_DISPOSE_SUGGESTION
   */
  public String getAppealDisposeSuggestion() {
    return appealDisposeSuggestion;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.APPEAL_DISPOSE_SUGGESTION
   * 
   * @param appealDisposeSuggestion
   */
  public void setAppealDisposeSuggestion(String appealDisposeSuggestion) {
    this.appealDisposeSuggestion = appealDisposeSuggestion;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.DISPOSE_DATE
   */
  public Date getDisposeDate() {
    return disposeDate;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.DISPOSE_DATE
   * 
   * @param disposeDate
   */
  public void setDisposeDate(Date disposeDate) {
    this.disposeDate = disposeDate;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column BNES_ACCT_APPEAL_INFO.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column BNES_ACCT_APPEAL_INFO.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

}
