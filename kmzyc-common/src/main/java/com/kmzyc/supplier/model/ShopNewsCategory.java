package com.kmzyc.supplier.model;

import java.io.Serializable;

public class ShopNewsCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long sncId;
	public Long shopId;
	public Long newsCategoryId;
	
	public ShopMain shopMain;
	public NewsCategory newsCategory;
	
	public Long getSncId() {
		return sncId;
	}
	public void setSncId(Long sncId) {
		this.sncId = sncId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getNewsCategoryId() {
		return newsCategoryId;
	}
	public void setNewsCategoryId(Long newsCategoryId) {
		this.newsCategoryId = newsCategoryId;
	}
	public ShopMain getShopMain() {
		return shopMain;
	}
	public void setShopMain(ShopMain shopMain) {
		this.shopMain = shopMain;
	}
	public NewsCategory getNewsCategory() {
		return newsCategory;
	}
	public void setNewsCategory(NewsCategory newsCategory) {
		this.newsCategory = newsCategory;
	}

	
}
