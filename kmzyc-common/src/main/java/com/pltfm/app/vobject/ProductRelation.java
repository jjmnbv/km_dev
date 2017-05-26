package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProductRelation implements Serializable {

	private static final long serialVersionUID = 3042545583627121561L;
	private Long relationId; // 关联主表的id

	private String relationName; // 关联的名称

	private BigDecimal mainSkuId; // 主skuID

	private BigDecimal mainSkuPrice;// 主产品价格

    private BigDecimal skuMarkPrice;//sku的市场价

	private BigDecimal totalRelationPrice; // 总的价格

	private Short relationType; // 关联类型

	private String remark; // 备注
	
	private BigDecimal  totalPrice ;    //总的市场价

	private List<ProductRelationView> relationViewList;    //界面显示的详情信息
	
	private boolean   productStatusIsValid=true;  // 判断关联的产品中有无没有上架的产品  即skuStatus为0，或者状态不为 3 (上架状态) 以及所关联的产品中的剩余数量是否大于零

    private Short status;  // 套餐状态

    private Short delStatus;// 删除状态

    private Date createDate;  //创建时间
     
    private Short editable;  //可编辑
    
    private String webSite;//所属站点
    private Short supplierType;//供应商类型
    private Short supplierId;//供应商id
    private List<ProductRelationAndDetail> showProductCoutntList;
    private Short allProCount;
    private String supplierName;//供应商名称
    private Short productSkuId; 
    private String  productSkuCode;//sku编码
    private BigDecimal pvValue;//套餐pv值
    
    private List<ProductRelationDetail> productRelationDetails;
    
    private BigDecimal costIncomeRatio;//合作收益百分比
    
    /**
     * 组方关联介绍(数据库Clob类型)
     */
    private String relationIntroduce;
    /**
     * 组方关联视频地址
     */
    private String relationVideo;
    /**
     * 主产品编号
     */
    private String productNo;
    /**
     * 组方简介
     */
    private String relationIntro;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 组方关联介绍lazy(数据库Clob类型)
     */
    private String relationIntroduceLazy;
    
	public List<ProductRelationView> getRelationViewList() {
		return relationViewList;
	}

	public void setRelationViewList(List<ProductRelationView> relationViewList) {
		this.relationViewList = relationViewList;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public BigDecimal getMainSkuId() {
		return mainSkuId;
	}

	public void setMainSkuId(BigDecimal mainSkuId) {
		this.mainSkuId = mainSkuId;
	}

	public BigDecimal getMainSkuPrice() {
		return mainSkuPrice;
	}

	public void setMainSkuPrice(BigDecimal mainSkuPrice) {
		this.mainSkuPrice = mainSkuPrice;
	}

	public BigDecimal getTotalRelationPrice() {
		return totalRelationPrice;
	}

	public void setTotalRelationPrice(BigDecimal totalRelationPrice) {
		this.totalRelationPrice = totalRelationPrice;
	}

	public Short getRelationType() {
		return relationType;
	}

	public void setRelationType(Short relationType) {
		this.relationType = relationType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isProductStatusIsValid() {
		return productStatusIsValid;
	}

	public void setProductStatusIsValid(boolean productStatusIsValid) {
		this.productStatusIsValid = productStatusIsValid;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Short delStatus) {
		this.delStatus = delStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Short getEditable() {
		return editable;
	}

	public void setEditable(Short editable) {
		this.editable = editable;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Short getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(Short supplierType) {
		this.supplierType = supplierType;
	}

	public Short getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Short supplierId) {
		this.supplierId = supplierId;
	}

	public List<ProductRelationAndDetail> getShowProductCoutntList() {
		return showProductCoutntList;
	}

	public void setShowProductCoutntList(
			List<ProductRelationAndDetail> showProductCoutntList) {
		this.showProductCoutntList = showProductCoutntList;
	}

	public Short getAllProCount() {
		return allProCount;
	}

	public void setAllProCount(Short allProCount) {
		this.allProCount = allProCount;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Short getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Short productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public BigDecimal getPvValue() {
		return pvValue;
	}

	public void setPvValue(BigDecimal pvValue) {
		this.pvValue = pvValue;
	}

	public BigDecimal getCostIncomeRatio() {
		return costIncomeRatio;
	}

	public void setCostIncomeRatio(BigDecimal costIncomeRatio) {
		this.costIncomeRatio = costIncomeRatio;
	}

	public List<ProductRelationDetail> getProductRelationDetails() {
		return productRelationDetails;
	}

	public void setProductRelationDetails(
			List<ProductRelationDetail> productRelationDetails) {
		this.productRelationDetails = productRelationDetails;
	}

	public String getRelationIntroduce() {
		return relationIntroduce;
	}

	public void setRelationIntroduce(String relationIntroduce) {
		this.relationIntroduce = relationIntroduce;
	}

	public String getRelationVideo() {
		return relationVideo;
	}

	public void setRelationVideo(String relationVideo) {
		this.relationVideo = relationVideo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getRelationIntro() {
		return relationIntro;
	}

	public void setRelationIntro(String relationIntro) {
		this.relationIntro = relationIntro;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRelationIntroduceLazy() {
		return relationIntroduceLazy;
	}

	public void setRelationIntroduceLazy(String relationIntroduceLazy) {
		this.relationIntroduceLazy = relationIntroduceLazy;
	}

    public BigDecimal getSkuMarkPrice() {
        return skuMarkPrice;
    }

    public void setSkuMarkPrice(BigDecimal skuMarkPrice) {
        this.skuMarkPrice = skuMarkPrice;
    }

    @Override
    public String toString() {
        return "ProductRelation{" +
                "relationId=" + relationId +
                ", relationName='" + relationName + '\'' +
                ", mainSkuId=" + mainSkuId +
                ", mainSkuPrice=" + mainSkuPrice +
                ", skuMarkPrice=" + skuMarkPrice +
                ", totalRelationPrice=" + totalRelationPrice +
                ", relationType=" + relationType +
                ", remark='" + remark + '\'' +
                ", totalPrice=" + totalPrice +
                ", relationViewList=" + relationViewList +
                ", productStatusIsValid=" + productStatusIsValid +
                ", status=" + status +
                ", delStatus=" + delStatus +
                ", createDate=" + createDate +
                ", editable=" + editable +
                ", webSite='" + webSite + '\'' +
                ", supplierType=" + supplierType +
                ", supplierId=" + supplierId +
                ", showProductCoutntList=" + showProductCoutntList +
                ", allProCount=" + allProCount +
                ", supplierName='" + supplierName + '\'' +
                ", productSkuId=" + productSkuId +
                ", productSkuCode='" + productSkuCode + '\'' +
                ", pvValue=" + pvValue +
                ", productRelationDetails=" + productRelationDetails +
                ", costIncomeRatio=" + costIncomeRatio +
                ", relationIntroduce='" + relationIntroduce + '\'' +
                ", relationVideo='" + relationVideo + '\'' +
                ", productNo='" + productNo + '\'' +
                ", relationIntro='" + relationIntro + '\'' +
                ", productName='" + productName + '\'' +
                ", relationIntroduceLazy='" + relationIntroduceLazy + '\'' +
                '}';
    }
}