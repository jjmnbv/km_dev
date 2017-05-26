package com.pltfm.app.vobject;



import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-17
 */
public class BnesAcctTransListQuery implements Serializable {

  private static final long serialVersionUID = 137404451955850991L;
  private Integer transListId;
  private Integer accountId;
  private Integer accountTransactionId;
  private double beforeAmount;
  private double afterAmount;
  private double moneyAmount;
  private Date createDate;
  // 添加结束时间
  private Date endDate;
  // 外部单号
  private String otherOrder;
  // 交易对象
  private Integer trasObject;
  private Integer createdId;
  private Date modifyDate;
  private Integer modifieId;

  /**
   * 交易流水号
   */
  private String accountNumber;
  /**
   * 账户号
   */
  private String accountLogin;
  // 交易内容
  private String content;
  private Integer mixNum;

  /**
   * 交易类型 1--充值;2--账户金额修改;3--订单交易 column BNES_ACCT_TRANSACTION.TYPE
   */
  private Integer type;
  /**
   * 最大值
   */
  private Integer maxNum;
  private Integer status;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public Integer getMixNum() {
    return mixNum;
  }

  public void setMixNum(Integer mixNum) {
    this.mixNum = mixNum;
  }

  public Integer getMaxNum() {
    return maxNum;
  }

  public void setMaxNum(Integer maxNum) {
    this.maxNum = maxNum;
  }



  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }



  public Integer getTransListId() {
    return transListId;
  }

  public void setTransListId(Integer transListId) {
    this.transListId = transListId;
  }

  public Integer getAccountId() {
    return accountId;
  }


  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public Integer getAccountTransactionId() {
    return accountTransactionId;
  }

  public void setAccountTransactionId(Integer accountTransactionId) {
    this.accountTransactionId = accountTransactionId;
  }


  public double getBeforeAmount() {
    return beforeAmount;
  }


  public void setBeforeAmount(double beforeAmount) {
    this.beforeAmount = beforeAmount;
  }


  public double getAfterAmount() {
    return afterAmount;
  }


  public void setAfterAmount(double afterAmount) {
    this.afterAmount = afterAmount;
  }

  public double getMoneyAmount() {
    return moneyAmount;
  }


  public void setMoneyAmount(double moneyAmount) {
    this.moneyAmount = moneyAmount;
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

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getOtherOrder() {
    return otherOrder;
  }

  public void setOtherOrder(String otherOrder) {
    this.otherOrder = otherOrder;
  }

  public Integer getTrasObject() {
    return trasObject;
  }

  public void setTrasObject(Integer trasObject) {
    this.trasObject = trasObject;
  }



}
