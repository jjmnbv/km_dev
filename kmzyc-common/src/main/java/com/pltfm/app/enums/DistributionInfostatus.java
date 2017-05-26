package com.pltfm.app.enums;


/**
 * 配送单是否已送达状态
 * @author luoyi
 *
 */
public enum DistributionInfostatus {
	UNDELIVERIES("0","未发货"),
	ISDELIVERIES("1","已发货"),
	RECEIVED("2","已收货");
	
	private String status;
	private String title = null;
	
	
	DistributionInfostatus(String status, String title) {
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
		strBuilder.append("DistributionInfostatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
