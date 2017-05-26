package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-06-25
 */
public class LoginRoseRelDO implements Serializable {

  private static final long serialVersionUID = 140368660426422263L;

  /**
   * column LOGIN_ROSE_REL.LR_ID
   */
  private BigDecimal lrId;

  /**
   * column LOGIN_ROSE_REL.N_LOGIN_ID
   */
  private BigDecimal nLoginId;

  /**
   * column LOGIN_ROSE_REL.N_LEVEL_ID
   */
  private BigDecimal nLevelId;

  /**
   * column LOGIN_ROSE_REL.N_CUSTOMER_TYPE_ID
   */
  private BigDecimal nCustomerTypeId;

  /**
   * column LOGIN_ROSE_REL.D_CREATE_DATE
   */
  private Date dCreateDate;

  /**
   * column LOGIN_ROSE_REL.N_CREATED
   */
  private BigDecimal nCreated;

  /**
   * column LOGIN_ROSE_REL.D_MODIFY_DATE
   */
  private Date dModifyDate;

  /**
   * column LOGIN_ROSE_REL.N_MODIFIED
   */
  private BigDecimal nModified;

  /**
   * column LOGIN_ROSE_REL.STATUS
   */
  private Short status;

  /**
   * column LOGIN_ROSE_REL.DESCRIPTION
   */
  private String description;

  /**
   * 商户主键
   */
  private BigDecimal n_CommercialTenantId;

  /**
   * column LOGIN_ROSE_REL.IS_VALID
   */
  private Short isValid;

  public Short getIsValid() {
    return isValid;
  }

  public void setIsValid(Short isValid) {
    this.isValid = isValid;
  }

  public LoginRoseRelDO() {
    super();
  }

  public LoginRoseRelDO(BigDecimal lrId, BigDecimal nLoginId, BigDecimal nLevelId,
      BigDecimal nCustomerTypeId, Date dCreateDate, BigDecimal nCreated, Date dModifyDate,
      BigDecimal nModified, Short status, String description, BigDecimal n_CommercialTenantId,
      Short isValid) {
    this.lrId = lrId;
    this.nLoginId = nLoginId;
    this.nLevelId = nLevelId;
    this.nCustomerTypeId = nCustomerTypeId;
    this.dCreateDate = dCreateDate;
    this.nCreated = nCreated;
    this.dModifyDate = dModifyDate;
    this.nModified = nModified;
    this.status = status;
    this.description = description;
    this.n_CommercialTenantId = n_CommercialTenantId;
    this.isValid = isValid;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.LR_ID
   */
  public BigDecimal getLrId() {
    return lrId;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.LR_ID
   * 
   * @param lrId
   */
  public void setLrId(BigDecimal lrId) {
    this.lrId = lrId;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.N_LOGIN_ID
   */
  public BigDecimal getnLoginId() {
    return nLoginId;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.N_LOGIN_ID
   * 
   * @param nLoginId
   */
  public void setnLoginId(BigDecimal nLoginId) {
    this.nLoginId = nLoginId;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.N_LEVEL_ID
   */
  public BigDecimal getnLevelId() {
    return nLevelId;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.N_LEVEL_ID
   * 
   * @param nLevelId
   */
  public void setnLevelId(BigDecimal nLevelId) {
    this.nLevelId = nLevelId;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.N_CUSTOMER_TYPE_ID
   */
  public BigDecimal getnCustomerTypeId() {
    return nCustomerTypeId;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.N_CUSTOMER_TYPE_ID
   * 
   * @param nCustomerTypeId
   */
  public void setnCustomerTypeId(BigDecimal nCustomerTypeId) {
    this.nCustomerTypeId = nCustomerTypeId;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.D_CREATE_DATE
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.D_CREATE_DATE
   * 
   * @param dCreateDate
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.N_CREATED
   */
  public BigDecimal getnCreated() {
    return nCreated;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.N_CREATED
   * 
   * @param nCreated
   */
  public void setnCreated(BigDecimal nCreated) {
    this.nCreated = nCreated;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.D_MODIFY_DATE
   */
  public Date getdModifyDate() {
    return dModifyDate;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.D_MODIFY_DATE
   * 
   * @param dModifyDate
   */
  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.N_MODIFIED
   */
  public BigDecimal getnModified() {
    return nModified;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.N_MODIFIED
   * 
   * @param nModified
   */
  public void setnModified(BigDecimal nModified) {
    this.nModified = nModified;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.STATUS
   */
  public Short getStatus() {
    return status;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.STATUS
   * 
   * @param status
   */
  public void setStatus(Short status) {
    this.status = status;
  }

  /**
   * getter for Column LOGIN_ROSE_REL.DESCRIPTION
   */
  public String getDescription() {
    return description;
  }

  /**
   * setter for Column LOGIN_ROSE_REL.DESCRIPTION
   * 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getN_CommercialTenantId() {
    return n_CommercialTenantId;
  }

  public void setN_CommercialTenantId(BigDecimal n_CommercialTenantId) {
    this.n_CommercialTenantId = n_CommercialTenantId;
  }


}
