package com.pltfm.app.vobject;

import java.util.Date;

public class TransactionList implements java.io.Serializable {

  /**
   * 账户金额交易变化信息
   */
  private static final long serialVersionUID = -2978469315180911059L;

  /**
   * 账户金额交易记录id
   */
  private long nTransactionListId;

  /**
   * 账户id
   */
  private long nAccountId;
  /**
   * 交易id
   */
  private long nAccountTransactionId;
  /**
   * 交易之前账户金额
   */
  private double nBeforeAmount;

  /**
   * 交易之后账户金额
   */
  private double nAfterAmount;
  /**
   * 交易金额
   */
  private double nMoney;

  /**
   * 交易创建人
   */
  private long creaded;
  /**
   * 交易创建日期
   */

  private Date dCreateDate;
  /**
   * 交易修改人
   */
  private long modified;
  /**
   * 交易修改日期
   */
  private Date dModifide;

  public long getnTransactionListId() {
    return nTransactionListId;
  }

  public void setnTransactionListId(long nTransactionListId) {
    this.nTransactionListId = nTransactionListId;
  }

  public long getnAccountId() {
    return nAccountId;
  }

  public void setnAccountId(long nAccountId) {
    this.nAccountId = nAccountId;
  }

  public long getnAccountTransactionId() {
    return nAccountTransactionId;
  }

  public void setnAccountTransactionId(long nAccountTransactionId) {
    this.nAccountTransactionId = nAccountTransactionId;
  }

  public double getnBeforeAmount() {
    return nBeforeAmount;
  }

  public void setnBeforeAmount(double nBeforeAmount) {
    this.nBeforeAmount = nBeforeAmount;
  }

  public double getnAfterAmount() {
    return nAfterAmount;
  }

  public void setnAfterAmount(double nAfterAmount) {
    this.nAfterAmount = nAfterAmount;
  }

  public double getnMoney() {
    return nMoney;
  }

  public void setnMoney(double nMoney) {
    this.nMoney = nMoney;
  }

  public long getCreaded() {
    return creaded;
  }

  public void setCreaded(long creaded) {
    this.creaded = creaded;
  }

  public Date getdCreateDate() {
    return dCreateDate;
  }

  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  public long getModified() {
    return modified;
  }

  public void setModified(long modified) {
    this.modified = modified;
  }

  public Date getdModifide() {
    return dModifide;
  }

  public void setdModifide(Date dModifide) {
    this.dModifide = dModifide;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }


}
