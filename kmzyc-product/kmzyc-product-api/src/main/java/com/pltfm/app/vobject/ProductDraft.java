package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class ProductDraft implements java.io.Serializable {
    /**
     * userId 用于数据权限的过滤条件
     */
    private String userId;
    /**
	 *
	 */
    private static final long serialVersionUID = -6682401638382937130L;

    private Long productId;
    private Long categoryId;
    private String categoryName;
    private String productSubtitle;
    private String productName;
    private String productNo;
    private String packListing;
    private String productTitle;
    private String keyword;
    private Long brandId;
    private String brandName;
    private Long drugCateId;
    private String drugCateCode;
    private Long createUser;
    private Date createTime;
    private Long checkUser;
    private Date checkTime;
    private Long modifUser;
    private Date modifTime;
    private String shopCode;
    private Date upTime;
    private Date archiveTime;
    private String channel;
    private Short approvalType;
    private String approvalNo;
    private Long producthot;
    private String status;
    private Short opType;
    private String reasons;
    private String productDesc;
    private String erpProductCode;
    private String introduce;

    private String introduceLazy;

    private String priceStatus;

    private String priceReasons;

    private String imageStatus;

    private String imageReasons;

    /**
     * 产品类型
     */
    private Integer productType;

    private List<ProductSkuDraft> productSkuDrafts;

    private List<CategoryAttr> categoryAttrList;

    private List<ProductAttrDraft> productAttrDraftList = new ArrayList<ProductAttrDraft>();

    private List<ProductCertificateDraft> cerfificateList = new ArrayList<ProductCertificateDraft>();

    private Long[] operationAttrIds;

    // 商品资质认证信息
    private List<ProductCertificateFileDraft> certificateFileList = new ArrayList<>();

    private List<Short> opTypes;

    // 产品所属渠道
    private String[] ckChannel;

    /**
     * 大类标识(非持久化)
     */
    private Long bCategoryId;

    /**
     * 中类标识(非持久化)
     */
    private Long mCategoryId;

    private DrugCategory drugCategory;

    private ProdBrand prodBrand;

    private String merchantName;

    // 用于模糊查询的品牌名
    private String searchBrandName;

    private String skuStatus;

    // 由于所属商家查询
    private String supplierNameForSearch;

    /**
     * 药方属性
     */
    private List<ProductAttrDraft> prescriptionAttrDraftList;

    /**
     * 市场价
     */
    private BigDecimal price;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    //pv值  临时存放
    private BigDecimal pvValue;
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
     * 商家类型 1.自营、2第三方
     */
    private Integer supplyType;
    
     //产品类目审核 
    private String categoryAudit;

    public String getCategoryAudit() {
        return categoryAudit;
    }


    public void setCategoryAudit(String categoryAudit) {
        this.categoryAudit = categoryAudit;
    }

    /**
     * 提交审核时间
     */
    private Timestamp toCheckTime;

    private String beforeToCheckTime;

    private String afterToCheckTime;

    public void coptyPropertisFromProduct(Product product) {
        this.productId = product.getId();
        this.approvalNo = product.getApprovalNo();
        this.approvalType = StringUtils.isEmpty(product.getApprovalType()) ? null : Integer.valueOf(
                        product.getApprovalType()).shortValue();
        this.archiveTime = product.getArchiveTime();
        this.brandId = product.getBrandId();
        this.brandName = product.getBrandName();
        this.categoryId = product.getCategoryId();
        this.categoryName = product.getCategoryName();
        this.channel = product.getChannel();
        this.productName = product.getName();
        this.productNo = product.getProductNo();
        this.shopCode = product.getShopCode();
        this.productTitle = product.getProductTitle();
        this.status = product.getStatus();
        this.createTime = product.getCreateTime();
        this.createUser = product.getCreateUser().longValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.PRODUCT_ID
     *
     * @return the value of PRODUCT_DRAFT.PRODUCT_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.PRODUCT_ID
     *
     * @param productId the value for PRODUCT_DRAFT.PRODUCT_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.CATEGORY_ID
     *
     * @return the value of PRODUCT_DRAFT.CATEGORY_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.CATEGORY_ID
     *
     * @param categoryId the value for PRODUCT_DRAFT.CATEGORY_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.PROCUCT_NAME
     *
     * @return the value of PRODUCT_DRAFT.PROCUCT_NAME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.PROCUCT_NAME
     *
     * @param productName the value for PRODUCT_DRAFT.PROCUCT_NAME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setProcuctName(String productName) {
        this.productName = productName;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.PRODUCT_NO
     *
     * @return the value of PRODUCT_DRAFT.PRODUCT_NO
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getProductNo() {
        return productNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.PRODUCT_NO
     *
     * @param productNo the value for PRODUCT_DRAFT.PRODUCT_NO
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.PACK_LISTING
     *
     * @return the value of PRODUCT_DRAFT.PACK_LISTING
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getPackListing() {
        return packListing;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.PACK_LISTING
     *
     * @param packListing the value for PRODUCT_DRAFT.PACK_LISTING
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setPackListing(String packListing) {
        this.packListing = packListing;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.PRODUCT_TITLE
     *
     * @return the value of PRODUCT_DRAFT.PRODUCT_TITLE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getProductTitle() {
        return productTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.PRODUCT_TITLE
     *
     * @param productTitle the value for PRODUCT_DRAFT.PRODUCT_TITLE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.KEYWORD
     *
     * @return the value of PRODUCT_DRAFT.KEYWORD
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.KEYWORD
     *
     * @param keyword the value for PRODUCT_DRAFT.KEYWORD
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.BRAND_ID
     *
     * @return the value of PRODUCT_DRAFT.BRAND_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.BRAND_ID
     *
     * @param brandId the value for PRODUCT_DRAFT.BRAND_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.DRUG_CATE_ID
     *
     * @return the value of PRODUCT_DRAFT.DRUG_CATE_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Long getDrugCateId() {
        return drugCateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.DRUG_CATE_ID
     *
     * @param drugCateId the value for PRODUCT_DRAFT.DRUG_CATE_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setDrugCateId(Long drugCateId) {
        this.drugCateId = drugCateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.CREATE_USER
     *
     * @return the value of PRODUCT_DRAFT.CREATE_USER
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.CREATE_USER
     *
     * @param createUser the value for PRODUCT_DRAFT.CREATE_USER
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.CREATE_TIME
     *
     * @return the value of PRODUCT_DRAFT.CREATE_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.CREATE_TIME
     *
     * @param createTime the value for PRODUCT_DRAFT.CREATE_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.CHECK_USER
     *
     * @return the value of PRODUCT_DRAFT.CHECK_USER
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Long getCheckUser() {
        return checkUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.CHECK_USER
     *
     * @param checkUser the value for PRODUCT_DRAFT.CHECK_USER
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setCheckUser(Long checkUser) {
        this.checkUser = checkUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.CHECK_TIME
     *
     * @return the value of PRODUCT_DRAFT.CHECK_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.CHECK_TIME
     *
     * @param checkTime the value for PRODUCT_DRAFT.CHECK_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.MODIF_USER
     *
     * @return the value of PRODUCT_DRAFT.MODIF_USER
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Long getModifUser() {
        return modifUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.MODIF_USER
     *
     * @param modifUser the value for PRODUCT_DRAFT.MODIF_USER
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setModifUser(Long modifUser) {
        this.modifUser = modifUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.MODIF_TIME
     *
     * @return the value of PRODUCT_DRAFT.MODIF_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Date getModifTime() {
        return modifTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.MODIF_TIME
     *
     * @param modifTime the value for PRODUCT_DRAFT.MODIF_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setModifTime(Date modifTime) {
        this.modifTime = modifTime;
    }

    public String getShopCode() {
        return shopCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.SHOP_ID
     *
     * @param shopCode the value for PRODUCT_DRAFT.SHOP_ID
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.UP_TIME
     *
     * @return the value of PRODUCT_DRAFT.UP_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Date getUpTime() {
        return upTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.UP_TIME
     *
     * @param upTime the value for PRODUCT_DRAFT.UP_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.ARCHIVE_TIME
     *
     * @return the value of PRODUCT_DRAFT.ARCHIVE_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Date getArchiveTime() {
        return archiveTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.ARCHIVE_TIME
     *
     * @param archiveTime the value for PRODUCT_DRAFT.ARCHIVE_TIME
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.CHANNEL
     *
     * @return the value of PRODUCT_DRAFT.CHANNEL
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getChannel() {
        return channel;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.CHANNEL
     *
     * @param channel the value for PRODUCT_DRAFT.CHANNEL
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.APPROVAL_TYPE
     *
     * @return the value of PRODUCT_DRAFT.APPROVAL_TYPE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Short getApprovalType() {
        return approvalType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.APPROVAL_TYPE
     *
     * @param approvalType the value for PRODUCT_DRAFT.APPROVAL_TYPE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setApprovalType(Short approvalType) {
        this.approvalType = approvalType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.APPROVAL_NO
     *
     * @return the value of PRODUCT_DRAFT.APPROVAL_NO
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getApprovalNo() {
        return approvalNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.APPROVAL_NO
     *
     * @param approvalNo the value for PRODUCT_DRAFT.APPROVAL_NO
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.STATUS
     *
     * @return the value of PRODUCT_DRAFT.STATUS
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.STATUS
     *
     * @param status the value for PRODUCT_DRAFT.STATUS
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.OP_TYPE
     *
     * @return the value of PRODUCT_DRAFT.OP_TYPE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public Short getOpType() {
        return opType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.OP_TYPE
     *
     * @param opType the value for PRODUCT_DRAFT.OP_TYPE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setOpType(Short opType) {
        this.opType = opType;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.REASONS
     *
     * @return the value of PRODUCT_DRAFT.REASONS
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getReasons() {
        return reasons;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.REASONS
     *
     * @param reasons the value for PRODUCT_DRAFT.REASONS
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.PRODUCT_DESC
     *
     * @return the value of PRODUCT_DRAFT.PRODUCT_DESC
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.PRODUCT_DESC
     *
     * @param productDesc the value for PRODUCT_DRAFT.PRODUCT_DESC
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_DRAFT.INTRODUCE
     *
     * @return the value of PRODUCT_DRAFT.INTRODUCE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_DRAFT.INTRODUCE
     *
     * @param introduce the value for PRODUCT_DRAFT.INTRODUCE
     *
     * @ibatorgenerated Fri Nov 29 14:10:05 CST 2013
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getDrugCateCode() {
        return drugCateCode;
    }

    public void setDrugCateCode(String drugCateCode) {
        this.drugCateCode = drugCateCode;
    }

    public List<CategoryAttr> getCategoryAttrList() {
        return categoryAttrList;
    }

    public void setCategoryAttrList(List<CategoryAttr> categoryAttrList) {
        this.categoryAttrList = categoryAttrList;
    }

    public Long[] getOperationAttrIds() {
        return operationAttrIds;
    }

    public void setOperationAttrIds(Long[] operationAttrIds) {
        this.operationAttrIds = operationAttrIds;
    }

    public List<ProductSkuDraft> getProductSkuDrafts() {
        return productSkuDrafts;
    }

    public void setProductSkuDrafts(List<ProductSkuDraft> productSkuDrafts) {
        this.productSkuDrafts = productSkuDrafts;
    }

    public List<ProductAttrDraft> getProductAttrDraftList() {
        return productAttrDraftList;
    }

    public void setProductAttrDraftList(List<ProductAttrDraft> productAttrDraftList) {
        this.productAttrDraftList = productAttrDraftList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getProducthot() {
        return producthot;
    }

    public void setProducthot(Long producthot) {
        this.producthot = producthot;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Integer getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
        this.supplyType = supplyType;
    }

    public Timestamp getToCheckTime() {
        return toCheckTime;
    }

    public void setToCheckTime(Timestamp toCheckTime) {
        this.toCheckTime = toCheckTime;
    }

    public String getBeforeToCheckTime() {
        return beforeToCheckTime;
    }

    public void setBeforeToCheckTime(String beforeToCheckTime) {
        this.beforeToCheckTime = beforeToCheckTime;
    }

    public String getAfterToCheckTime() {
        return afterToCheckTime;
    }

    public void setAfterToCheckTime(String afterToCheckTime) {
        this.afterToCheckTime = afterToCheckTime;
    }

    public List<Short> getOpTypes() {
        return opTypes;
    }

    public void setOpTypes(List<Short> opTypes) {
        this.opTypes = opTypes;
    }

    public String getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(String priceStatus) {
        this.priceStatus = priceStatus;
    }

    public String getPriceReasons() {
        return priceReasons;
    }

    public void setPriceReasons(String priceReasons) {
        this.priceReasons = priceReasons;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getImageReasons() {
        return imageReasons;
    }

    public void setImageReasons(String imageReasons) {
        this.imageReasons = imageReasons;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public List<ProductCertificateDraft> getCerfificateList() {
        return cerfificateList;
    }

    public void setCerfificateList(List<ProductCertificateDraft> cerfificateList) {
        this.cerfificateList = cerfificateList;
    }

    public String getSearchBrandName() {
        return searchBrandName;
    }

    public void setSearchBrandName(String searchBrandName) {
        this.searchBrandName = searchBrandName;
    }

    public String[] getCkChannel() {
        return ckChannel;
    }

    public void setCkChannel(String[] ckChannel) {
        this.ckChannel = ckChannel;
    }

    public String getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(String skuStatus) {
        this.skuStatus = skuStatus;
    }

    public String getSupplierNameForSearch() {
        return supplierNameForSearch;
    }

    public void setSupplierNameForSearch(String supplierNameForSearch) {
        this.supplierNameForSearch = supplierNameForSearch;
    }

    public List<ProductAttrDraft> getPrescriptionAttrDraftList() {
        return prescriptionAttrDraftList;
    }

    public void setPrescriptionAttrDraftList(List<ProductAttrDraft> prescriptionAttrDraftList) {
        this.prescriptionAttrDraftList = prescriptionAttrDraftList;
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

    public BigDecimal getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(BigDecimal unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getSellerSkuCode() {
        return sellerSkuCode;
    }

    public void setSellerSkuCode(String sellerSkuCode) {
        this.sellerSkuCode = sellerSkuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ProductCertificateFileDraft> getCertificateFileList() {
        return certificateFileList;
    }

    public void setCertificateFileList(List<ProductCertificateFileDraft> certificateFileList) {
        this.certificateFileList = certificateFileList;
    }

    public BigDecimal getPvValue() {
        return pvValue;
    }

    public void setPvValue(BigDecimal pvValue) {
        this.pvValue = pvValue;
    }
}