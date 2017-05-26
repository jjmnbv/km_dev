package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.kmzyc.promotion.app.vobject.ProductErpRelation;

public class ViewProductSku implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1221127634778556968L;
    // 解决新版优惠券产品选择冲突新加标记1：新版优惠券
    private int editCode;

    // 新增个SUPPLIER_TYPE
    private Integer supplierType;

    // 新增shopCode
    private String shopCode;

    // 产品实际库存
    private Long stockQuality;
    // 品牌
    private String brandName;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.PRODUCT_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private Long productId;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.PROCUCT_NAME
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private String procuctName;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.PRODUCT_NO
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private String productNo;

    private String productTitle;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.STATUS
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.MARKET_PRICE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private BigDecimal marketPrice;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.COST_PRICE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private BigDecimal costPrice;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.CATEGORY_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private Long categoryId;

    /**
     * 大类标识(非持久化)
     */
    private Long bCategoryId;

    /**
     * 中类标识(非持久化)
     */
    private Long mCategoryId;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.CATEGORY_NAME
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private String categoryName;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private Long productSkuId;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    private String productSkuCode;

    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column VIEW_PRODUCT_SKU.SKU_STATUS
     * 
     * @ibatorgenerated Wed Sep 11 13:50:50 CST 2013
     */
    private String skuStatus;

    private BigDecimal price;

    private Double unitWeight;

    private Double markPrice;

    private Double skuCostPrice;

    private Double pvValue;

    private Double costIncomeRatio;

    private Double freight;

    private String freeStatus;

    private List<ViewSkuAttr> viewSkuAttrs;

    // 用于模糊查询的品牌名
    private String searchBrandName;

    // 保存该Sku的操作类型(草稿,修改所有信息,单独修改价格)20140725 maliqun
    private Short opType;

    /**
     * 供应链对应编码关系
     */
    private ProductErpRelation productErpRelation;

    private String erpProCode;

    private Long correspondingStatus;

    private String systemCode;

    private Integer userId;

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.PRODUCT_ID
     *
     * @return the value of VIEW_PRODUCT_SKU.PRODUCT_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.PRODUCT_ID
     *
     * @param productId the value for VIEW_PRODUCT_SKU.PRODUCT_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.PROCUCT_NAME
     *
     * @return the value of VIEW_PRODUCT_SKU.PROCUCT_NAME
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public String getProcuctName() {
        return procuctName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.PROCUCT_NAME
     *
     * @param procuctName the value for VIEW_PRODUCT_SKU.PROCUCT_NAME
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setProcuctName(String procuctName) {
        this.procuctName = procuctName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.PRODUCT_NO
     *
     * @return the value of VIEW_PRODUCT_SKU.PRODUCT_NO
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.PRODUCT_NO
     *
     * @param productNo the value for VIEW_PRODUCT_SKU.PRODUCT_NO
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.STATUS
     *
     * @return the value of VIEW_PRODUCT_SKU.STATUS
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.STATUS
     *
     * @param status the value for VIEW_PRODUCT_SKU.STATUS
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.MARKET_PRICE
     *
     * @return the value of VIEW_PRODUCT_SKU.MARKET_PRICE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.MARKET_PRICE
     *
     * @param marketPrice the value for VIEW_PRODUCT_SKU.MARKET_PRICE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.COST_PRICE
     *
     * @return the value of VIEW_PRODUCT_SKU.COST_PRICE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.COST_PRICE
     *
     * @param costPrice the value for VIEW_PRODUCT_SKU.COST_PRICE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.CATEGORY_ID
     *
     * @return the value of VIEW_PRODUCT_SKU.CATEGORY_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.CATEGORY_ID
     *
     * @param categoryId the value for VIEW_PRODUCT_SKU.CATEGORY_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.CATEGORY_NAME
     *
     * @return the value of VIEW_PRODUCT_SKU.CATEGORY_NAME
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.CATEGORY_NAME
     *
     * @param categoryName the value for VIEW_PRODUCT_SKU.CATEGORY_NAME
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @return the value of VIEW_PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public Long getProductSkuId() {
        return productSkuId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @param productSkuId the value for VIEW_PRODUCT_SKU.PRODUCT_SKU_ID
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column VIEW_PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @return the value of VIEW_PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public String getProductSkuCode() {
        return productSkuCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column VIEW_PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @param productSkuCode the value for VIEW_PRODUCT_SKU.PRODUCT_SKU_CODE
     *
     * @ibatorgenerated Thu Aug 01 17:27:14 CST 2013
     */
    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public List<ViewSkuAttr> getViewSkuAttrs() {
        return viewSkuAttrs;
    }

    public void setViewSkuAttrs(List<ViewSkuAttr> viewSkuAttrs) {
        this.viewSkuAttrs = viewSkuAttrs;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(String skuStatus) {
        this.skuStatus = skuStatus;
    }

    public Double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Double getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(Double markPrice) {
        this.markPrice = markPrice;
    }

    public Double getSkuCostPrice() {
        return skuCostPrice;
    }

    public void setSkuCostPrice(Double skuCostPrice) {
        this.skuCostPrice = skuCostPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getBCategoryId() {
        return bCategoryId;
    }

    public void setBCategoryId(Long bCategoryId) {
        this.bCategoryId = bCategoryId;
    }

    public Long getMCategoryId() {
        return mCategoryId;
    }

    public void setMCategoryId(Long mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public String getSearchBrandName() {
        return searchBrandName;
    }

    public void setSearchBrandName(String searchBrandName) {
        this.searchBrandName = searchBrandName;
    }

    public Short getOpType() {
        return opType;
    }

    public void setOpType(Short opType) {
        this.opType = opType;
    }

    public Integer getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Integer supplierType) {
        this.supplierType = supplierType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public ProductErpRelation getProductErpRelation() {
        return productErpRelation;
    }

    public void setProductErpRelation(ProductErpRelation productErpRelation) {
        this.productErpRelation = productErpRelation;
    }

    public int getEditCode() {
        return editCode;
    }

    public void setEditCode(int editCode) {
        this.editCode = editCode;
    }

    public String getErpProCode() {
        return erpProCode;
    }

    public void setErpProCode(String erpProCode) {
        this.erpProCode = erpProCode;
    }

    public Long getCorrespondingStatus() {
        return correspondingStatus;
    }

    public void setCorrespondingStatus(Long correspondingStatus) {
        this.correspondingStatus = correspondingStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
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

    public Long getStockQuality() {
        return stockQuality;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setStockQuality(Long stockQuality) {
        this.stockQuality = stockQuality;
    }

}
