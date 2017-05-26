package com.pltfm.app.vobject;



import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-17
 */
public class BnesResPermissionQuery implements Serializable {

  private static final long serialVersionUID = 137404452603018174L;

  /**
   * column BNES_RES_PERMISSION.RES_PERMISSION_ID
   */
  private Integer resPermissionId;

  /**
   * column BNES_RES_PERMISSION.CUSTOMER_TYPE_ID
   */
  private Integer customerTypeId;

  /**
   * column BNES_RES_PERMISSION.BINES_FUNCTION_ID
   */
  private Integer binesFunctionId;

  /**
   * column BNES_RES_PERMISSION.CREATE_DATE
   */
  private Date createDate;

  /**
   * column BNES_RES_PERMISSION.CREATED_ID
   */
  private Integer createdId;

  /**
   * column BNES_RES_PERMISSION.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * column BNES_RES_PERMISSION.MODIFIE_ID
   */
  private Integer modifieId;

  public BnesResPermissionQuery() {
    super();
  }

  public BnesResPermissionQuery(Integer resPermissionId, Integer customerTypeId,
      Integer binesFunctionId, Date createDate, Integer createdId, Date modifyDate,
      Integer modifieId) {
    this.resPermissionId = resPermissionId;
    this.customerTypeId = customerTypeId;
    this.binesFunctionId = binesFunctionId;
    this.createDate = createDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
  }

  /**
   * getter for Column BNES_RES_PERMISSION.RES_PERMISSION_ID
   */
  public Integer getResPermissionId() {
    return resPermissionId;
  }

  /**
   * setter for Column BNES_RES_PERMISSION.RES_PERMISSION_ID
   * 
   * @param resPermissionId
   */
  public void setResPermissionId(Integer resPermissionId) {
    this.resPermissionId = resPermissionId;
  }

  /**
   * getter for Column BNES_RES_PERMISSION.CUSTOMER_TYPE_ID
   */
  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  /**
   * setter for Column BNES_RES_PERMISSION.CUSTOMER_TYPE_ID
   * 
   * @param customerTypeId
   */
  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  /**
   * getter for Column BNES_RES_PERMISSION.BINES_FUNCTION_ID
   */
  public Integer getBinesFunctionId() {
    return binesFunctionId;
  }

  /**
   * setter for Column BNES_RES_PERMISSION.BINES_FUNCTION_ID
   * 
   * @param binesFunctionId
   */
  public void setBinesFunctionId(Integer binesFunctionId) {
    this.binesFunctionId = binesFunctionId;
  }

  /**
   * getter for Column BNES_RES_PERMISSION.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column BNES_RES_PERMISSION.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column BNES_RES_PERMISSION.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column BNES_RES_PERMISSION.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column BNES_RES_PERMISSION.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column BNES_RES_PERMISSION.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column BNES_RES_PERMISSION.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column BNES_RES_PERMISSION.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

}
