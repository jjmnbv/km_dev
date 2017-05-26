package com.kmzyc.b2b.model;

import java.io.Serializable;

public class SupplierOrderItem implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 6806897128709782550L;

  // 供应商
  private String supplier;
  // 供应商名称
  private String supplierName;
  // 供应商类型
  private Long supplierType;

  private String serviceQQ;
  // 1:QQ,2:旺旺
  private Long serviceType;

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public Long getSupplierType() {
    return supplierType;
  }

  public void setSupplierType(Long supplierType) {
    this.supplierType = supplierType;
  }

  public String getServiceQQ() {
    return serviceQQ;
  }

  public void setServiceQQ(String serviceQQ) {
    this.serviceQQ = serviceQQ;
  }

  public Long getServiceType() {
    return serviceType;
  }

  public void setServiceType(Long serviceType) {
    this.serviceType = serviceType;
  }

}
