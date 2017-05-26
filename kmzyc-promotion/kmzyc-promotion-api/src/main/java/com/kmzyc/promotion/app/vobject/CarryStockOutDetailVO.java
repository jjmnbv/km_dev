package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单出库明细单(远程接口用)
 * 
 * @author luoyi
 * @since 2013/09/02
 */
public class CarryStockOutDetailVO implements Serializable {
	private static final long serialVersionUID = -3000889673055790639L;

	private Long warehouseId;// 仓库ID
	private String skuCode;// SKU编码
	private Long billDetailID;// 单据明细ID
	private Long commodityNumber;// 出库数量
	private BigDecimal unitPrice;// 单价
	private Long stockId;// 库存ID(订单系统不需要传此值,产品系统处理时生成库存ID)

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getBillDetailID() {
		return billDetailID;
	}

	public void setBillDetailID(Long billDetailID) {
		this.billDetailID = billDetailID;
	}

	public Long getCommodityNumber() {
		return commodityNumber;
	}

	public void setCommodityNumber(Long commodityNumber) {
		this.commodityNumber = commodityNumber;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	@Override
	public String toString() {
		return "CarryStockOutDetailVO [warehouseId=" + warehouseId + ", skuCode=" + skuCode + ", billDetailID="
				+ billDetailID + ", commodityNumber=" + commodityNumber + ", unitPrice=" + unitPrice + ", stockId="
				+ stockId + "]";
	}

}
