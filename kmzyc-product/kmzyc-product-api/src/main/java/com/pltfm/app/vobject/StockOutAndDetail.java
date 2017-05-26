package com.pltfm.app.vobject;

import java.io.Serializable;

public class StockOutAndDetail implements Serializable {

	private static final long serialVersionUID = -2323149895986041453L;

	/**
	 * StockOutAndDetail无参构造方法
	 */
	public StockOutAndDetail() {

	}

	private Long stockOutId;// 出库ID
	
	private String stockOutNo;//出库单编码

	private Long warehouseId;// 出库仓库ID

	private Short status;// 状态

	private Short type;// 出库单类型

	private Long detailId;// 明细ID

	private Long stockId;// 库存ID

	private Long productSkuId;// 产品出库条码ID

	private String productSkuValue;// 产品货号SKU

	private Integer quantity;// 出库数量

	public Long getStockOutId() {
		return stockOutId;
	}

	public void setStockOutId(Long stockOutId) {
		this.stockOutId = stockOutId;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "StockOutAndDetail [stockOutId=" + stockOutId + ", warehouseId="
				+ warehouseId + ", status=" + status + ", type=" + type
				+ ", detailId=" + detailId + ", stockId=" + stockId
				+ ", productSkuId=" + productSkuId + ", productSkuValue="
				+ productSkuValue + ", quantity=" + quantity + "]";
	}

	public String getStockOutNo() {
		return stockOutNo;
	}

	public void setStockOutNo(String stockOutNo) {
		this.stockOutNo = stockOutNo;
	}

}
