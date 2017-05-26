package com.pltfm.app.enums;


/**
 * 运营属性
 * @author xkj
 *
 */
public enum MsgOperation {
	ADD("ADD"),	// 添加索引
	UPDATE("UPDATE"), // 更新
	DELETE("DELETE");	// 删除
	
	private String type;
	
	
	MsgOperation(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("OperationAttr[status=").append(this.type).append("]");
		return strBuilder.toString();
	}
}
