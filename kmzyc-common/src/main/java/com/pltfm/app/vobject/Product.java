package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品main类
 * 
 * @author Administrator
 */
public class Product extends BaseBean implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3763627341457389355L;

    /**
     * 产品副标题
     */
    private String productSubtitle;

    /**
     * 产品批注信息
     */
    private String postil;

    /**
     * 产品类型
     */
    private Integer productType;

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
     * 重量（临时存放）
     */
    private BigDecimal unitWeight;

    /**
     * 库存（临时存放）
     */
    private Long stock;

    /**
     * 商品货号（临时存放）
     */
    private String sellerSkuCode;

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

    private List<ProductCertificate> cerfificateList;

    private List<ProductAttr> productAttrList;

    // 商品资质认证信息
    private List<ProductCertificateFile> certificateFileList =
            new ArrayList<ProductCertificateFile>();

    private Long[] operationAttrIds;

    private String[] ckChannel;

    private String introduceLazy;

    // 用于模糊查询的品牌名
    private String searchBrandName;
    // 用于模糊查询的大类名
    private String bCategoryName;
    // 用于模糊查询的中类名
    private String mCategoryName;
    // 用于模糊查询的小类名
    private String searchCategoryName;
    // 商户名称
    private String merchantName;

    // 所属商家名称
    private String supplierNameForSearch;

    /**
     * 违规下架的原因
     */
    private String reasons;

    private List<ProductSku> productSkusForImages;// add xkj 20150803,用于供应商平台产品列表显示图片

    /**
     * 20151218 add 不做持久化,只是用作查询参数
     */
    private String productSkuCode;
    private String fanliStatus;
    private String minCommission;
    private String maxCommission;
    private int startIndex;
    private int endIndex;

    /** 20151218 add end **/

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
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

    public String getIntroduceLazy() {
        return introduceLazy;
    }

    public void setIntroduceLazy(String introduceLazy) {
        this.introduceLazy = introduceLazy;
    }

    public String getSearchBrandName() {
        return searchBrandName;
    }

    public void setSearchBrandName(String searchBrandName) {
        this.searchBrandName = searchBrandName;
    }

    public String getBCategoryName() {
        return bCategoryName;
    }

    public void setBCategoryName(String bCategoryName) {
        this.bCategoryName = bCategoryName;
    }

    public String getMCategoryName() {
        return mCategoryName;
    }

    public void setMCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public String getSearchCategoryName() {
        return searchCategoryName;
    }

    public void setSearchCategoryName(String searchCategoryName) {
        this.searchCategoryName = searchCategoryName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getPostil() {
        return postil;
    }

    public void setPostil(String postil) {
        this.postil = postil;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public List<ProductCertificate> getCerfificateList() {
        return cerfificateList;
    }

    public void setCerfificateList(List<ProductCertificate> cerfificateList) {
        this.cerfificateList = cerfificateList;
    }

    public String[] getCkChannel() {
        return ckChannel;
    }

    public void setCkChannel(String[] ckChannel) {
        this.ckChannel = ckChannel;
    }

    private Integer suppliterType;


    public Integer getSuppliterType() {
        return suppliterType;
    }

    public void setSuppliterType(Integer suppliterType) {
        this.suppliterType = suppliterType;
    }

    public String getSupplierNameForSearch() {
        return supplierNameForSearch;
    }

    public void setSupplierNameForSearch(String supplierNameForSearch) {
        this.supplierNameForSearch = supplierNameForSearch;
    }

    public List<ProductSku> getProductSkusForImages() {
        return productSkusForImages;
    }

    public void setProductSkusForImages(List<ProductSku> productSkusForImages) {
        this.productSkusForImages = productSkusForImages;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public BigDecimal getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(BigDecimal unitWeight) {
        this.unitWeight = unitWeight;
    }

    public String getSellerSkuCode() {
        return sellerSkuCode;
    }

    public void setSellerSkuCode(String sellerSkuCode) {
        this.sellerSkuCode = sellerSkuCode;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }


    public Long getbCategoryId() {
        return bCategoryId;
    }

    public void setbCategoryId(Long bCategoryId) {
        this.bCategoryId = bCategoryId;
    }

    public Long getmCategoryId() {
        return mCategoryId;
    }

    public void setmCategoryId(Long mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getFanliStatus() {
        return fanliStatus;
    }

    public void setFanliStatus(String fanliStatus) {
        this.fanliStatus = fanliStatus;
    }

    public String getMinCommission() {
        return minCommission;
    }

    public void setMinCommission(String minCommission) {
        this.minCommission = minCommission;
    }

    public String getMaxCommission() {
        return maxCommission;
    }

    public void setMaxCommission(String maxCommission) {
        this.maxCommission = maxCommission;
    }

    public List<ProductCertificateFile> getCertificateFileList() {
        return certificateFileList;
    }

    public void setCertificateFileList(List<ProductCertificateFile> certificateFileList) {
        this.certificateFileList = certificateFileList;
    }

}
