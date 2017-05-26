package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockOutVO implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long warehouseID;// 仓库ID

  private String skuCode;

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  private Long billDetailID;
  private Long commodityNumber;
  private BigDecimal unitPrice;// 单价
  private Long billNo;
  private String purchaserName;// 收货人名称
  private String purchaserAddrr;// 收货人地址
  private String purchaserTel;// 收货人电话

  public Long getWarehouseID() {
    return warehouseID;
  }

  public void setWarehouseID(Long warehouseID) {
    this.warehouseID = warehouseID;
  }

  public Long getBillDetailID() {
    return billDetailID;
  }

  public void setBillDetailID(Long billDetailID) {
    this.billDetailID = billDetailID;
  }

  public Long getCommodityNumber() {
    return commodityNumber;
  }

  public void setCommodityNumber(Long commodityNumber) {
    this.commodityNumber = commodityNumber;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Long getBillNo() {
    return billNo;
  }

  public void setBillNo(Long billNo) {
    this.billNo = billNo;
  }

  public String getPurchaserName() {
    return purchaserName;
  }

  public void setPurchaserName(String purchaserName) {
    this.purchaserName = purchaserName;
  }

  public String getPurchaserAddrr() {
    return purchaserAddrr;
  }

  public void setPurchaserAddrr(String purchaserAddrr) {
    this.purchaserAddrr = purchaserAddrr;
  }

  public String getPurchaserTel() {
    return purchaserTel;
  }

  public void setPurchaserTel(String purchaserTel) {
    this.purchaserTel = purchaserTel;
  }

}
