package com.kmzyc.b2b.model;

public class ProductStockInterface {
  // skuID
  private Long skuId;
  // 库存总和
  private Double sum;
  // 商品状态（上下架）
  private Integer status;

  public Double getSum() {
    if (sum == null) {
      sum = 0d;
    }
    return sum;
  }

  public void setSum(Double sum) {
    this.sum = sum;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getSkuId() {
    return skuId;
  }

  public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }

  @Override
  public String toString() {
    return "{'skuId':" + skuId + ",'sum':" + sum + ", 'status':" + status + "}";
  }

}
