package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ProductRelationView implements Serializable {

	private static final long serialVersionUID = 3042545583627121560L;
	private Long skuId; // 产品skuId

	private BigDecimal oldPrice; // 产品原价格

	private BigDecimal newPrice; // 产品关联之后设置的价格（新价格）

	private BigDecimal marketPrice; // 市场价格

	private String productName; // 产品名称

	private Short relationDetailType; // 关联产品的类型

	private List<ProductImage> imgList; // 图片对象集合

	private boolean productIsValid = true;// // 判断关联的产品中有无没有上架的产品
	// 即skuStatus为0，或者状态不为 3 (上架状态)
	// 以及所关联的产品中的剩余数量是否大于零

	private String productTitle;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Short getRelationDetailType() {
		return relationDetailType;
	}

	public void setRelationDetailType(Short relationDetailType) {
		this.relationDetailType = relationDetailType;
	}

	public void setImgList(List<ProductImage> imgList) {
		this.imgList = imgList;
	}

	public boolean isProductIsValid() {
		return productIsValid;
	}

	public void setProductIsValid(boolean productIsValid) {
		this.productIsValid = productIsValid;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public List<ProductImage> getImgList() {
		return imgList;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

}
