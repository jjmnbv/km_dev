package com.pltfm.app.enums;

public enum ConsultCheckStatus {
	WAITCHECK("0","待审核"),
	NOTPASSED("1","未通过"),
	HAVEPASSED("2","已通过");
	
	private String status=null;
	private String title=null;
	
	ConsultCheckStatus(String status,String title)
	{
		this.status=status;
		this.title=title;
	}

	public String getStatus() {
		return status;
	}

    private void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

    private void setTitle(String title) {
		this.title = title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ConsultCheckStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
