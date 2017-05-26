package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;

/**
 * 用于cms远程接口调用
 * 
 * @author hewenfeng
 * 
 */
public class ProductSkuPriceCache implements Serializable {
	private static final long serialVersionUID = 7044935363735338905L;
	// skuID
	private Long productSkuId;
	private Long productId;
	// 促销价格
	private Double promotionPrice;
	/**
	 * 商品促销标签
	 */
	private String[] promotionInfoLebal;

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public String[] getPromotionInfoLebal() {
		return promotionInfoLebal;
	}

	public void setPromotionInfoLebal(String[] promotionInfoLebal) {
		this.promotionInfoLebal = promotionInfoLebal;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
