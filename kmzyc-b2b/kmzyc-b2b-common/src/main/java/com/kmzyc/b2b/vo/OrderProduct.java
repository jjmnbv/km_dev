package com.kmzyc.b2b.vo;

public class OrderProduct {/*
	*/
  /** 产品SKU ID */
  /*
   * private Long productSkuId;
   *//** 产品ID */
  /*
   * private Long productID;
   *//** 产品购买数量 */
  /*
   * private int amount;
   *//** 市场价格 */
  /*
   * private BigDecimal marketPrice;
   *//** 交易单价 */
  /*
   * private BigDecimal dealPrice;
   *//** 最终总价格 */
  /*
   * private BigDecimal lastPrice;
   *//** 最终总价格 */
  /*
   * //private BigDecimal lastMoney;
   * 
   * public BigDecimal getLastMoney() { return dealPrice.multiply(new BigDecimal(amount)); }
   * 
   * 
   * 
   * private String productName;
   * 
   * 
   * public Long getProductSkuId() { return productSkuId; }
   * 
   * public void setProductSkuId(Long productSkuId) { this.productSkuId = productSkuId; }
   * 
   * public Long getProductID() { return productID; }
   * 
   * public void setProductID(Long productID) { this.productID = productID; }
   * 
   * public int getAmount() { return amount; }
   * 
   * public void setAmount(int amount) { this.amount = amount; }
   * 
   * public BigDecimal getMarketPrice() { return marketPrice; }
   * 
   * public void setMarketPrice(BigDecimal marketPrice) { this.marketPrice = marketPrice; }
   *//** 交易单价 */
  /*
   * public BigDecimal getDealPrice() { return dealPrice; }
   *//** 交易单价 */
  /*
   * public void setDealPrice(BigDecimal dealPrice) { this.dealPrice = dealPrice; }
   *//** 最终总价格 */
  /*
   * public BigDecimal getLastPrice() { return lastPrice; }
   *//** 最终总价格 */
  /*
   * public void setLastPrice(BigDecimal lastPrice) { this.lastPrice = lastPrice; }
   * 
   * public OrderProduct(CarProduct cp){ this.amount=cp.getAmount(); //
   * this.marketPrice=BigDecimal.valueOf(cp.getMarketPrice()); this.productID=cp.getProductID();
   * this.productSkuId=cp.getProductSkuId(); this.productName=cp.getName();
   * this.dealPrice=cp.getCostPrice();
   * this.lastPrice=BigDecimal.valueOf(cp.getAmount()).multiply(dealPrice);
   * 
   * } public OrderProduct(){
   * 
   * } public OrderProduct(CompositionCarProduct ccp){ this.amount=ccp.getAmount();
   * this.marketPrice=ccp.getCostPrice(); this.dealPrice=ccp.getCostPrice();
   * this.productID=Long.valueOf(ccp.getId()); //this.productSkuId=this.productId;
   * this.lastPrice=ccp.getCostPrice(); }
   * 
   * public String getProductName() { return productName; }
   * 
   * public void setProductName(String productName) { this.productName = productName; }
   */
}
