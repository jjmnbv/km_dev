package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

/**
 * 订单传过来的包
 * 
 * @author Administrator
 * 
 */
public class OrderVo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 产品skucode
	 */
	private String productSkuCode;
	
	/**
	 * 产品单价（最终价格）
	 */
	private BigDecimal productPrice;
	
	/**
	 * 产品数量
	 * @return
	 */
	private int productNumber;
	
	/**
	 * 对应产品总价
	 * @return
	 */
	private BigDecimal productTotlePrice;
	
	
	/**
	 * 产品类型 ：0.单独产品 1.套餐内产品 
	 * @return
	 */
	 private Integer productType;
	 /**
	  * 套餐ID
	  */
	 private BigDecimal relationId;
	 /**
	  * 套餐价格
	  */
	 private BigDecimal relationPrice;
	
	public BigDecimal getRelationPrice() {
		return relationPrice;
	}

	public void setRelationPrice(BigDecimal relationPrice) {
		this.relationPrice = relationPrice;
	}

	public BigDecimal getRelationId() {
		return relationId;
	}

	public void setRelationId(BigDecimal relationId) {
		this.relationId = relationId;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(int productNumber) {
		this.productNumber = productNumber;
	}

	public BigDecimal getProductTotlePrice() {
		return productTotlePrice;
	}

	public void setProductTotlePrice(BigDecimal productTotlePrice) {
		this.productTotlePrice = productTotlePrice;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}


 
	 
}
