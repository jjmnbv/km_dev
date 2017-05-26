package com.kmzyc.b2b.third.model.taobao;

/**
 * 优惠券封装类
 * 
 * @author Administrator 2014-06-13 reference
 */
public class Ump {
  String idValue;// 优惠ID
  String name;// 优惠名称
  String skuId;// sku id
  String skuPrice;// sku 价格（优惠后）

  public String getIdValue() {
    return idValue;
  }

  public void setIdValue(String idValue) {
    this.idValue = idValue;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getSkuPrice() {
    return skuPrice;
  }

  public void setSkuPrice(String skuPrice) {
    this.skuPrice = skuPrice;
  }

}
