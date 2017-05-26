package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

import com.pltfm.app.entities.OrderAlter;

public class OrderAlterVo extends OrderAlter implements Serializable {
  private static final long serialVersionUID = 1L;

  private String proposeStatusStr;// 状态

  public String getProposeStatusStr() {
    return proposeStatusStr;
  }

  public void setProposeStatusStr(String proposeStatusStr) {
    this.proposeStatusStr = proposeStatusStr;
  }

  private String backTypeStr;// 商品返回方式

  public String getBackTypeStr() {
    return backTypeStr;
  }

  public void setBackTypeStr(String backTypeStr) {
    this.backTypeStr = backTypeStr;
  }

  private String alterTypeStr;// 服务类型

  public String getAlterTypeStr() {
    return alterTypeStr;
  }

  public void setAlterTypeStr(String alterTypeStr) {
    this.alterTypeStr = alterTypeStr;
  }

  private String skuCode;

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  private String commodityName;

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  private String supplierName;// 供应商名称

  private String imgPath;// 产品图片路径

  private Long productSkuId;// 产品skuid

  // private String commodityTitle;// 产品标题

  private String orderType; // 订单类型
  
  private BigDecimal finalPayment;

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }
  
  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public BigDecimal getFinalPayment() {
    return finalPayment;
  }

  public void setFinalPayment(BigDecimal finalPayment) {
    this.finalPayment = finalPayment;
  }

  // public String getCommodityTitle() {
  // return commodityTitle;
  // }
  //
  // public void setCommodityTitle(String commodityTitle) {
  // this.commodityTitle = commodityTitle;
  // }
  
 

}
