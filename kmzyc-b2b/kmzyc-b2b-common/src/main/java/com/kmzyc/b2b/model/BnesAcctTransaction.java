package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BnesAcctTransaction implements Serializable {
  private static final long serialVersionUID = 3475999346013785817L;

  private BigDecimal accountTransactionId;

  private BigDecimal accountId;

  private String accountNumber;

  private Short type;

  private String content;

  private BigDecimal amount;

  private Short status;

  private Date createDate;

  private BigDecimal createdId;

  private Date modifyDate;

  private BigDecimal modifieId;

  private String tranBank;

  private String loginAccount;

  public BigDecimal getAccountTransactionId() {
    return accountTransactionId;
  }

  public void setAccountTransactionId(BigDecimal accountTransactionId) {
    this.accountTransactionId = accountTransactionId;
  }

  public BigDecimal getAccountId() {
    return accountId;
  }

  public void setAccountId(BigDecimal accountId) {
    this.accountId = accountId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public BigDecimal getCreatedId() {
    return createdId;
  }

  public void setCreatedId(BigDecimal createdId) {
    this.createdId = createdId;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public BigDecimal getModifieId() {
    return modifieId;
  }

  public void setModifieId(BigDecimal modifieId) {
    this.modifieId = modifieId;
  }

  public String getTranBank() {
    return tranBank;
  }

  public void setTranBank(String tranBank) {
    this.tranBank = tranBank;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  @Override
  public String toString() {
    return "BnesAcctTransaction [accountTransactionId=" + accountTransactionId + ", accountId="
        + accountId + ", accountNumber=" + accountNumber + ", type=" + type + ", content="
        + content + ", amount=" + amount + ", status=" + status + ", createDate=" + createDate
        + ", createdId=" + createdId + ", modifyDate=" + modifyDate + ", modifieId=" + modifieId
        + ", tranBank=" + tranBank + "]";
  }

}
