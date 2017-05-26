package com.kmzyc.promotion.app.vobject;

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

	private BigDecimal totalRelationPrice; // 总的价格

	private Short relationType; // 关联类型

	private String remark; // 备注

	private BigDecimal totalPrice; // 总的市场价

	private List<ProductRelationView> relationViewList; // 界面显示的详情信息

	private boolean productStatusIsValid = true; // 判断关联的产品中有无没有上架的产品
	// 即skuStatus为0，或者状态不为 3
	// (上架状态)
	// 以及所关联的产品中的剩余数量是否大于零

	private Short status; // 套餐状态

	private Short delStatus;// 删除状态
	private Date createDate; // 创建时间

	private Short editable; // 可编辑

	public List<ProductRelationView> getRelationViewList() {
		return relationViewList;
	}

	public void setRelationViewList(List<ProductRelationView> relationViewList) {
		this.relationViewList = relationViewList;
	}

	public Long getRelationId() {
		return relationId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_RELATION.RELATION_ID
	 * 
	 * @param relationId
	 *            the value for PRODUCT_RELATION.RELATION_ID
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_RELATION.RELATION_NAME
	 * 
	 * @return the value of PRODUCT_RELATION.RELATION_NAME
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public String getRelationName() {
		return relationName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_RELATION.RELATION_NAME
	 * 
	 * @param relationName
	 *            the value for PRODUCT_RELATION.RELATION_NAME
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_RELATION.MAIN_SKU_ID
	 * 
	 * @return the value of PRODUCT_RELATION.MAIN_SKU_ID
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public BigDecimal getMainSkuId() {
		return mainSkuId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_RELATION.MAIN_SKU_ID
	 * 
	 * @param mainSkuId
	 *            the value for PRODUCT_RELATION.MAIN_SKU_ID
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public void setMainSkuId(BigDecimal mainSkuId) {
		this.mainSkuId = mainSkuId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_RELATION.MAIN_SKU_PRICE
	 * 
	 * @return the value of PRODUCT_RELATION.MAIN_SKU_PRICE
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public BigDecimal getMainSkuPrice() {
		return mainSkuPrice;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_RELATION.MAIN_SKU_PRICE
	 * 
	 * @param mainSkuPrice
	 *            the value for PRODUCT_RELATION.MAIN_SKU_PRICE
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public void setMainSkuPrice(BigDecimal mainSkuPrice) {
		this.mainSkuPrice = mainSkuPrice;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_RELATION.TOTAL_RELATION_PRICE
	 * 
	 * @return the value of PRODUCT_RELATION.TOTAL_RELATION_PRICE
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public BigDecimal getTotalRelationPrice() {
		return totalRelationPrice;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_RELATION.TOTAL_RELATION_PRICE
	 * 
	 * @param totalRelationPrice
	 *            the value for PRODUCT_RELATION.TOTAL_RELATION_PRICE
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public void setTotalRelationPrice(BigDecimal totalRelationPrice) {
		this.totalRelationPrice = totalRelationPrice;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_RELATION.RELATION_TYPE
	 * 
	 * @return the value of PRODUCT_RELATION.RELATION_TYPE
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public Short getRelationType() {
		return relationType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_RELATION.RELATION_TYPE
	 * 
	 * @param relationType
	 *            the value for PRODUCT_RELATION.RELATION_TYPE
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public void setRelationType(Short relationType) {
		this.relationType = relationType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_RELATION.REMARK
	 * 
	 * @return the value of PRODUCT_RELATION.REMARK
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_RELATION.REMARK
	 * 
	 * @param remark
	 *            the value for PRODUCT_RELATION.REMARK
	 * 
	 * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
	 */
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

}