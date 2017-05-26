package com.kmzyc.supplier.model;

import com.kmzyc.supplier.model.ShopCategorys;
import com.pltfm.app.vobject.Product;

import java.io.Serializable;


public class ShopProductCategory implements Serializable {

    private static final long serialVersionUID = -1682441506134050907L;

    private Long id;

    private Long shopCategoryId;

    private Long productId;

    /**
     * 未修改之前的店内分类ID
     */
    private long originalCategoryId;

    private ShopCategorys shopCategory;

    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ShopCategorys getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategorys shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getOriginalCategoryId() {
        return originalCategoryId;
    }

    public void setOriginalCategoryId(long originalCategoryId) {
        this.originalCategoryId = originalCategoryId;
    }

}