package com.kmzyc.promotion.app.enums;

/**
 * 库存日志业务操作
 * 
 * @author xkj
 * 
 */
public enum StockLogBillType {

	ADD_STOCK(new Short("1"), "新增库存"), UPDATE_STOCK(new Short("2"), "修改库存"), PURCHASE_INFO(new Short("3"), "采购单"), STOCK_IN(
			new Short("4"), "入库单"), STOCK_OUT(new Short("5"), "出库单"), ORDER(new Short("6"), "销售订单");

	private Short type;
	private String title = null;

	StockLogBillType(Short type, String title) {
		this.type = type;
		this.title = title;
	}

	public Short getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("StockLogOpType[type=").append(this.type).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
