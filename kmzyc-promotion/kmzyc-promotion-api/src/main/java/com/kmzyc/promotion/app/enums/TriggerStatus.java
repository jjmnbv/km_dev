package com.kmzyc.promotion.app.enums;

public enum TriggerStatus {
	TR_WAIT("1", "等待上架中"), TR_RUN("2", "运行"), TR_OVER("3", "完结"), TR_SUCEESS("4", "全部上架成功"), TR_PARTFAIL("5",
			"部分产品不符合上架规则,部分上架失败"), TR_SYSFAIL("6", "系统出现故障,部分上架失败");

	private String status;
	private String title = null;

	TriggerStatus(String status, String title) {
		this.status = status;
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("TriggerStatus[status=").append(this.status).append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
