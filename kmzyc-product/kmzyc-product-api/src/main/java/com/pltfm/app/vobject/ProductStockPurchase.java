package com.pltfm.app.vobject;

import java.io.Serializable;

public class ProductStockPurchase  implements Serializable {

	
private static final long serialVersionUID = -516414990604139588L;	
	
private Long stockId;
private Long warehouseId;              // 产品仓库ID

private Long productId;                // 产品ID

private String productName;        // 产品名称
private String productNo;            // 产品编号

private Long skuAttributeId;      //产品货号ID
private String skuAttValue;      //产品货号

private Long stockQuality;   // 库存数量


private Long inTransitQuality; //在途数量

private String  stockInNo;  // 入库单号

private String purchaseNo  ;	//采购单号



public Long getStockId() {
	return stockId;
}



public void setStockId(Long stockId) {
	this.stockId = stockId;
}



public Long getWarehouseId() {
	return warehouseId;
}



public void setWarehouseId(Long warehouseId) {
	this.warehouseId = warehouseId;
}



public Long getProductId() {
	return productId;
}



public void setProductId(Long productId) {
	this.productId = productId;
}



public String getProductName() {
	return productName;
}



public void setProductName(String productName) {
	this.productName = productName;
}



public String getProductNo() {
	return productNo;
}



public void setProductNo(String productNo) {
	this.productNo = productNo;
}



public Long getSkuAttributeId() {
	return skuAttributeId;
}



public void setSkuAttributeId(Long skuAttributeId) {
	this.skuAttributeId = skuAttributeId;
}



public String getSkuAttValue() {
	return skuAttValue;
}



public void setSkuAttValue(String skuAttValue) {
	this.skuAttValue = skuAttValue;
}



public Long getStockQuality() {
	return stockQuality;
}



public void setStockQuality(Long stockQuality) {
	this.stockQuality = stockQuality;
}



public Long getInTransitQuality() {
	return inTransitQuality;
}



public void setInTransitQuality(Long inTransitQuality) {
	this.inTransitQuality = inTransitQuality;
}



public String getPurchaseNo() {
	return purchaseNo;
}



public void setPurchaseNo(String purchaseNo) {
	this.purchaseNo = purchaseNo;
}



public String getStockInNo() {
	return stockInNo;
}



public void setStockInNo(String stockInNo) {
	this.stockInNo = stockInNo;
}
	
	
	
	
	
}
