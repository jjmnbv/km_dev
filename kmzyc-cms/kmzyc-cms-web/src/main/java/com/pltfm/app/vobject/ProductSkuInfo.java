package com.pltfm.app.vobject;

import java.io.Serializable;

public class ProductSkuInfo implements Serializable {
    private static final long serialVersionUID = -6096113746832307147L;
    private Long skuId;
    private String categoryAtrr;


    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getCategoryAtrr() {
        return categoryAtrr;
    }

    public void setCategoryAtrr(String categoryAtrr) {
        this.categoryAtrr = categoryAtrr;
    }


}
