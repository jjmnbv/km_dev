package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRelationDetail implements Serializable {

    private static final long serialVersionUID = -5070840407573389653L;

    private Long relationDetailId;

    private Long relationId;

    private Long relationSkuId;

    private BigDecimal relationSkuPrice;

    private Short relationDetailType;
    
    private Short productCount;//商品数量
    
    private ProductSku productSku;

    private BigDecimal skuPrice;//sku实际价格

    private BigDecimal skuMarkPrice;//sku市场价格

    private Long skuStatus;//sku状态

    private String productName;//对应的商品名称

    private String productTitle;//商品标题

    private Long productStatus;//商品状态

    public Long getRelationDetailId() {
        return relationDetailId;
    }

    public void setRelationDetailId(Long relationDetailId) {
        this.relationDetailId = relationDetailId;
    }

    public BigDecimal getRelationSkuPrice() {
        return relationSkuPrice;
    }

    public void setRelationSkuPrice(BigDecimal relationSkuPrice) {
        this.relationSkuPrice = relationSkuPrice;
    }

    public Short getRelationDetailType() {
        return relationDetailType;
    }

    public void setRelationDetailType(Short relationDetailType) {
        this.relationDetailType = relationDetailType;
    }

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getRelationSkuId() {
		return relationSkuId;
	}

	public void setRelationSkuId(Long relationSkuId) {
		this.relationSkuId = relationSkuId;
	}

	public Short getProductCount() {
		return productCount;
	}

	public void setProductCount(Short productCount) {
		this.productCount = productCount;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public BigDecimal getSkuMarkPrice() {
        return skuMarkPrice;
    }

    public void setSkuMarkPrice(BigDecimal skuMarkPrice) {
        this.skuMarkPrice = skuMarkPrice;
    }

    public Long getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(Long skuStatus) {
        this.skuStatus = skuStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Long productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "ProductRelationDetail{" +
                "relationDetailId=" + relationDetailId +
                ", relationId=" + relationId +
                ", relationSkuId=" + relationSkuId +
                ", relationSkuPrice=" + relationSkuPrice +
                ", relationDetailType=" + relationDetailType +
                ", productCount=" + productCount +
                ", productSku=" + productSku +
                ", skuPrice=" + skuPrice +
                ", skuMarkPrice=" + skuMarkPrice +
                ", skuStatus=" + skuStatus +
                ", productName='" + productName + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productStatus=" + productStatus +
                '}';
    }
}