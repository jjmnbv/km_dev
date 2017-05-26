package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出库明细单
 * 
 * @author luoyi
 * @createDate 2013/08/15
 */
public class StockOutDetail implements Serializable {
	private static final long serialVersionUID = 2708941578285654787L;

	/**
	 * StockOutDetail无参构造方法
	 */
	public StockOutDetail() {

	}

	private Long detailId;// 明细ID

	private Long stockOutId;// 出库单ID

	private Long stockId;// 库存ID

	private Long productId;// 产品ID

	private String productName;// 产品名称

	private String productNo;// 产品编号

	private Long productSkuId;// 产品出库条码ID

	private String productSkuValue;// 产品货号SKU

	private String barCode;// 产品出库条码

	private Long billDetailId;// 业务单据明细ID

	private Short billType;// 单据类型

	private Integer quantity;// 出库数量

	private BigDecimal taxPrice;// 含税单价

	private BigDecimal taxSum;// 含税金额

	private BigDecimal taxRate;// 税率

	private BigDecimal tax;// 税额

	private BigDecimal price;// 不含税单价

	private BigDecimal sum;// 不含税金额

	private String remark;// 备注

	private String str_productId;

	private String str_skuId;

	private String str_stockOutPrice;

	private String str_quantity;

	/*
	 * 以下为属性get和set方法,请不要将业务代码放在下面处理
	 */
	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getStockOutId() {
		return stockOutId;
	}

	public void setStockOutId(Long stockOutId) {
		this.stockOutId = stockOutId;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Long getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductSkuValue() {
		return productSkuValue;
	}

	public void setProductSkuValue(String productSkuValue) {
		this.productSkuValue = productSkuValue;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Long getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(Long billDetailId) {
		this.billDetailId = billDetailId;
	}

	public Short getBillType() {
		return billType;
	}

	public void setBillType(Short billType) {
		this.billType = billType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}

	public BigDecimal getTaxSum() {
		return taxSum;
	}

	public void setTaxSum(BigDecimal taxSum) {
		this.taxSum = taxSum;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "StockOutDetail [detailId=" + detailId + ", stockOutId=" + stockOutId + ", stockId=" + stockId
				+ ", productId=" + productId + ", productName=" + productName + ", productNo=" + productNo
				+ ", productSkuId=" + productSkuId + ", productSkuValue=" + productSkuValue + ", barCode=" + barCode
				+ ", billDetailId=" + billDetailId + ", billType=" + billType + ", quantity=" + quantity
				+ ", taxPrice=" + taxPrice + ", taxSum=" + taxSum + ", taxRate=" + taxRate + ", tax=" + tax
				+ ", price=" + price + ", sum=" + sum + ", remark=" + remark + "]";
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

	public String getStr_quantity() {
		return str_quantity;
	}

	public void setStr_quantity(String str_quantity) {
		this.str_quantity = str_quantity;
	}

	public String getStr_stockOutPrice() {
		return str_stockOutPrice;
	}

	public void setStr_stockOutPrice(String str_stockOutPrice) {
		this.str_stockOutPrice = str_stockOutPrice;
	}

}