package com.pltfm.app.vobject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 产品+SKU类 此类无实体表，主要是为了映射多表查询用
 * 在价格缓存，远程接口都有使用
 * @author luoyi
 */
public class ProductAndSku extends BaseBean implements java.io.Serializable {

	private static final long serialVersionUID = -6147915500965578709L;

	/**
	 * 类目唯一标示
	 */
	private Long categoryId;

	/**
	 * 类目唯一标示
	 */
	private Long productId;

	/**
	 * 产品备注
	 */
	private String productDesc;
	/**
	 * 产品关键词
	 */
	private String keyword;

	/**
	 * 产品热度
	 */
	private Long productHot;
	/**
	 * 产品标题
	 */
	private String productTitle;
	/**
	 * 包装清单
	 */
	private String packListing;
	/**
	 * 产品介绍(数据库Clob类型)
	 */
	private String introduce;
	/**
	 * 产品状态（草稿、待审核、待上架、下架、系统下架、上架）
	 */
	private String status;
	/**
	 * 下架时间
	 */
	private Date archiveTime;
	/**
	 * 审核人
	 */
	private Integer checkUser;
	/**
	 * 审核人姓名
	 */
	private Integer checkUserName;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 修改时间
	 */
	private Date modifTime;
	/**
	 * 修改人
	 */
	private Integer modifUser;
	/**
	 * 商铺id（预留字段）
	 */
	private String shopCode;
	/**
	 * 上架时间
	 */
	private Date upTime;
	/**
	 * 所属渠道
	 */
	private String channel;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 成本价
	 */
	private Double costPrice;
	/**
	 * sku定价
	 */
	private Double price;
	
	/**
	 * sku定价
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * sku定价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 产品编码
	 */
	private String productNo;

	/**
	 * 品牌ID
	 */
	private Long brandId;

	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 产品SKUID
	 */
	private Long productSkuId;

	/**
	 * 产品skuCode
	 */
	private String productSkuCode;

	private String approvalNo;

	private String approvalType;

	private String categoryName;

	private List<ProductSku> productSkus;

	private List<CategoryAttr> categoryAttrList;

	private List<ProductAttr> productAttrList;

	private Long[] operationAttrIds;

	/*
	 * 以下为set和get方法
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getPackListing() {
		return packListing;
	}

	public void setPackListing(String packListing) {
		this.packListing = packListing;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getModifTime() {
		return modifTime;
	}

	public void setModifTime(Date modifTime) {
		this.modifTime = modifTime;
	}

	public Integer getModifUser() {
		return modifUser;
	}

	public void setModifUser(Integer modifUser) {
		this.modifUser = modifUser;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Long getProductHot() {
		return productHot;
	}

	public void setProductHot(Long productHot) {
		this.productHot = productHot;
	}

	public Integer getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(Integer checkUser) {
		this.checkUser = checkUser;
	}

	public Integer getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(Integer checkUserName) {
		this.checkUserName = checkUserName;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public List<CategoryAttr> getCategoryAttrList() {
		return categoryAttrList;
	}

	public void setCategoryAttrList(List<CategoryAttr> categoryAttrList) {
		this.categoryAttrList = categoryAttrList;
	}

	public List<ProductAttr> getProductAttrList() {
		return productAttrList;
	}

	public void setProductAttrList(List<ProductAttr> productAttrList) {
		this.productAttrList = productAttrList;
	}

	public Long[] getOperationAttrIds() {
		return operationAttrIds;
	}

	public void setOperationAttrIds(Long[] operationAttrIds) {
		this.operationAttrIds = operationAttrIds;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<ProductSku> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	@Override
	public String toString() {
		return "ProductAndSku [categoryId=" + categoryId + ", productId="
				+ productId + ", productDesc=" + productDesc + ", keyword="
				+ keyword + ", productHot=" + productHot + ", productTitle="
				+ productTitle + ", packListing=" + packListing
				+ ", introduce=" + introduce + ", status=" + status
				+ ", archiveTime=" + archiveTime + ", checkUser=" + checkUser
				+ ", checkUserName=" + checkUserName + ", checkTime="
				+ checkTime + ", modifTime=" + modifTime + ", modifUser="
				+ modifUser + ", shopCode=" + shopCode + ", upTime=" + upTime
				+ ", channel=" + channel + ", marketPrice=" + marketPrice
				+ ", costPrice=" + costPrice + ", productNo=" + productNo
				+ ", brandId=" + brandId + ", brandName=" + brandName
				+ ", productSkuId=" + productSkuId + ", productSkuCode="
				+ productSkuCode + ", approvalNo=" + approvalNo
				+ ", approvalType=" + approvalType + ", categoryName="
				+ categoryName + ", productSkus=" + productSkus
				+ ", categoryAttrList=" + categoryAttrList
				+ ", productAttrList=" + productAttrList
				+ ", operationAttrIds=" + Arrays.toString(operationAttrIds)
				+ "]";
	}

}
