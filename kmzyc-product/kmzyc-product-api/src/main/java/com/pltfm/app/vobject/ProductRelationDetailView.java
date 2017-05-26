package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.List;

public class ProductRelationDetailView {

	
	
	 private Long relationDetailId;  // 关联子表的主键id
	
	 private BigDecimal price;	//sku  价格
	
	 private String productSkuCode;  //skuCode
	
	 private String procuctName;  //产品名称
	
	 private String channel; //渠道
	
	 private String productNo;  // 产品编号
	 
	 private String brandName;  //品牌名称
	 private ProdBrand prodBrand;
	 
	 private BigDecimal  newPrice;// 产品设定的新价格
	 
	 private int  remainingQuantity ; // 剩余数量
	 private Long skuId ; // 产品skuId

	 private int   status; //产品的状态
	 
	 private String productTitle;//产品标题
	 private List<ProductSku> productSkus;//产品sku描述
	 private Short productCount;//商品数量
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public String getProcuctName() {
		return procuctName;
	}

	public void setProcuctName(String procuctName) {
		this.procuctName = procuctName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}

	public Long getRelationDetailId() {
		return relationDetailId;
	}

	public void setRelationDetailId(Long relationDetailId) {
		this.relationDetailId = relationDetailId;
	}

	public int getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public List<ProductSku> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}

	public Short getProductCount() {
		return productCount;
	}

	public void setProductCount(Short productCount) {
		this.productCount = productCount;
	}

	public ProdBrand getProdBrand() {
		return prodBrand;
	}

	public void setProdBrand(ProdBrand prodBrand) {
		this.prodBrand = prodBrand;
	}
	 
	 
	 
}
