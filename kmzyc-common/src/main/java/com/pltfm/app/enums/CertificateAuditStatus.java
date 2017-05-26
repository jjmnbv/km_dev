package com.pltfm.app.enums;


/**
 * 
 * @ClassName: CertificateAuditStatus 
 * @Description: TODO(资质重审状态) 
 * @author pengbo
 * @date 2016年8月30日 下午2:09:27
 */
public enum CertificateAuditStatus {
	UNAUDIT("0","待申请重审"),
	AUDITORIAL("1","重审中"),
	AUDIT("2","重审通过"),
    NOT_THROUGH_AUDIT("3","重审不通过");
	
	private String status;
	private String title = null;
	
	CertificateAuditStatus(String status, String title) {
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
		strBuilder.append("CertificateAuditStatus[status=").append(this.status)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
