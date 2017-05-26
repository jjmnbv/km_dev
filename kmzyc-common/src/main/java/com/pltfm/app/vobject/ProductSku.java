package com.pltfm.app.vobject;

import java.util.List;


public class ProductSku implements java.io.Serializable {
    
	private static final long serialVersionUID = 2982742963453984415L;

    private Long productSkuId;

    private Long productId;

    private Double price;

    private String productSkuCode;
    
	private Double unitWeight;
	
	private Double markPrice;
	
	private Double costPrice;
	
	private Double pvValue;
	
	/**
	 * 合作方收益比
	 */
	private Double costIncomeRatio;
    
    private String status;
    
    private String productName;
    private String productTitle;
    
    private Double freight;
    
    private String freeStatus;
    
    private Long categoryId;
    
    private Long brandId;
    
    private String channel;
    
    private List<ProductSkuAttr> productSkuAttrList;
    
    private List<ProductImage> productSkuImages;
    
    private int skip;
    private int max;
	//上架状态
    private String upStatus;
    
    private String skuIntroduce;
    
    private String skuIntroduceLazy;
    
    private Long warehouseId;
    
    private String warehouseName;
    
    private String skuAttrs;
    
    private Long stockQuantity;//用于供应商平台显示库存（临时）
    
    private String sellerSkuCode;
    
    private Long stock;//临时存放库存（无意义）
    
    private String viewImgPath;//供应商展示图片
    
    private String skuBarCode;	//商品条形码
    
  /**
   * 上架状态
   * DRAFT("0","草稿"),
	UNAUDIT("1","待审核"),
	AUDIT("2","已审核,待上架"),
	UP("3","已上架"),
	DOWN("4","已下架"),
	SYSDOWN("5","系统下架");
   * @return
   */
	public String getUpStatus() {
		return upStatus;
	}
	 /**
	   * 上架状态
	   * DRAFT("0","草稿"),
	UNAUDIT("1","待审核"),
	AUDIT("2","已审核,待上架"),
	UP("3","已上架"),
	DOWN("4","已下架"),
	SYSDOWN("5","系统下架");
	   * @return
	   */
	public void setUpStatus(String upStatus) {
		this.upStatus = upStatus;
	}
	public int getSkip() {
	    return skip;
	}
	public void setSkip(int skip) {
	    this.skip = skip;
	}
	
	public int getMax() {
	    return max;
	}
	public void setMax(int max) {
	    this.max = max;
	}

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @return the value of PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public Long getProductSkuId() {
        return productSkuId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @param productSkuId the value for PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column PRODUCT_SKU.PRODUCT_ID
     *
     * @return the value of PRODUCT_SKU.PRODUCT_ID
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column PRODUCT_SKU.PRODUCT_ID
     *
     * @param productId the value for PRODUCT_SKU.PRODUCT_ID
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column PRODUCT_SKU.PRICE
     *
     * @return the value of PRODUCT_SKU.PRICE
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column PRODUCT_SKU.PRICE
     *
     * @param price the value for PRODUCT_SKU.PRICE
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @return the value of PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public String getProductSkuCode() {
        return productSkuCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @param productSkuCode the value for PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @ibatorgenerated Tue Aug 13 11:29:28 CST 2013
     */
    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

	public List<ProductSkuAttr> getProductSkuAttrList() {
		return productSkuAttrList;
	}

	public void setProductSkuAttrList(List<ProductSkuAttr> productSkuAttrList) {
		this.productSkuAttrList = productSkuAttrList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}
	public List<ProductImage> getProductSkuImages() {
		return productSkuImages;
	}
	public void setProductSkuImages(List<ProductImage> productSkuImages) {
		this.productSkuImages = productSkuImages;
	}
	
	/**
	 * 商品
	 */
	private Product product;
	/**
	 * 商品
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * 商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	public Double getMarkPrice() {
		return markPrice;
	}
	public void setMarkPrice(Double markPrice) {
		this.markPrice = markPrice;
	}
	/**渠道*/
	public String getChannel() {
		return channel;
	}
	/**渠道*/
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public String getSkuIntroduce() {
		return skuIntroduce;
	}
	public void setSkuIntroduce(String skuIntroduce) {
		this.skuIntroduce = skuIntroduce;
	}
	public String getSkuIntroduceLazy() {
		return skuIntroduceLazy;
	}
	public void setSkuIntroduceLazy(String skuIntroduceLazy) {
		this.skuIntroduceLazy = skuIntroduceLazy;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	private Promotion promotion;

	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public String getSkuAttrs() {
		return skuAttrs;
	}
	public void setSkuAttrs(String skuAttrs) {
		this.skuAttrs = skuAttrs;
	}
	public Long getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public Double getPvValue() {
		return pvValue;
	}
	public void setPvValue(Double pvValue) {
		this.pvValue = pvValue;
	}
	public Double getCostIncomeRatio() {
		return costIncomeRatio;
	}
	public void setCostIncomeRatio(Double costIncomeRatio) {
		this.costIncomeRatio = costIncomeRatio;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public String getFreeStatus() {
		return freeStatus;
	}
	public void setFreeStatus(String freeStatus) {
		this.freeStatus = freeStatus;
	}
	public String getSellerSkuCode() {
		return sellerSkuCode;
	}
	public void setSellerSkuCode(String sellerSkuCode) {
		this.sellerSkuCode = sellerSkuCode;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public String getViewImgPath() {
		return viewImgPath;
	}
	public void setViewImgPath(String viewImgPath) {
		this.viewImgPath = viewImgPath;
	}
	public String getSkuBarCode() {
		return skuBarCode;
	}
	public void setSkuBarCode(String skuBarCode) {
		this.skuBarCode = skuBarCode;
	}
	
}