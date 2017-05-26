package com.kmzyc.promotion.app.fobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class StockInAndDetail implements Serializable {

	private static final long serialVersionUID = -2916805452577843434L;

	private BigDecimal stockInId;// 出库ID

	private BigDecimal warehouseId;// 出库仓库ID

	private Short status;// 状态

	private Integer detailId;// 明细ID

	private Integer stockId;// 库存ID

	private Integer productSkuId;// 产品出库条码ID

	private String productSkuValue;// 产品货号SKU

	private Integer quantity;// 出库数量

	private String purchaseNo; // 采购单号

	public BigDecimal getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(BigDecimal warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
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

	public BigDecimal getStockInId() {
		return stockInId;
	}

	public void setStockInId(BigDecimal stockInId) {
		this.stockInId = stockInId;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

}
