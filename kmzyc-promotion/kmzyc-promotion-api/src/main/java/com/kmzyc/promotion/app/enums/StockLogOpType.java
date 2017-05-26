package com.kmzyc.promotion.app.enums;

/**
 * 库存操作类型
 * 
 * @author xkj
 * 
 */
public enum StockLogOpType {

	IN(new Short("1"), "入库"), OUT(new Short("2"), "出库"), ADD_IN_TRANSIT(new Short("3"), "加在途"), DEC_IN_TRANSIT(
			new Short("4"), "减在途"), ADD_ORDER(new Short("5"), "加订购"), DEC_ORDER(new Short("6"), "减订购"), OP_STOCK(
			new Short("7"), "直接操作库存记录");

	private Short type;
	private String title = null;

	StockLogOpType(Short type, String title) {
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
