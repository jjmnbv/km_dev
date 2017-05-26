package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-11
 */
public class TransactionListQuery implements Serializable {

  private static final long serialVersionUID = 137350466091196007L;

  /**
   * column TRANSACTION_LIST.N_TRANSACTION_LIST_ID
   */
  private BigDecimal nTransactionListId;

  /**
   * column TRANSACTION_LIST.N_ACCOUNT_ID
   */
  private BigDecimal nAccountId;

  /**
   * column TRANSACTION_LIST.N_ACCOUNT_TRANSACTION_ID
   */
  private BigDecimal nAccountTransactionId;

  /**
   * column TRANSACTION_LIST.N_BEFORE_AMOUNT
   */
  private BigDecimal nBeforeAmount;

  /**
   * column TRANSACTION_LIST.N_AFTER_AMOUNT
   */
  private BigDecimal nAfterAmount;

  /**
   * column TRANSACTION_LIST.N_MONEY
   */
  private BigDecimal nMoney;

  /**
   * column TRANSACTION_LIST.D_MODIFIDE
   */
  private Date dModifide;

  /**
   * column TRANSACTION_LIST.D_CREATE_DATE
   */
  private Date dCreateDate;

  /**
   * column TRANSACTION_LIST.MODIFIED
   */
  private BigDecimal modified;

  /**
   * column TRANSACTION_LIST.CREADED
   */
  private BigDecimal creaded;

  public TransactionListQuery() {
    super();
  }

  public TransactionListQuery(BigDecimal nTransactionListId, BigDecimal nAccountId,
      BigDecimal nAccountTransactionId, BigDecimal nBeforeAmount, BigDecimal nAfterAmount,
      BigDecimal nMoney, Date dModifide, Date dCreateDate, BigDecimal modified,
      BigDecimal creaded) {
    this.nTransactionListId = nTransactionListId;
    this.nAccountId = nAccountId;
    this.nAccountTransactionId = nAccountTransactionId;
    this.nBeforeAmount = nBeforeAmount;
    this.nAfterAmount = nAfterAmount;
    this.nMoney = nMoney;
    this.dModifide = dModifide;
    this.dCreateDate = dCreateDate;
    this.modified = modified;
    this.creaded = creaded;
  }

  /**
   * getter for Column TRANSACTION_LIST.N_TRANSACTION_LIST_ID
   */
  public BigDecimal getnTransactionListId() {
    return nTransactionListId;
  }

  /**
   * setter for Column TRANSACTION_LIST.N_TRANSACTION_LIST_ID
   * 
   * @param nTransactionListId
   */
  public void setnTransactionListId(BigDecimal nTransactionListId) {
    this.nTransactionListId = nTransactionListId;
  }

  /**
   * getter for Column TRANSACTION_LIST.N_ACCOUNT_ID
   */
  public BigDecimal getnAccountId() {
    return nAccountId;
  }

  /**
   * setter for Column TRANSACTION_LIST.N_ACCOUNT_ID
   * 
   * @param nAccountId
   */
  public void setnAccountId(BigDecimal nAccountId) {
    this.nAccountId = nAccountId;
  }

  /**
   * getter for Column TRANSACTION_LIST.N_ACCOUNT_TRANSACTION_ID
   */
  public BigDecimal getnAccountTransactionId() {
    return nAccountTransactionId;
  }

  /**
   * setter for Column TRANSACTION_LIST.N_ACCOUNT_TRANSACTION_ID
   * 
   * @param nAccountTransactionId
   */
  public void setnAccountTransactionId(BigDecimal nAccountTransactionId) {
    this.nAccountTransactionId = nAccountTransactionId;
  }

  /**
   * getter for Column TRANSACTION_LIST.N_BEFORE_AMOUNT
   */
  public BigDecimal getnBeforeAmount() {
    return nBeforeAmount;
  }

  /**
   * setter for Column TRANSACTION_LIST.N_BEFORE_AMOUNT
   * 
   * @param nBeforeAmount
   */
  public void setnBeforeAmount(BigDecimal nBeforeAmount) {
    this.nBeforeAmount = nBeforeAmount;
  }

  /**
   * getter for Column TRANSACTION_LIST.N_AFTER_AMOUNT
   */
  public BigDecimal getnAfterAmount() {
    return nAfterAmount;
  }

  /**
   * setter for Column TRANSACTION_LIST.N_AFTER_AMOUNT
   * 
   * @param nAfterAmount
   */
  public void setnAfterAmount(BigDecimal nAfterAmount) {
    this.nAfterAmount = nAfterAmount;
  }

  /**
   * getter for Column TRANSACTION_LIST.N_MONEY
   */
  public BigDecimal getnMoney() {
    return nMoney;
  }

  /**
   * setter for Column TRANSACTION_LIST.N_MONEY
   * 
   * @param nMoney
   */
  public void setnMoney(BigDecimal nMoney) {
    this.nMoney = nMoney;
  }

  /**
   * getter for Column TRANSACTION_LIST.D_MODIFIDE
   */
  public Date getdModifide() {
    return dModifide;
  }

  /**
   * setter for Column TRANSACTION_LIST.D_MODIFIDE
   * 
   * @param dModifide
   */
  public void setdModifide(Date dModifide) {
    this.dModifide = dModifide;
  }

  /**
   * getter for Column TRANSACTION_LIST.D_CREATE_DATE
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * setter for Column TRANSACTION_LIST.D_CREATE_DATE
   * 
   * @param dCreateDate
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * getter for Column TRANSACTION_LIST.MODIFIED
   */
  public BigDecimal getModified() {
    return modified;
  }

  /**
   * setter for Column TRANSACTION_LIST.MODIFIED
   * 
   * @param modified
   */
  public void setModified(BigDecimal modified) {
    this.modified = modified;
  }

  /**
   * getter for Column TRANSACTION_LIST.CREADED
   */
  public BigDecimal getCreaded() {
    return creaded;
  }

  /**
   * setter for Column TRANSACTION_LIST.CREADED
   * 
   * @param creaded
   */
  public void setCreaded(BigDecimal creaded) {
    this.creaded = creaded;
  }

}
