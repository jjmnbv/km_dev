package com.kmzyc.supplier.constrant;


/**
 * 供应商证件类型相关的枚举类
 * 
 * @author luoyi
 * @version 1.0
 */
public enum CertificateTypeEnum {
	Shen_Fen_Zheng(0,"身份证"),
	Hu_Zhao(1,"护照"),
	Hui_Xiang_Zheng(2,"回乡证");
	
	private int type;
	private String title = null;

	CertificateTypeEnum(int type, String title) {
		this.type = type;
		this.title = title;
	}

	public int getType() {
		return type;
	}

    private void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}
}
