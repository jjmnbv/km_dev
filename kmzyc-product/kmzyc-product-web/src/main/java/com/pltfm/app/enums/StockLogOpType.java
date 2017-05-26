package com.pltfm.app.enums;


/**
 * 库存操作类型
 * @author xkj
 *
 */
public enum StockLogOpType {
	
	IN(Short.valueOf("1"),"入库"),
	OUT(Short.valueOf("2"),"出库"),
	ADD_IN_TRANSIT(Short.valueOf("3"),"加在途"),
	DEC_IN_TRANSIT(Short.valueOf("4"),"减在途"),
	ADD_ORDER(Short.valueOf("5"),"加订购"),
	DEC_ORDER(Short.valueOf("6"),"减订购"),
	OP_STOCK(Short.valueOf("7"),"直接操作库存记录");
	
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
		strBuilder.append("StockLogOpType[type=").append(this.type)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
