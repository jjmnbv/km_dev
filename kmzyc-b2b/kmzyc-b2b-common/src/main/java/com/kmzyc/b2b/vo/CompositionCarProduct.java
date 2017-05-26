package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 套装产品
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unused")
public class CompositionCarProduct implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = -9023265588933275042L;
  /** 套装ID */
  private Long id;
  /** 购买数量 */
  private int amount;
  /** 套装销售单价 */
  private BigDecimal price;

  /****
   * 外部系统编码
   */
  private String erpSysCode;

  /***
   * 外部系统产品编号
   */
  private String erpProCode;

  /** 套装销售单价 */
  private BigDecimal finalPrice;

  /** 库存数量 */
  private int stockCount;
  /**
   * 套装名称
   */
  private String name;
  /** 已下架 */
  private Boolean isOutOfStock;
  /**
   * 套装副产品
   */
  private List<CarProduct> productList;
  /**
   * 产品价格总计
   */
  private BigDecimal productsPriceSum;

  /***
   * pv值
   */
  private BigDecimal pvalue;

  /***
   * 获利百分比
   */

  private BigDecimal costIncomeRadio;

  /*** 获利金额 */
  private BigDecimal costIncomeMoney;
  /***
   * 主商品
   */
  private CarProduct mainCarProduct;

  private Boolean isChecked;

  public int calcAmount() {
    int rs = 0;
    if (null != productList && !productList.isEmpty()) {
      for (CarProduct cp : productList) {
        rs += cp.getAmount();
      }
    }
    return rs;
  }

  public int getAmount() {
    return amount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public List<CarProduct> getProductList() {
    return productList;
  }

  public void setProductList(List<CarProduct> productList) {
    this.productList = productList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JSON(serialize = false)
  public BigDecimal getWeigth() {
    BigDecimal weight = new BigDecimal(0);
    if (this.productList == null) return weight;
    for (CarProduct cp : productList) {
      BigDecimal oneWeight = BigDecimal.valueOf(cp.getAmount()).multiply(cp.getUnitWeight());
      weight = weight.add(oneWeight);
    }
    return weight;
  }

  /**
   * 组合商品折后价金额
   * 
   * @return
   */
  public BigDecimal getLastAllMoney() {
    return BigDecimal.valueOf(amount).multiply(finalPrice);
  }

  public BigDecimal getOrgMoney() {
    return BigDecimal.valueOf(amount).multiply(price);
  }

  /** 库存数量 */
  public int getStockCount() {
    return stockCount;
  }

  /** 库存数量 */
  public void setStockCount(int stockCount) {
    this.stockCount = stockCount;
  }

  /** 已下架 */
  public Boolean getIsOutOfStock() {
    return isOutOfStock;
  }

  /** 已下架 */
  public void setIsOutOfStock(Boolean isOutOfStock) {
    this.isOutOfStock = isOutOfStock;
  }

  public Boolean getIsChecked() {
    if (isChecked == null) {
      isChecked = true;
    }
    return isChecked;
  }

  @JSON(serialize = false)
  public CarProduct getMainCarProduct() {
    return this.getProductList().get(0);
  }

  public void setMainCarProduct(CarProduct mainCarProduct) {
    this.mainCarProduct = mainCarProduct;
  }

  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
  }

  @JSON(serialize = false)
  public String getIdKey() {
    return "CompositionCarProduct id:" + this.getId();
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

  public BigDecimal getCostIncomeRadio() {
    return costIncomeRadio;
  }

  public void setCostIncomeRadio(BigDecimal costIncomeRadio) {
    this.costIncomeRadio = costIncomeRadio;
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
}
