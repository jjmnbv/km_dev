package com.kmzyc.supplier.vo;

import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProductDraft;

import java.io.Serializable;
import java.util.List;

public class ProductDraftVo implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1182946675673131790L;

	private ProductDraft product;
	
	private List<Category> categoryList;

	public ProductDraft getProduct() {
		return product;
	}

	public void setProduct(ProductDraft product) {
		this.product = product;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

}
