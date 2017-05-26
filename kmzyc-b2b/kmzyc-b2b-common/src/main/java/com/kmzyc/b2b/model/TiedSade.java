package com.kmzyc.b2b.model;

import java.math.BigDecimal;

public class TiedSade {

  private Long tiedSadeId;
  private Long mainSku;
  private Long tiedSadeSku;
  private BigDecimal tiedSadeSkuPrice;
  private Short tiedSadeType;

  public Long getTiedSadeId() {
    return tiedSadeId;
  }

  public void setTiedSadeId(Long tiedSadeId) {
    this.tiedSadeId = tiedSadeId;
  }

  public Long getMainSku() {
    return mainSku;
  }

  public void setMainSku(Long mainSku) {
    this.mainSku = mainSku;
  }

  public Long getTiedSadeSku() {
    return tiedSadeSku;
  }

  public void setTiedSadeSku(Long tiedSadeSku) {
    this.tiedSadeSku = tiedSadeSku;
  }

  public BigDecimal getTiedSadeSkuPrice() {
    return tiedSadeSkuPrice;
  }

  public void setTiedSadeSkuPrice(BigDecimal tiedSadeSkuPrice) {
    this.tiedSadeSkuPrice = tiedSadeSkuPrice;
  }

  public Short getTiedSadeType() {
    return tiedSadeType;
  }

  public void setTiedSadeType(Short tiedSadeType) {
    this.tiedSadeType = tiedSadeType;
  }

}
