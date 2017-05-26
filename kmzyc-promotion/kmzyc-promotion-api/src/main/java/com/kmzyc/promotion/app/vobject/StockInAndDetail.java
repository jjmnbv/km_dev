package com.kmzyc.promotion.app.vobject;

public class StockInAndDetail implements java.io.Serializable {

	private static final long serialVersionUID = -165231070254015190L;

	private Long stockInId;

	private Integer warehouseId;

	private Long productId;

	private String stockInNo; // 入库单据号

	private String status;

	private String productName;

	private String productNo;

	private Long productSkuId;

	private String productSkuValue;

	private Integer quantity;

	private Short type;

	private String purchaseNo;// 采购单号

	public Long getStockInId() {
		return stockInId;
	}

	public void setStockInId(Long stockInId) {
		this.stockInId = stockInId;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
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

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductSkuValue() {
		return productSkuValue;
	}

	public void setProductSkuValue(String productSkuValue) {
		this.productSkuValue = productSkuValue;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
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
