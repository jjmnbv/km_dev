package com.pltfm.app.enums;


/**
 * 库存日志业务操作
 * @author xkj
 *
 */
public enum StockLogBillType {

	ADD_STOCK(Short.valueOf("1"),"新增库存"),
	UPDATE_STOCK(Short.valueOf("2"),"修改库存"),
	PURCHASE_INFO(Short.valueOf("3"),"采购单"),
	STOCK_IN(Short.valueOf("4"),"入库单"),
	STOCK_OUT(Short.valueOf("5"),"出库单"),
	ORDER(Short.valueOf("6"),"销售订单"),
	KJ_ORDER(Short.valueOf("7"),"砍价商品订单");

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
		strBuilder.append("StockLogOpType[type=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
