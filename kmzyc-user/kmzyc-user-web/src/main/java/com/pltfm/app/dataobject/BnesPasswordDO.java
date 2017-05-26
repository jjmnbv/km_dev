package com.pltfm.app.dataobject;



import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-26
 */
public class BnesPasswordDO implements Serializable {

  private static final long serialVersionUID = 137482553744948663L;

  /**
   * column BNES_PASSWORD.ACCOUNT_ID
   */
  private Integer accountId;

  /**
   * column BNES_PASSWORD.USER_ID
   */
  private Integer userId;

  /**
   * column BNES_PASSWORD.CREATE_DATE
   */
  private Date createDate;

  /**
   * column BNES_PASSWORD.CODE
   */
  private String code;

  /**
   * column BNES_PASSWORD.FAIL_DATE
   */
  private Date failDate;

  /**
   * column BNES_PASSWORD.CREATED_ID
   */
  private Integer createdId;

  /**
   * column BNES_PASSWORD.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * column BNES_PASSWORD.MODIFIE_ID
   */
  private Integer modifieId;

  public BnesPasswordDO() {
    super();
  }

  public BnesPasswordDO(Integer accountId, Integer userId, Date createDate, String code,
      Date failDate, Integer createdId, Date modifyDate, Integer modifieId) {
    this.accountId = accountId;
    this.userId = userId;
    this.createDate = createDate;
    this.code = code;
    this.failDate = failDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
  }

  /**
   * getter for Column BNES_PASSWORD.ACCOUNT_ID
   */
  public Integer getAccountId() {
    return accountId;
  }

  /**
   * setter for Column BNES_PASSWORD.ACCOUNT_ID
   * 
   * @param accountId
   */
  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  /**
   * getter for Column BNES_PASSWORD.USER_ID
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * setter for Column BNES_PASSWORD.USER_ID
   * 
   * @param userId
   */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /**
   * getter for Column BNES_PASSWORD.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column BNES_PASSWORD.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column BNES_PASSWORD.CODE
   */
  public String getCode() {
    return code;
  }

  /**
   * setter for Column BNES_PASSWORD.CODE
   * 
   * @param code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * getter for Column BNES_PASSWORD.FAIL_DATE
   */
  public Date getFailDate() {
    return failDate;
  }

  /**
   * setter for Column BNES_PASSWORD.FAIL_DATE
   * 
   * @param failDate
   */
  public void setFailDate(Date failDate) {
    this.failDate = failDate;
  }

  /**
   * getter for Column BNES_PASSWORD.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column BNES_PASSWORD.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column BNES_PASSWORD.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column BNES_PASSWORD.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column BNES_PASSWORD.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column BNES_PASSWORD.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

}
