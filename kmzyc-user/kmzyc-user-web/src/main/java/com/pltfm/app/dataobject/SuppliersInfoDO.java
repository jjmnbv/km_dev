package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据对象
 * 
 * @since 2014-10-22
 */
public class SuppliersInfoDO implements Serializable {

  private static final long serialVersionUID = 141396257810166848L;

  /**
   * column SUPPLIERS_INFO.SUPPLIER_ID
   */
  private BigDecimal supplierId;

  /**
   * column SUPPLIERS_INFO.MERCHANT_ID
   */
  private BigDecimal merchantId;

  /**
   * column SUPPLIERS_INFO.SALE_PRODUCT_DESCRIBE
   */
  private String saleProductDescribe;

  /**
   * column SUPPLIERS_INFO.SETTLEMENT_CYCLE
   */
  private Short settlementCycle;

  /**
   * column SUPPLIERS_INFO.SETTLEMENT_WAY
   */
  private Short settlementWay;

  /**
   * column SUPPLIERS_INFO.SUPPLIER_TYPE
   */
  private Short supplierType;

  /**
   * column SUPPLIERS_INFO.STATUS
   */
  private Short status;

  /**
   * column SUPPLIERS_INFO.DESCRIBE
   */
  private String describe;

  /**
   * 公司名称
   */
  private String corporateName;
  /** 开始索引值 **/
  private Integer startIndex;
  /** 结束索引值 **/
  private Integer endIndex;

  public Integer getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public SuppliersInfoDO() {
    super();
  }

  public SuppliersInfoDO(BigDecimal supplierId, BigDecimal merchantId, String saleProductDescribe,
      Short settlementCycle, Short settlementWay, Short supplierType, Short status, String describe,
      String corporateName) {
    this.supplierId = supplierId;
    this.merchantId = merchantId;
    this.saleProductDescribe = saleProductDescribe;
    this.settlementCycle = settlementCycle;
    this.settlementWay = settlementWay;
    this.supplierType = supplierType;
    this.status = status;
    this.describe = describe;
    this.corporateName = corporateName;
  }

  /**
   * getter for Column SUPPLIERS_INFO.SUPPLIER_ID
   */
  public BigDecimal getSupplierId() {
    return supplierId;
  }

  /**
   * setter for Column SUPPLIERS_INFO.SUPPLIER_ID
   * 
   * @param supplierId
   */
  public void setSupplierId(BigDecimal supplierId) {
    this.supplierId = supplierId;
  }

  /**
   * getter for Column SUPPLIERS_INFO.MERCHANT_ID
   */
  public BigDecimal getMerchantId() {
    return merchantId;
  }

  /**
   * setter for Column SUPPLIERS_INFO.MERCHANT_ID
   * 
   * @param merchantId
   */
  public void setMerchantId(BigDecimal merchantId) {
    this.merchantId = merchantId;
  }

  /**
   * getter for Column SUPPLIERS_INFO.SALE_PRODUCT_DESCRIBE
   */
  public String getSaleProductDescribe() {
    return saleProductDescribe;
  }

  /**
   * setter for Column SUPPLIERS_INFO.SALE_PRODUCT_DESCRIBE
   * 
   * @param saleProductDescribe
   */
  public void setSaleProductDescribe(String saleProductDescribe) {
    this.saleProductDescribe = saleProductDescribe;
  }

  /**
   * getter for Column SUPPLIERS_INFO.SETTLEMENT_CYCLE
   */
  public Short getSettlementCycle() {
    return settlementCycle;
  }

  /**
   * setter for Column SUPPLIERS_INFO.SETTLEMENT_CYCLE
   * 
   * @param settlementCycle
   */
  public void setSettlementCycle(Short settlementCycle) {
    this.settlementCycle = settlementCycle;
  }

  /**
   * getter for Column SUPPLIERS_INFO.SETTLEMENT_WAY
   */
  public Short getSettlementWay() {
    return settlementWay;
  }

  /**
   * setter for Column SUPPLIERS_INFO.SETTLEMENT_WAY
   * 
   * @param settlementWay
   */
  public void setSettlementWay(Short settlementWay) {
    this.settlementWay = settlementWay;
  }

  /**
   * getter for Column SUPPLIERS_INFO.SUPPLIER_TYPE
   */
  public Short getSupplierType() {
    return supplierType;
  }

  /**
   * setter for Column SUPPLIERS_INFO.SUPPLIER_TYPE
   * 
   * @param supplierType
   */
  public void setSupplierType(Short supplierType) {
    this.supplierType = supplierType;
  }

  /**
   * getter for Column SUPPLIERS_INFO.STATUS
   */
  public Short getStatus() {
    return status;
  }

  /**
   * setter for Column SUPPLIERS_INFO.STATUS
   * 
   * @param status
   */
  public void setStatus(Short status) {
    this.status = status;
  }

  /**
   * getter for Column SUPPLIERS_INFO.DESCRIBE
   */
  public String getDescribe() {
    return describe;
  }

  /**
   * setter for Column SUPPLIERS_INFO.DESCRIBE
   * 
   * @param describe
   */
  public void setDescribe(String describe) {
    this.describe = describe;
  }

}
