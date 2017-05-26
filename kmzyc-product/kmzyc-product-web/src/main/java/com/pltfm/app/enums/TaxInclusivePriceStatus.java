package com.pltfm.app.enums;


/**
 * 税率(含税)
 * @author kinspar
 *
 */
public enum TaxInclusivePriceStatus {
	INCLUSIVEPRICE(1.17,"含税税率"),
	NOTINCLUSIVEPRICE(1,"不含税税率");
	
	private double taxPirce;
	private String title = null;
	
	
	TaxInclusivePriceStatus(double taxPirce, String title) {
		this.taxPirce = taxPirce;
		this.title = title;
	}
	
	public double getTaxPirce() {
		return taxPirce;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("TaxInclusivePrice[status=").append(this.taxPirce)
		.append(",title=").append(this.title).append("]");
		return strBuilder.toString();
	}
}
