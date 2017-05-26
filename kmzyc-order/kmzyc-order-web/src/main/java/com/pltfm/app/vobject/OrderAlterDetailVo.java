package com.pltfm.app.vobject;

import java.io.Serializable;

import com.pltfm.app.entities.OrderAlterDetail;

public class OrderAlterDetailVo extends OrderAlterDetail implements Serializable {
  private static final long serialVersionUID = 1L;

  private String commodityName;// 商品名称

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  private String commodityUnitPrice;// 价格

  public String getCommodityUnitPrice() {
    return commodityUnitPrice;
  }

  public void setCommodityUnitPrice(String commodityUnitPrice) {
    this.commodityUnitPrice = commodityUnitPrice;
  }

  private String commodityNumber;// 数量

  public String getCommodityNumber() {
    return commodityNumber;
  }

  public void setCommodityNumber(String commodityNumber) {
    this.commodityNumber = commodityNumber;
  }

}
