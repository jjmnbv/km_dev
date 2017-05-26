package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

/**
 * 选择搭配商品时候显示列表所需要的实体类
 * 
 * @author Administrator
 * 
 */

public class ProductTiedShow implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5641872003936254710L;

	/**
	 * tiedSade 表中的主键值
	 */

	private Long tiedSadeId;

	/**
	 * 产品SKU 编号
	 */

	private String skuCode;

	/**
	 * 被搭配的商品的SKUID
	 */
	private Long edproductSkuId;
	/**
	 * 被搭配商品的价格
	 */
	private BigDecimal edPrice;

	/**
	 * 匹配商品的skuID
	 */
	private Long productSkuId;

	/**
	 * 匹配的商品的价格
	 */
	private BigDecimal selfPrice;

	/**
	 * 搭售产品的类型
	 */

	private Short tiedSadeType;

	/**
	 * 匹配商品的名称
	 */
	private String selfName;

	/**
	 * 匹配商品的图片地址
	 */

	private String productSkuImage;
	/**
	 * 两种商品的搭配价格
	 */
	private BigDecimal matchPrice;

	/**
	 * 匹配商品的展示页面地址
	 */
	private String showSite;

	public Long getEdproductSkuId() {
		return edproductSkuId;
	}

	public void setEdproductSkuId(Long edproductSkuId) {
		this.edproductSkuId = edproductSkuId;
	}

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public BigDecimal getSelfPrice() {
		return selfPrice;
	}

	public void setSelfPrice(BigDecimal selfPrice) {
		this.selfPrice = selfPrice;
	}

	public String getSelfName() {
		return selfName;
	}

	public void setSelfName(String selfName) {
		this.selfName = selfName;
	}

	public String getProductSkuImage() {
		return productSkuImage;
	}

	public void setProductSkuImage(String productSkuImage) {
		this.productSkuImage = productSkuImage;
	}

	public BigDecimal getMatchPrice() {
		return matchPrice;
	}

	public void setMatchPrice(BigDecimal matchPrice) {
		this.matchPrice = matchPrice;
	}

	public String getShowSite() {
		return showSite;
	}

	public void setShowSite(String showSite) {
		this.showSite = showSite;
	}

	public BigDecimal getEdPrice() {
		return edPrice;
	}

	public void setEdPrice(BigDecimal edPrice) {
		this.edPrice = edPrice;
	}

	public Long getTiedSadeId() {
		return tiedSadeId;
	}

	public void setTiedSadeId(Long tiedSadeId) {
		this.tiedSadeId = tiedSadeId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Short getTiedSadeType() {
		return tiedSadeType;
	}

	public void setTiedSadeType(Short tiedSadeType) {
		this.tiedSadeType = tiedSadeType;
	}

}
