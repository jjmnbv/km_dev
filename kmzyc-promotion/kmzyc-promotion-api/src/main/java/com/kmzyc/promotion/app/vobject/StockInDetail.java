package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

public class StockInDetail {
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.DETAIL_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Long detailId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.STOCK_IN_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Long stockInId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.STOCK_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Long stockId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.PRODUCT_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Long productId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.PRODUCT_NAME
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private String productName;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.PRODUCT_NO
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private String productNo;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.PRODUCT_SKU_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Long productSkuId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.PRODUCT_SKU_VALUE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private String productSkuValue;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.BILL_DETAIL_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Long billDetailId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.BILL_TYPE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Short billType;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.BATCH_NUMBER
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private String batchNumber;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.QUANTITY
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private Integer quantity;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.TAX_PRICE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private BigDecimal taxPrice;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.TAX_SUM
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private BigDecimal taxSum;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.TAX_RATE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private BigDecimal taxRate;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.TAX
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private BigDecimal tax;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.PRICE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private BigDecimal price;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.SUM
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private BigDecimal sum;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.REMARK
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	private String remark;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column STOCK_IN_DETAIL.PURCHASE_NO
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */

	// 采购单的采购单号
	private String purchaseNo;

	private String str_productId;

	private String str_skuId;

	private String str_stockInPrice;

	private String str_quantity;

	public Long getDetailId() {
		return detailId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.DETAIL_ID
	 * 
	 * @param detailId
	 *            the value for STOCK_IN_DETAIL.DETAIL_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.STOCK_IN_ID
	 * 
	 * @return the value of STOCK_IN_DETAIL.STOCK_IN_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public Long getStockInId() {
		return stockInId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.STOCK_IN_ID
	 * 
	 * @param stockInId
	 *            the value for STOCK_IN_DETAIL.STOCK_IN_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setStockInId(Long stockInId) {
		this.stockInId = stockInId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.STOCK_ID
	 * 
	 * @return the value of STOCK_IN_DETAIL.STOCK_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public Long getStockId() {
		return stockId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.STOCK_ID
	 * 
	 * @param stockId
	 *            the value for STOCK_IN_DETAIL.STOCK_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.PRODUCT_ID
	 * 
	 * @return the value of STOCK_IN_DETAIL.PRODUCT_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.PRODUCT_ID
	 * 
	 * @param productId
	 *            the value for STOCK_IN_DETAIL.PRODUCT_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.PRODUCT_NAME
	 * 
	 * @return the value of STOCK_IN_DETAIL.PRODUCT_NAME
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.PRODUCT_NAME
	 * 
	 * @param productName
	 *            the value for STOCK_IN_DETAIL.PRODUCT_NAME
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.PRODUCT_NO
	 * 
	 * @return the value of STOCK_IN_DETAIL.PRODUCT_NO
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public String getProductNo() {
		return productNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.PRODUCT_NO
	 * 
	 * @param productNo
	 *            the value for STOCK_IN_DETAIL.PRODUCT_NO
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.PRODUCT_SKU_ID
	 * 
	 * @return the value of STOCK_IN_DETAIL.PRODUCT_SKU_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public Long getProductSkuId() {
		return productSkuId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.PRODUCT_SKU_ID
	 * 
	 * @param productSkuId
	 *            the value for STOCK_IN_DETAIL.PRODUCT_SKU_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.PRODUCT_SKU_VALUE
	 * 
	 * @return the value of STOCK_IN_DETAIL.PRODUCT_SKU_VALUE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public String getProductSkuValue() {
		return productSkuValue;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.PRODUCT_SKU_VALUE
	 * 
	 * @param productSkuValue
	 *            the value for STOCK_IN_DETAIL.PRODUCT_SKU_VALUE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setProductSkuValue(String productSkuValue) {
		this.productSkuValue = productSkuValue;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.BILL_DETAIL_ID
	 * 
	 * @return the value of STOCK_IN_DETAIL.BILL_DETAIL_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public Long getBillDetailId() {
		return billDetailId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.BILL_DETAIL_ID
	 * 
	 * @param billDetailId
	 *            the value for STOCK_IN_DETAIL.BILL_DETAIL_ID
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setBillDetailId(Long billDetailId) {
		this.billDetailId = billDetailId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.BILL_TYPE
	 * 
	 * @return the value of STOCK_IN_DETAIL.BILL_TYPE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public Short getBillType() {
		return billType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.BILL_TYPE
	 * 
	 * @param billType
	 *            the value for STOCK_IN_DETAIL.BILL_TYPE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setBillType(Short billType) {
		this.billType = billType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.BATCH_NUMBER
	 * 
	 * @return the value of STOCK_IN_DETAIL.BATCH_NUMBER
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.BATCH_NUMBER
	 * 
	 * @param batchNumber
	 *            the value for STOCK_IN_DETAIL.BATCH_NUMBER
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.QUANTITY
	 * 
	 * @return the value of STOCK_IN_DETAIL.QUANTITY
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.QUANTITY
	 * 
	 * @param quantity
	 *            the value for STOCK_IN_DETAIL.QUANTITY
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.TAX_PRICE
	 * 
	 * @return the value of STOCK_IN_DETAIL.TAX_PRICE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.TAX_PRICE
	 * 
	 * @param taxPrice
	 *            the value for STOCK_IN_DETAIL.TAX_PRICE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.TAX_SUM
	 * 
	 * @return the value of STOCK_IN_DETAIL.TAX_SUM
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public BigDecimal getTaxSum() {
		return taxSum;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.TAX_SUM
	 * 
	 * @param taxSum
	 *            the value for STOCK_IN_DETAIL.TAX_SUM
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setTaxSum(BigDecimal taxSum) {
		this.taxSum = taxSum;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.TAX_RATE
	 * 
	 * @return the value of STOCK_IN_DETAIL.TAX_RATE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.TAX_RATE
	 * 
	 * @param taxRate
	 *            the value for STOCK_IN_DETAIL.TAX_RATE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.TAX
	 * 
	 * @return the value of STOCK_IN_DETAIL.TAX
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public BigDecimal getTax() {
		return tax;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.TAX
	 * 
	 * @param tax
	 *            the value for STOCK_IN_DETAIL.TAX
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.PRICE
	 * 
	 * @return the value of STOCK_IN_DETAIL.PRICE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.PRICE
	 * 
	 * @param price
	 *            the value for STOCK_IN_DETAIL.PRICE
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.SUM
	 * 
	 * @return the value of STOCK_IN_DETAIL.SUM
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public BigDecimal getSum() {
		return sum;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.SUM
	 * 
	 * @param sum
	 *            the value for STOCK_IN_DETAIL.SUM
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.REMARK
	 * 
	 * @return the value of STOCK_IN_DETAIL.REMARK
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.REMARK
	 * 
	 * @param remark
	 *            the value for STOCK_IN_DETAIL.REMARK
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column STOCK_IN_DETAIL.PURCHASE_NO
	 * 
	 * @return the value of STOCK_IN_DETAIL.PURCHASE_NO
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public String getPurchaseNo() {
		return purchaseNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column STOCK_IN_DETAIL.PURCHASE_NO
	 * 
	 * @param purchaseNo
	 *            the value for STOCK_IN_DETAIL.PURCHASE_NO
	 * 
	 * @ibatorgenerated Thu Sep 05 16:13:47 CST 2013
	 */
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getStr_productId() {
		return str_productId;
	}

	public void setStr_productId(String str_productId) {
		this.str_productId = str_productId;
	}

	public String getStr_skuId() {
		return str_skuId;
	}

	public void setStr_skuId(String str_skuId) {
		this.str_skuId = str_skuId;
	}

	public String getStr_stockInPrice() {
		return str_stockInPrice;
	}

	public void setStr_stockInPrice(String str_stockInPrice) {
		this.str_stockInPrice = str_stockInPrice;
	}

	public String getStr_quantity() {
		return str_quantity;
	}

	public void setStr_quantity(String str_quantity) {
		this.str_quantity = str_quantity;
	}
}