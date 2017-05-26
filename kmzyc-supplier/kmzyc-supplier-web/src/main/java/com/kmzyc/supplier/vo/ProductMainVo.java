package com.kmzyc.supplier.vo;

import com.kmzyc.supplier.model.ShopCategorys;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.Product;

import java.io.Serializable;
import java.util.List;

public class ProductMainVo implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1182946675673131790L;

	private Product product;
	
	private List<Category> categoryList;
	
	/**
	 * maliqun add 存储产品的店内分类
	 */
	private List<ShopCategorys> shopCategorys;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<ShopCategorys> getShopCategorys() {
		return shopCategorys;
	}

	public void setShopCategorys(List<ShopCategorys> shopCategorys) {
		this.shopCategorys = shopCategorys;
	}

}
