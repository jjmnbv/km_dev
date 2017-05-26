package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.pltfm.app.vobject.DrugCategory;
import com.pltfm.app.vobject.ViewSkuAttr;

public class KMCloudProductData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 71710086739216517L;
	private Long productSkuId; // 产品skuId
	private Long categoryId; // 物理类目唯一性标示
	private Long productId; // 产品ID
	private Long drugCateCode;// 品类编号
	private Long drugCateId;// 品类ID
	private String productSkuCode; // 产品sku编码
	private String productTypeCN;// 产品类别
	private Long productType; // 产品类型(0：非药品 1：OTC药品 2：医疗器械)
	private String productName; // 产品名称
	private String productTitle; // 产品标题
	private String productSubtitle; // 产品副标题
	private String brandName; // 品牌
	private String keyword; // 关键字
	private String packListing; // 包装清单
	private BigDecimal salePrice; // 销售价
	private BigDecimal marketPrice; // 市场价
	private String status; // 状态(0:草稿、1:待审核、2:待上架、4:下架、5:系统下架、3:上架 6: 审核不通过, -1
							// :删除, -2违规下架)
	private String createTime; // sku创建时间
	private String modifTime; // sku修改时间
	private String skuImgPath; // sku图片路径

	private List<ViewSkuAttr> viewSkuAttrs; // sku属性,规格等等
	private DrugCategory drugCategory;
	private BigDecimal promotionPrice; // 促销价

	public String getProductTypeCN() {
		return productTypeCN;
	}

	public void setProductTypeCN(String productTypeCN) {
		this.productTypeCN = productTypeCN;
	}

	public String getSkuImgPath() {
		return skuImgPath;
	}

	public void setSkuImgPath(String skuImgPath) {
		this.skuImgPath = skuImgPath;
	}

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
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

	public String getProductSubtitle() {
		return productSubtitle;
	}

	public void setProductSubtitle(String productSubtitle) {
		this.productSubtitle = productSubtitle;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPackListing() {
		return packListing;
	}

	public void setPackListing(String packListing) {
		this.packListing = packListing;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModifTime() {
		return modifTime;
	}

	public void setModifTime(String modifTime) {
		this.modifTime = modifTime;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public Long getProductType() {
		return productType;
	}

	public void setProductType(Long productType) {
		this.productType = productType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getDrugCateCode() {
		return drugCateCode;
	}

	public void setDrugCateCode(Long drugCateCode) {
		this.drugCateCode = drugCateCode;
	}

	public Long getDrugCateId() {
		return drugCateId;
	}

	public void setDrugCateId(Long drugCateId) {
		this.drugCateId = drugCateId;
	}

	public DrugCategory getDrugCategory() {
		return drugCategory;
	}

	public void setDrugCategory(DrugCategory drugCategory) {
		this.drugCategory = drugCategory;
	}

	public List<ViewSkuAttr> getViewSkuAttrs() {
		return viewSkuAttrs;
	}

	public void setViewSkuAttrs(List<ViewSkuAttr> viewSkuAttrs) {
		this.viewSkuAttrs = viewSkuAttrs;
	}

	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

}
