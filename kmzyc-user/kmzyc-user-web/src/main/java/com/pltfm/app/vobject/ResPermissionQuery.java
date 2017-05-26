package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-11
 */
public class ResPermissionQuery implements Serializable {

  private static final long serialVersionUID = 137352416715092802L;

  /**
   * column RES_PERMISSION.N_RES_PERMISSION_ID
   */
  private BigDecimal nResPermissionId;

  /**
   * column RES_PERMISSION.N_CUSTOMER_TYPE_ID
   */
  private BigDecimal nCustomerTypeId;

  /**
   * column RES_PERMISSION.N_BUSINESS_FUNCTION_ID
   */
  private BigDecimal nBusinessFunctionId;

  /**
   * column RES_PERMISSION.D_CREATE_DATE
   */
  private Date dCreateDate;

  /**
   * column RES_PERMISSION.N_CREATED
   */
  private BigDecimal nCreated;

  /**
   * column RES_PERMISSION.N_MODIFIED
   */
  private BigDecimal nModified;

  /**
   * column RES_PERMISSION.D_MODIFIED_DATE
   */
  private Date dModifiedDate;

  public ResPermissionQuery() {
    super();
  }

  public ResPermissionQuery(BigDecimal nResPermissionId, BigDecimal nCustomerTypeId,
      BigDecimal nBusinessFunctionId, Date dCreateDate, BigDecimal nCreated, BigDecimal nModified,
      Date dModifiedDate) {
    this.nResPermissionId = nResPermissionId;
    this.nCustomerTypeId = nCustomerTypeId;
    this.nBusinessFunctionId = nBusinessFunctionId;
    this.dCreateDate = dCreateDate;
    this.nCreated = nCreated;
    this.nModified = nModified;
    this.dModifiedDate = dModifiedDate;
  }

  /**
   * getter for Column RES_PERMISSION.N_RES_PERMISSION_ID
   */
  public BigDecimal getnResPermissionId() {
    return nResPermissionId;
  }

  /**
   * setter for Column RES_PERMISSION.N_RES_PERMISSION_ID
   * 
   * @param nResPermissionId
   */
  public void setnResPermissionId(BigDecimal nResPermissionId) {
    this.nResPermissionId = nResPermissionId;
  }

  /**
   * getter for Column RES_PERMISSION.N_CUSTOMER_TYPE_ID
   */
  public BigDecimal getnCustomerTypeId() {
    return nCustomerTypeId;
  }

  /**
   * setter for Column RES_PERMISSION.N_CUSTOMER_TYPE_ID
   * 
   * @param nCustomerTypeId
   */
  public void setnCustomerTypeId(BigDecimal nCustomerTypeId) {
    this.nCustomerTypeId = nCustomerTypeId;
  }

  /**
   * getter for Column RES_PERMISSION.N_BUSINESS_FUNCTION_ID
   */
  public BigDecimal getnBusinessFunctionId() {
    return nBusinessFunctionId;
  }

  /**
   * setter for Column RES_PERMISSION.N_BUSINESS_FUNCTION_ID
   * 
   * @param nBusinessFunctionId
   */
  public void setnBusinessFunctionId(BigDecimal nBusinessFunctionId) {
    this.nBusinessFunctionId = nBusinessFunctionId;
  }

  /**
   * getter for Column RES_PERMISSION.D_CREATE_DATE
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * setter for Column RES_PERMISSION.D_CREATE_DATE
   * 
   * @param dCreateDate
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * getter for Column RES_PERMISSION.N_CREATED
   */
  public BigDecimal getnCreated() {
    return nCreated;
  }

  /**
   * setter for Column RES_PERMISSION.N_CREATED
   * 
   * @param nCreated
   */
  public void setnCreated(BigDecimal nCreated) {
    this.nCreated = nCreated;
  }

  /**
   * getter for Column RES_PERMISSION.N_MODIFIED
   */
  public BigDecimal getnModified() {
    return nModified;
  }

  /**
   * setter for Column RES_PERMISSION.N_MODIFIED
   * 
   * @param nModified
   */
  public void setnModified(BigDecimal nModified) {
    this.nModified = nModified;
  }

  /**
   * getter for Column RES_PERMISSION.D_MODIFIED_DATE
   */
  public Date getdModifiedDate() {
    return dModifiedDate;
  }

  /**
   * setter for Column RES_PERMISSION.D_MODIFIED_DATE
   * 
   * @param dModifiedDate
   */
  public void setdModifiedDate(Date dModifiedDate) {
    this.dModifiedDate = dModifiedDate;
  }

}
