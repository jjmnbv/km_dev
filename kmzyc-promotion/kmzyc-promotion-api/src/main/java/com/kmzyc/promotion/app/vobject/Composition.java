package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 套餐
 * 
 * @author xlg
 * 
 */
public class Composition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 套装ID
	 */
	private Long cid;

	/**
	 * 购买数量
	 */
	private Integer amount;
	/**
	 * 套装销售单价
	 */
	private BigDecimal price;

	/**
	 * 套装销售单价
	 */
	private BigDecimal finalPrice;

	/***
	 * pv值
	 */

	private BigDecimal pvalue;

	/****
	 * 外部系统编码
	 */
	private String erpSysCode;

	/***
	 * 外部系统产品编号
	 */
	private String erpProCode;

	/****
	 *获利百分比
	 */

	private BigDecimal costIncomeRatio;

	/**
	 * 获利金额
	 */
	private BigDecimal costIncomeMoney;

	/**
	 * 库存数量
	 */
	private Integer stockCount;
	/**
	 * 套装名称
	 */
	private String name;
	/** 已下架 */
	private Boolean isOutOfStock;
	/**
	 * 套装产品
	 */
	private List<BaseProduct> productList;

	/***
	 * 主商品
	 */
	private BaseProduct mainCarProduct;

	/**
	 * 是否选中
	 */
	private Boolean isChecked;
	/**
	 * 产品价格总计
	 */
	private BigDecimal productsPriceSum;

	/**
	 * 商品运费
	 */
	private BigDecimal freight;

	/**
	 * 是否免邮（0：不免邮；1：免邮）
	 */
	private Integer freeStatus;

	public int calcAmount() {
		int rs = 0;
		if (null != productList && !productList.isEmpty()) {
			for (BaseProduct cp : productList) {
				rs += cp.getAmount();
			}
		}
		return rs;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsOutOfStock() {
		return isOutOfStock;
	}

	public void setIsOutOfStock(Boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}

	public List<BaseProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<BaseProduct> productList) {
		this.productList = productList;
	}

	public BaseProduct getMainCarProduct() {
		if (null == mainCarProduct) {
			mainCarProduct = null != productList ? productList.get(0) : null;
		}
		return mainCarProduct;
	}

	public void setMainCarProduct(BaseProduct mainCarProduct) {
		this.mainCarProduct = mainCarProduct;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public BigDecimal getWeigth() {
		BigDecimal weight = new BigDecimal(0);
		if (this.productList == null)
			return weight;
		for (BaseProduct bp : productList) {
			BigDecimal oneWeight = BigDecimal.valueOf(bp.getAmount()).multiply(bp.getUnitWeight());
			weight = weight.add(oneWeight);
		}
		return weight;
	}

	public BigDecimal getLastAllMoney() {
		return BigDecimal.valueOf(amount).multiply(finalPrice);
	}

	public BigDecimal getOrgMoney() {
		return BigDecimal.valueOf(amount).multiply(price);
	}

	public BigDecimal getProductsPriceSum() {
		return productsPriceSum;
	}

	public void setProductsPriceSum(BigDecimal productsPriceSum) {
		this.productsPriceSum = productsPriceSum;
	}

	public BigDecimal getPvalue() {
		return pvalue;
	}

	public void setPvalue(BigDecimal pvalue) {
		this.pvalue = pvalue;
	}

	public BigDecimal getCostIncomeRatio() {
		return costIncomeRatio;
	}

	public void setCostIncomeRatio(BigDecimal costIncomeRatio) {
		this.costIncomeRatio = costIncomeRatio;
	}

	public String getErpSysCode() {
		return erpSysCode;
	}

	public void setErpSysCode(String erpSysCode) {
		this.erpSysCode = erpSysCode;
	}

	public String getErpProCode() {
		return erpProCode;
	}

	public void setErpProCode(String erpProCode) {
		this.erpProCode = erpProCode;
	}

	public BigDecimal getCostIncomeMoney() {
		return costIncomeMoney;
	}

	public void setCostIncomeMoney(BigDecimal costIncomeMoney) {
		this.costIncomeMoney = costIncomeMoney;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Integer getFreeStatus() {
		return freeStatus;
	}

	public void setFreeStatus(Integer freeStatus) {
		this.freeStatus = freeStatus;
	}
}