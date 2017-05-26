package com.pltfm.app.enums;


/**
 * 活动渠道
 * @author xkj
 *
 */
public enum ActivityChannl {
    /* 康美云店 康美中药城 返利网 */
	KMYD("KMYD","康美云店"),
    KMB2B("KMB2B", "康美中药城"),
	FLW("FLW","返利网");
	
	private String channl;
	private String title = null;
	
	
	ActivityChannl(String channl, String title) {
		this.channl = channl;
		this.title = title;
	}
	
	public String getChannl() {
		return channl;
	}

	public String getTitle() {
		return title;
	}
	
	@Override
    public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("ActivityChannl[status=").append(this.channl)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
