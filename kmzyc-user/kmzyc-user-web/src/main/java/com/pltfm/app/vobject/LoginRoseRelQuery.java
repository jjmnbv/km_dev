package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-05-22
 */
public class LoginRoseRelQuery implements Serializable {

  private static final long serialVersionUID = 140075006346950456L;


  private BigDecimal lrId;

  private BigDecimal nLoginId;


  private Integer commercialTenantId;

  private BigDecimal nLevelId;

  private Integer nCustomerTypeId;

  private Date dCreateDate;

  private BigDecimal nCreated;

  private Date dModifyDate;

  private BigDecimal nModified;

  private Short isValid;

  public BigDecimal getLrId() {
    return lrId;
  }

  public void setLrId(BigDecimal lrId) {
    this.lrId = lrId;
  }

  public BigDecimal getnLoginId() {
    return nLoginId;
  }

  public void setnLoginId(BigDecimal nLoginId) {
    this.nLoginId = nLoginId;
  }

  public Integer getCommercialTenantId() {
    return commercialTenantId;
  }

  public void setCommercialTenantId(Integer commercialTenantId) {
    this.commercialTenantId = commercialTenantId;
  }

  public BigDecimal getnLevelId() {
    return nLevelId;
  }

  public void setnLevelId(BigDecimal nLevelId) {
    this.nLevelId = nLevelId;
  }

  public Integer getnCustomerTypeId() {
    return nCustomerTypeId;
  }

  public void setnCustomerTypeId(Integer nCustomerTypeId) {
    this.nCustomerTypeId = nCustomerTypeId;
  }

  public Date getdCreateDate() {
    return dCreateDate;
  }

  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  public BigDecimal getnCreated() {
    return nCreated;
  }

  public void setnCreated(BigDecimal nCreated) {
    this.nCreated = nCreated;
  }

  public Date getdModifyDate() {
    return dModifyDate;
  }

  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  public BigDecimal getnModified() {
    return nModified;
  }

  public void setnModified(BigDecimal nModified) {
    this.nModified = nModified;
  }

  public Short getIsValid() {
    return isValid;
  }

  public void setIsValid(Short isValid) {
    this.isValid = isValid;
  }



}
