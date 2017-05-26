package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;

public class ProductStockAndSku implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long stockId;
	private Long warehouseId;
	private Long skuAttributeId;
	private String skuAttValue;
	private Long stockQuality;
	private Long productSkuId;
	private String status;

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

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
