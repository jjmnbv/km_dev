package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.supplier.model.SuppliersInfo;
import com.pltfm.cms.vobject.CmsProductRelation;
import com.pltfm.cms.vobject.CommercialTenantBasicInfo;
import com.pltfm.cms.vobject.ProductCertificateFile;
import com.pltfm.cms.vobject.SuppliersCertificateInfo;

/**
 * 产品视图实体类
 *
 * @author cjm
 * @since 2013-9-18
 */
public class ViewProductInfo implements Serializable {


    public Integer getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(Integer saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    private static final long serialVersionUID = -7900596805019265235L;
    /**
     * 开始索引值
     **/
    private Integer startIndex;
    /**
     * 结束索引值
     **/
    private Integer endIndex;
    /**
     * 套餐组合信息
     **/
    private List<CmsProductRelation> cmsRelationList;
    /**
     * SKU描述
     **/
    private String skudesc;//

    /**
     * 产品图片集合
     **/
    private List<ProductImage> productImageList;
    /**
     * 产品属性关联集合
     **/
    private List<ProductAttr> productAttrList;
    /**
     * 产品详细集合*
     */
    private List<ViewProductDetailInfo> viewProductDetailInfoList;
    private List<ViewSkuAttr> productDetailInfo;
    /**
     * 产品药品信息集合
     **/
    private List<ViewSkuAttr> productLists;
    /**
     * 产品sku信息集合
     **/
    private List<CategoryAttr> categoryAttrList;
    /**
     * 产品sku信息默认值存放集合
     **/
    private List<ViewSkuAttr> viewSkuAttrList;
    /**
     * 产品标签集合
     */
    private List<String> productTags;
    /**
     * 产品自动标签集合
     **/
    private List<String> productAutoTags;
    /**
     * 不同sku所对应的sku信息值
     **/
    private List<ProductSkuInfo> skuInfoList;
    /**
     * 产品优惠套餐组合信息
     **/
    private List<ProductRelation> relationList;
    /**
     * 产品人气组合信息
     **/
    private List<ProductRelation> packageList;
    /**
     * 产品品牌信息
     **/
    private ProdBrand prodBrand;
    /**
     * 产品同类其他品牌信息
     **/
    private List<ProdBrand> otherBrandList;
    /**
     * 是否拥有到货通知属性
     **/
    private Integer messageFlag;
    /**
     * sku对象
     **/
    private ProductSku productSku;
    //供应商信息
    private CommercialTenantBasicInfo commercialTenantBasicInfo;
    private CategoryDetailInfo categoryDetail;
    private Date upTime;
    private Integer productSkuId;

    private List productSkuIdSort;//skuid排序
    private Integer productId;
    private Integer categoryId; //三级类目
    private Integer bcategoryId;//一级类目
    private Integer mcategoryId;//二级类目
    private String categoryName;
    private String procuctName;
    private String productTitle;//主标题
    private String productSubtitle;//副标题
    private String keyword;
    private Integer producthot;
    private String shopCode;
    private Double marketPrice;
    private String productNo;
    private Integer brandId;
    private String brandName;
    private Short approvalType;
    private String approvalNo;
    private Integer productType;
    private Integer num;//评价数量
    private Integer point;//平均分
    private Integer saleQuantity;//销量
    private Double pvValue;//
    private Double costIncomeratio;//
    private Double freight;//运费
    private String freeStatus;//是否包邮(0不包邮，1包邮）

    //产品评价信息
    private Map<String, Object> productAppraiseMap;


    //预售价
    private Double presellPrice;
    //定金
    private Double depositPrice;
    //尾款
    private Double finalPrice;
    //支付定金时间段
    private Date depositStartTime;
    private Date depositEndTime;
    //尾款支付时间段
    private Date finalpayStartTime;
    private Date finalpayEndTime;
    //发货时间段
    private Date deliveryStartTime;
    private Date deliveryEndTime;


    // WAREHOUSE_NAME,SKU_INTRODUCE,BRAND_INTRODUCE
    //仓库名
    private String warehouseName;
    //商品介绍
    private String skuIntroduce;
    //品牌的介绍
    private String brandIntroduct;

    private Short supplierType;//类型
    private Short serviceType;


    private String corporateName; //公司名
    private String serviceQq;


    private Long shopId; //店ID


    private ViewShopMain shopMain;//店铺
    private SuppliersInfo suppliers;//供应商
    private Long supplierId;//供应商ID
    private String shopName;//供应商名
    private Long commercialTenantId;//关联用户ID

    /**
     * 产品Id集合
     */
    private List productIds;
    /**
     * 产品SkuId集合
     */
    private List productSkuIds;

    /**
     * 供应商电话
     */
    private String fixedPhone;

    /**
     * 供应商手机号
     */
    private String mobile;
    /**
     * 供应商所在地
     */
    private String corporateLocation;


    private String productSkuCode;
    private Double price;
    private Integer promotionId;
    private String productDesc;// 产品备注
    /**
     * 页面参数
     **/
    private Integer startProductId;
    private Integer endProductId;
    private String introduce;
    private String productFilterSql;
    private String category;// 类目
    private Double promotionPrice;// 活动价
    private Date promotionsEndDate;//活动截止时间
    private Integer windowId;// 窗口ID
    private Integer flag;// 标志

    private Integer windowDataId;// 窗口数据主键ID
    private Integer sort;//窗口数据排序
    private String userDefinedName;//自定义名称

    private Integer shopCategoryId;//店铺类目id
    private Category categoryObj;
    private String productNameOrCode;

    private String imgPath7;
    private String imgPath8;
    private String imgPath5;

    /**
     * 产品正式资质文件
     */
    private List<ProductCertificateFile> productCertificateFiles;
    private String advertApprovalNo;
    /**
     * 供应商资质信息
     */
    private List<SuppliersCertificateInfo> suppliersCertificateInfos;
    /**
     * 供应商资质重审信息
     */
    private com.pltfm.cms.vobject.SuppliersRecheck suppliersRecheck;

    public List<ProductCertificateFile> getProductCertificateFiles() {
        return productCertificateFiles;
    }

    public void setProductCertificateFiles(List<ProductCertificateFile> productCertificateFiles) {
        this.productCertificateFiles = productCertificateFiles;
        if (productCertificateFiles != null && productCertificateFiles.size() > 0) {
            this.advertApprovalNo = productCertificateFiles.get(0).getAdvertApprovalNo();
        }
    }

    public String getAdvertApprovalNo() {
        return advertApprovalNo;
    }

    public void setAdvertApprovalNo(String advertApprovalNo) {
        this.advertApprovalNo = advertApprovalNo;
    }

    public List<SuppliersCertificateInfo> getSuppliersCertificateInfos() {
        return suppliersCertificateInfos;
    }

    public void setSuppliersCertificateInfos(List<SuppliersCertificateInfo> suppliersCertificateInfos) {
        this.suppliersCertificateInfos = suppliersCertificateInfos;
    }

    public com.pltfm.cms.vobject.SuppliersRecheck getSuppliersRecheck() {
        return suppliersRecheck;
    }

    public void setSuppliersRecheck(com.pltfm.cms.vobject.SuppliersRecheck suppliersRecheck) {
        this.suppliersRecheck = suppliersRecheck;
    }

    public Double getPvValue() {
        return pvValue;
    }

    public void setPvValue(Double pvValue) {
        this.pvValue = pvValue;
    }

    public Integer getBcategoryId() {
        return bcategoryId;
    }

    public void setBcategoryId(Integer bcategoryId) {
        this.bcategoryId = bcategoryId;
    }

    public Integer getMcategoryId() {
        return mcategoryId;
    }

    public void setMcategoryId(Integer mcategoryId) {
        this.mcategoryId = mcategoryId;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public Long getShopId() {
        return shopId;
    }

    public String getServiceQq() {
        return serviceQq;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public SuppliersInfo getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(SuppliersInfo suppliers) {
        this.suppliers = suppliers;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }


    public ViewShopMain getShopMain() {
        return shopMain;
    }

    public void setShopMain(ViewShopMain shopMain) {
        this.shopMain = shopMain;
    }

    public void setServiceQq(String serviceQq) {
        this.serviceQq = serviceQq;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<ViewProductDetailInfo> getViewProductDetailInfoList() {
        return viewProductDetailInfoList;
    }

    public void setViewProductDetailInfoList(
            List<ViewProductDetailInfo> viewProductDetailInfoList) {
        this.viewProductDetailInfoList = viewProductDetailInfoList;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    public String getCorporateLocation() {
        return corporateLocation;
    }

    public void setCorporateLocation(String corporateLocation) {
        this.corporateLocation = corporateLocation;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProcuctName() {
        return procuctName;
    }

    public void setProcuctName(String procuctName) {
        this.procuctName = procuctName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getProducthot() {
        return producthot;
    }

    public void setProducthot(Integer producthot) {
        this.producthot = producthot;
    }

    public String getShopCode() {
        return shopCode;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }


    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Short getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(Short approvalType) {
        this.approvalType = approvalType;
    }

    public Short getServiceType() {
        return serviceType;
    }

    public void setServiceType(Short serviceType) {
        this.serviceType = serviceType;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStartProductId() {
        return startProductId;
    }

    public void setStartProductId(Integer startProductId) {
        this.startProductId = startProductId;
    }

    public Integer getEndProductId() {
        return endProductId;
    }

    public void setEndProductId(Integer endProductId) {
        this.endProductId = endProductId;
    }

    public List<CategoryAttr> getCategoryAttrList() {
        return categoryAttrList;
    }

    public void setCategoryAttrList(List<CategoryAttr> categoryAttrList) {
        this.categoryAttrList = categoryAttrList;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getProductFilterSql() {
        return productFilterSql;
    }

    public void setProductFilterSql(String productFilterSql) {
        this.productFilterSql = productFilterSql;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ViewSkuAttr> getViewSkuAttrList() {
        return viewSkuAttrList;
    }

    public void setViewSkuAttrList(List<ViewSkuAttr> viewSkuAttrList) {
        this.viewSkuAttrList = viewSkuAttrList;
    }

    public ProdBrand getProdBrand() {
        return prodBrand;
    }

    public void setProdBrand(ProdBrand prodBrand) {
        this.prodBrand = prodBrand;
    }

    public List<ProdBrand> getOtherBrandList() {
        return otherBrandList;
    }

    public void setOtherBrandList(List<ProdBrand> otherBrandList) {
        this.otherBrandList = otherBrandList;
    }

    public List<ProductAttr> getProductAttrList() {
        return productAttrList;
    }

    public void setProductAttrList(List<ProductAttr> productAttrList) {
        this.productAttrList = productAttrList;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public List<CmsProductRelation> getCmsRelationList() {
        return cmsRelationList;
    }

    public void setCmsRelationList(List<CmsProductRelation> cmsRelationList) {
        this.cmsRelationList = cmsRelationList;
    }

    public List<ProductSkuInfo> getSkuInfoList() {
        return skuInfoList;
    }

    public void setSkuInfoList(List<ProductSkuInfo> skuInfoList) {
        this.skuInfoList = skuInfoList;
    }

    public List<ProductRelation> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<ProductRelation> relationList) {
        this.relationList = relationList;
    }

    public List<ProductRelation> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<ProductRelation> packageList) {
        this.packageList = packageList;
    }

    public Integer getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Integer productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<ViewSkuAttr> getProductDetailInfo() {
        return productDetailInfo;
    }

    public void setProductDetailInfo(List<ViewSkuAttr> productDetailInfo) {
        this.productDetailInfo = productDetailInfo;
    }

    public CategoryDetailInfo getCategoryDetail() {
        return categoryDetail;
    }

    public void setCategoryDetail(CategoryDetailInfo categoryDetail) {
        this.categoryDetail = categoryDetail;
    }

    public List<String> getProductAutoTags() {
        return productAutoTags;
    }

    public void setProductAutoTags(List<String> productAutoTags) {
        this.productAutoTags = productAutoTags;
    }

    public Integer getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(Integer messageFlag) {
        this.messageFlag = messageFlag;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public List getProductIds() {
        return productIds;
    }

    public void setProductIds(List productIds) {
        this.productIds = productIds;
    }

    public List getProductSkuIds() {
        return productSkuIds;
    }

    public void setProductSkuIds(List productSkuIds) {
        this.productSkuIds = productSkuIds;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getSkuIntroduce() {
        return skuIntroduce;
    }

    public void setSkuIntroduce(String skuIntroduce) {
        this.skuIntroduce = skuIntroduce;
    }

    public String getBrandIntroduct() {
        return brandIntroduct;
    }

    public void setBrandIntroduct(String brandIntroduct) {
        this.brandIntroduct = brandIntroduct;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getCommercialTenantId() {
        return commercialTenantId;
    }

    public void setCommercialTenantId(Long commercialTenantId) {
        this.commercialTenantId = commercialTenantId;
    }

    public List<ViewSkuAttr> getProductLists() {
        return productLists;
    }

    public void setProductLists(List<ViewSkuAttr> productLists) {
        this.productLists = productLists;
    }

    public Short getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Short supplierType) {
        this.supplierType = supplierType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPoint() {
        return point;
    }

    public String getSkudesc() {
        return skudesc;
    }

    public void setSkudesc(String skudesc) {
        this.skudesc = skudesc;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getPromotionsEndDate() {
        return promotionsEndDate;
    }

    public void setPromotionsEndDate(Date promotionsEndDate) {
        this.promotionsEndDate = promotionsEndDate;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getWindowDataId() {
        return windowDataId;
    }

    public void setWindowDataId(Integer windowDataId) {
        this.windowDataId = windowDataId;
    }

    public String getUserDefinedName() {
        return userDefinedName;
    }

    public void setUserDefinedName(String userDefinedName) {
        this.userDefinedName = userDefinedName;
    }

    public Integer getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Integer shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getProductNameOrCode() {
        return productNameOrCode;
    }

    public void setProductNameOrCode(String productNameOrCode) {
        this.productNameOrCode = productNameOrCode;
    }

    public Category getCategoryObj() {
        return categoryObj;
    }

    public void setCategoryObj(Category categoryObj) {
        this.categoryObj = categoryObj;
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

    public CommercialTenantBasicInfo getCommercialTenantBasicInfo() {
        return commercialTenantBasicInfo;
    }

    public void setCommercialTenantBasicInfo(
            CommercialTenantBasicInfo commercialTenantBasicInfo) {
        this.commercialTenantBasicInfo = commercialTenantBasicInfo;
    }

    public List getProductSkuIdSort() {
        return productSkuIdSort;
    }

    public void setProductSkuIdSort(List productSkuIdSort) {
        this.productSkuIdSort = productSkuIdSort;
    }

    public Double getCostIncomeratio() {
        return costIncomeratio;
    }

    public void setCostIncomeratio(Double costIncomeratio) {
        this.costIncomeratio = costIncomeratio;
    }

    public Double getPresellPrice() {
        return presellPrice;
    }

    public void setPresellPrice(Double presellPrice) {
        this.presellPrice = presellPrice;
    }

    public Double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(Double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Date getDepositStartTime() {
        return depositStartTime;
    }

    public void setDepositStartTime(Date depositStartTime) {
        this.depositStartTime = depositStartTime;
    }

    public Date getDepositEndTime() {
        return depositEndTime;
    }

    public void setDepositEndTime(Date depositEndTime) {
        this.depositEndTime = depositEndTime;
    }

    public Date getFinalpayStartTime() {
        return finalpayStartTime;
    }

    public void setFinalpayStartTime(Date finalpayStartTime) {
        this.finalpayStartTime = finalpayStartTime;
    }

    public Date getFinalpayEndTime() {
        return finalpayEndTime;
    }

    public void setFinalpayEndTime(Date finalpayEndTime) {
        this.finalpayEndTime = finalpayEndTime;
    }

    public Date getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(Date deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public Date getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public void setDeliveryEndTime(Date deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Map<String, Object> getProductAppraiseMap() {
        return productAppraiseMap;
    }

    public void setProductAppraiseMap(Map<String, Object> productAppraiseMap) {
        this.productAppraiseMap = productAppraiseMap;
    }

    public String getImgPath7() {
        return imgPath7;
    }

    public void setImgPath7(String imgPath7) {
        this.imgPath7 = imgPath7;
    }

    public String getImgPath8() {
        return imgPath8;
    }

    public void setImgPath8(String imgPath8) {
        this.imgPath8 = imgPath8;
    }

    public String getImgPath5() {
        return imgPath5;
    }

    public void setImgPath5(String imgPath5) {
        this.imgPath5 = imgPath5;
    }
}