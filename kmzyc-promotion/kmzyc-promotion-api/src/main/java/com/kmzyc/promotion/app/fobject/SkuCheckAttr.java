package com.kmzyc.promotion.app.fobject;

import java.io.Serializable;

public class SkuCheckAttr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6726682004037105823L;

	private String skuCheckedId;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSkuCheckedId() {
		return skuCheckedId;
	}

	public void setSkuCheckedId(String skuCheckedId) {
		this.skuCheckedId = skuCheckedId;
	}

}
