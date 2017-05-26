package com.pltfm.app.fobject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pltfm.app.vobject.BaseBean;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.DrugCategory;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductSku;

/**
 * 产品辅助类
 * 
 * @author Administrator
 */
public class ProductResultMap extends BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3763627341457389355L;
	
	/**
	 * 产品副标题
	 */
	private String productSubtitle;

	/**
	 * 类目唯一标示
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
	
	private Integer productId;
	
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
	private String checkUserName;
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
	private BigDecimal marketPrice;
	/**
	 * 成本价
	 */
	private BigDecimal costPrice;
	
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
	 * 品类ID
	 */
	private Long drugCateId;
	
	/**
	 * 品类编号
	 */
	private String drugCateCode;
	
	/**
	 * 品类名称
	 */
	private String drugCateName;
	
	private String approvalNo;
	
	private String approvalType;
	
	private String categoryName;
	
	private String erpProductCode;
	
	private DrugCategory drugCategory;
	
	private ProdBrand prodBrand;
	
	private List<ProductSku> productSkus;
	
	private List<CategoryAttr> categoryAttrList;
	
	private List<ProductAttr> productAttrList;
	
	private Long[] operationAttrIds;
	
	private Long productskuId;
	
	private String productSkuCode;
	
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

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
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

	public String getDrugCateCode() {
		return drugCateCode;
	}

	public void setDrugCateCode(String drugCateCode) {
		this.drugCateCode = drugCateCode;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public Integer getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(Integer checkUser) {
		this.checkUser = checkUser;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public Integer getModifUser() {
		return modifUser;
	}

	public void setModifUser(Integer modifUser) {
		this.modifUser = modifUser;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getDrugCateId() {
		return drugCateId;
	}

	public void setDrugCateId(Long drugCateId) {
		this.drugCateId = drugCateId;
	}

	public Long[] getOperationAttrIds() {
		return operationAttrIds;
	}

	public void setOperationAttrIds(Long[] operationAttrIds) {
		this.operationAttrIds = operationAttrIds;
	}

	public DrugCategory getDrugCategory() {
		return drugCategory;
	}

	public void setDrugCategory(DrugCategory drugCategory) {
		this.drugCategory = drugCategory;
	}

	public ProdBrand getProdBrand() {
		return prodBrand;
	}

	public void setProdBrand(ProdBrand prodBrand) {
		this.prodBrand = prodBrand;
	}

	public String getDrugCateName() {
		return drugCateName;
	}

	public void setDrugCateName(String drugCateName) {
		this.drugCateName = drugCateName;
	}

	public String getErpProductCode() {
		return erpProductCode;
	}

	public void setErpProductCode(String erpProductCode) {
		this.erpProductCode = erpProductCode;
	}

	public Long getProductskuId() {
		return productskuId;
	}

	public void setProductskuId(Long productskuId) {
		this.productskuId = productskuId;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public String getProductSubtitle() {
		return productSubtitle;
	}

	public void setProductSubtitle(String productSubtitle) {
		this.productSubtitle = productSubtitle;
	}


}
