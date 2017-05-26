package com.pltfm.app.enums;


/**
 * 审核状态
 * @author xkj
 *
 */
public enum IsAddItransitStatus {
	NOT_ADD(Short.valueOf("0"),"未增加过在途数量"),
	IS_ADD(Short.valueOf("1"),"已增加过在途数量");
	
	private Short status;
	private String title = null;
	
	
	IsAddItransitStatus(Short status, String title) {
		this.status = status;
		this.title = title;
	}
	
	public Short getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("IsAddItransitStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
