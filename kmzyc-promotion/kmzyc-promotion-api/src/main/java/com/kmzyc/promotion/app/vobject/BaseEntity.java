package com.kmzyc.promotion.app.vobject;

public class BaseEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5040346676843173938L;
	// ------------ for page
	Integer skip;
	Integer max;

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}
