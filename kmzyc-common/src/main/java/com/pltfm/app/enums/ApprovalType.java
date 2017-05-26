package com.pltfm.app.enums;


/**
 * 审核状态
 * @author xkj
 *
 */
public enum ApprovalType {
	OTHER("0","其他"),
	GY_ZHUN("1","国药准字"),
	GY_SHI("2","国药试字"),
	GY_JIAN("3","国药健字"),
	GS_JIAN("4","国食健字"),
	WS_JIAN("5","卫食健字");
	
	private String status;
	private String title = null;
	
	
	ApprovalType(String status, String title) {
		this.status = status;
		this.title = title;
	}
	
	public String getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ApprovalType[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
