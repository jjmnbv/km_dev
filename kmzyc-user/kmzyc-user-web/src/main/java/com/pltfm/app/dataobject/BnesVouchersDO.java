package com.pltfm.app.dataobject;



import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-26
 */
public class BnesVouchersDO implements Serializable {

  private static final long serialVersionUID = 137482619014077691L;

  /**
   * column BNES_VOUCHERS.VOUCHERS_ID
   */
  private Integer vouchersId;

  /**
   * column BNES_VOUCHERS.ACCOUNT_ID
   */
  private Integer accountId;

  /**
   * column BNES_VOUCHERS.NAME
   */
  private String name;

  /**
   * column BNES_VOUCHERS.VALID
   */
  private Date valid;

  /**
   * column BNES_VOUCHERS.N_NUMBER
   */
  private Integer nNumber;

  /**
   * column BNES_VOUCHERS.CREATE_DATE
   */
  private Date createDate;

  /**
   * column BNES_VOUCHERS.CREATED_ID
   */
  private Integer createdId;

  /**
   * column BNES_VOUCHERS.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * column BNES_VOUCHERS.MODIFIE_ID
   */
  private Integer modifieId;

  public BnesVouchersDO() {
    super();
  }

  public BnesVouchersDO(Integer vouchersId, Integer accountId, String name, Date valid,
      Integer nNumber, Date createDate, Integer createdId, Date modifyDate, Integer modifieId) {
    this.vouchersId = vouchersId;
    this.accountId = accountId;
    this.name = name;
    this.valid = valid;
    this.nNumber = nNumber;
    this.createDate = createDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
  }

  /**
   * getter for Column BNES_VOUCHERS.VOUCHERS_ID
   */
  public Integer getVouchersId() {
    return vouchersId;
  }

  /**
   * setter for Column BNES_VOUCHERS.VOUCHERS_ID
   * 
   * @param vouchersId
   */
  public void setVouchersId(Integer vouchersId) {
    this.vouchersId = vouchersId;
  }

  /**
   * getter for Column BNES_VOUCHERS.ACCOUNT_ID
   */
  public Integer getAccountId() {
    return accountId;
  }

  /**
   * setter for Column BNES_VOUCHERS.ACCOUNT_ID
   * 
   * @param accountId
   */
  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  /**
   * getter for Column BNES_VOUCHERS.NAME
   */
  public String getName() {
    return name;
  }

  /**
   * setter for Column BNES_VOUCHERS.NAME
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * getter for Column BNES_VOUCHERS.VALID
   */
  public Date getValid() {
    return valid;
  }

  /**
   * setter for Column BNES_VOUCHERS.VALID
   * 
   * @param valid
   */
  public void setValid(Date valid) {
    this.valid = valid;
  }

  /**
   * getter for Column BNES_VOUCHERS.N_NUMBER
   */
  public Integer getnNumber() {
    return nNumber;
  }

  /**
   * setter for Column BNES_VOUCHERS.N_NUMBER
   * 
   * @param nNumber
   */
  public void setnNumber(Integer nNumber) {
    this.nNumber = nNumber;
  }

  /**
   * getter for Column BNES_VOUCHERS.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column BNES_VOUCHERS.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column BNES_VOUCHERS.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column BNES_VOUCHERS.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column BNES_VOUCHERS.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column BNES_VOUCHERS.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column BNES_VOUCHERS.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column BNES_VOUCHERS.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

}
