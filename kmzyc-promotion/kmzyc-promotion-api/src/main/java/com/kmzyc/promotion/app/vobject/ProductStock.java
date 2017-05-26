package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

public class ProductStock implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5164149906041310951L;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.STOCK_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long stockId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.WAREHOUSE_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long warehouseId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.PRODUCT_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long productId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.PRODUCT_NAME
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private String productName;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.PRODUCT_NO
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private String productNo;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.SKU_ATTRIBUTE_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long skuAttributeId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.SKU_ATT_VALUE
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private String skuAttValue;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.STOCK_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long stockQuality;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.ORDER_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long orderQuality;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.IN_TRANSIT_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long inTransitQuality;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.ALARM_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long alarmQuality;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.TOTAL_SALES
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long totalSales;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.PRICE
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private BigDecimal price;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.MIN_DAY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long minDay;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.MAX_DAY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long maxDay;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.ADJUST
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private Long adjust;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column PRODUCT_STOCK.REMARK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	private String remark;

	private Long lockCount;// 锁库存量，非持久化

	private Long unLockCount;// 解锁库存量，非持久化

	private Product product;

	private Long billDetailId;

	private String billNo;

	private Long changeOrderQuatity;// 增加/减少订购数的数量，非持久化

	private Long beginQuantity;// 查询大于等于的数量

	private Long endQuantity;// 查询小于的数量

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.STOCK_ID
	 * 
	 * @return the value of PRODUCT_STOCK.STOCK_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getStockId() {
		return stockId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.STOCK_ID
	 * 
	 * @param stockId
	 *            the value for PRODUCT_STOCK.STOCK_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.WAREHOUSE_ID
	 * 
	 * @return the value of PRODUCT_STOCK.WAREHOUSE_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getWarehouseId() {
		return warehouseId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.WAREHOUSE_ID
	 * 
	 * @param warehouseId
	 *            the value for PRODUCT_STOCK.WAREHOUSE_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.PRODUCT_ID
	 * 
	 * @return the value of PRODUCT_STOCK.PRODUCT_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.PRODUCT_ID
	 * 
	 * @param productId
	 *            the value for PRODUCT_STOCK.PRODUCT_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.PRODUCT_NAME
	 * 
	 * @return the value of PRODUCT_STOCK.PRODUCT_NAME
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.PRODUCT_NAME
	 * 
	 * @param productName
	 *            the value for PRODUCT_STOCK.PRODUCT_NAME
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.PRODUCT_NO
	 * 
	 * @return the value of PRODUCT_STOCK.PRODUCT_NO
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public String getProductNo() {
		return productNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.PRODUCT_NO
	 * 
	 * @param productNo
	 *            the value for PRODUCT_STOCK.PRODUCT_NO
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.SKU_ATTRIBUTE_ID
	 * 
	 * @return the value of PRODUCT_STOCK.SKU_ATTRIBUTE_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getSkuAttributeId() {
		return skuAttributeId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.SKU_ATTRIBUTE_ID
	 * 
	 * @param skuAttributeId
	 *            the value for PRODUCT_STOCK.SKU_ATTRIBUTE_ID
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setSkuAttributeId(Long skuAttributeId) {
		this.skuAttributeId = skuAttributeId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.SKU_ATT_VALUE
	 * 
	 * @return the value of PRODUCT_STOCK.SKU_ATT_VALUE
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public String getSkuAttValue() {
		return skuAttValue;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.SKU_ATT_VALUE
	 * 
	 * @param skuAttValue
	 *            the value for PRODUCT_STOCK.SKU_ATT_VALUE
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setSkuAttValue(String skuAttValue) {
		this.skuAttValue = skuAttValue;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.STOCK_QUALITY
	 * 
	 * @return the value of PRODUCT_STOCK.STOCK_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getStockQuality() {
		return stockQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.STOCK_QUALITY
	 * 
	 * @param stockQuality
	 *            the value for PRODUCT_STOCK.STOCK_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setStockQuality(Long stockQuality) {
		this.stockQuality = stockQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.ORDER_QUALITY
	 * 
	 * @return the value of PRODUCT_STOCK.ORDER_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getOrderQuality() {
		return orderQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.ORDER_QUALITY
	 * 
	 * @param orderQuality
	 *            the value for PRODUCT_STOCK.ORDER_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setOrderQuality(Long orderQuality) {
		this.orderQuality = orderQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.IN_TRANSIT_QUALITY
	 * 
	 * @return the value of PRODUCT_STOCK.IN_TRANSIT_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getInTransitQuality() {
		return inTransitQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.IN_TRANSIT_QUALITY
	 * 
	 * @param inTransitQuality
	 *            the value for PRODUCT_STOCK.IN_TRANSIT_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setInTransitQuality(Long inTransitQuality) {
		this.inTransitQuality = inTransitQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.ALARM_QUALITY
	 * 
	 * @return the value of PRODUCT_STOCK.ALARM_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getAlarmQuality() {
		return alarmQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.ALARM_QUALITY
	 * 
	 * @param alarmQuality
	 *            the value for PRODUCT_STOCK.ALARM_QUALITY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setAlarmQuality(Long alarmQuality) {
		this.alarmQuality = alarmQuality;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.TOTAL_SALES
	 * 
	 * @return the value of PRODUCT_STOCK.TOTAL_SALES
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getTotalSales() {
		return totalSales;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.TOTAL_SALES
	 * 
	 * @param totalSales
	 *            the value for PRODUCT_STOCK.TOTAL_SALES
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.PRICE
	 * 
	 * @return the value of PRODUCT_STOCK.PRICE
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.PRICE
	 * 
	 * @param price
	 *            the value for PRODUCT_STOCK.PRICE
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.MIN_DAY
	 * 
	 * @return the value of PRODUCT_STOCK.MIN_DAY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getMinDay() {
		return minDay;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.MIN_DAY
	 * 
	 * @param minDay
	 *            the value for PRODUCT_STOCK.MIN_DAY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setMinDay(Long minDay) {
		this.minDay = minDay;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.MAX_DAY
	 * 
	 * @return the value of PRODUCT_STOCK.MAX_DAY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getMaxDay() {
		return maxDay;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.MAX_DAY
	 * 
	 * @param maxDay
	 *            the value for PRODUCT_STOCK.MAX_DAY
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setMaxDay(Long maxDay) {
		this.maxDay = maxDay;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.ADJUST
	 * 
	 * @return the value of PRODUCT_STOCK.ADJUST
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public Long getAdjust() {
		return adjust;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.ADJUST
	 * 
	 * @param adjust
	 *            the value for PRODUCT_STOCK.ADJUST
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setAdjust(Long adjust) {
		this.adjust = adjust;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column PRODUCT_STOCK.REMARK
	 * 
	 * @return the value of PRODUCT_STOCK.REMARK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column PRODUCT_STOCK.REMARK
	 * 
	 * @param remark
	 *            the value for PRODUCT_STOCK.REMARK
	 * 
	 * @ibatorgenerated Mon Jul 29 10:50:35 CST 2013
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getLockCount() {
		return lockCount;
	}

	public void setLockCount(Long lockCount) {
		this.lockCount = lockCount;
	}

	public Long getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(Long billDetailId) {
		this.billDetailId = billDetailId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Long getUnLockCount() {
		return unLockCount;
	}

	public void setUnLockCount(Long unLockCount) {
		this.unLockCount = unLockCount;
	}

	public Long getChangeOrderQuatity() {
		return changeOrderQuatity;
	}

	public void setChangeOrderQuatity(Long changeOrderQuatity) {
		this.changeOrderQuatity = changeOrderQuatity;
	}

	public Long getBeginQuantity() {
		return beginQuantity;
	}

	public void setBeginQuantity(Long beginQuantity) {
		this.beginQuantity = beginQuantity;
	}

	public Long getEndQuantity() {
		return endQuantity;
	}

	public void setEndQuantity(Long endQuantity) {
		this.endQuantity = endQuantity;
	}
}