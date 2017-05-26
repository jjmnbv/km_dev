package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 配送明细单
 * 
 * @author luoyi
 * @since 2013/08/20
 * 
 */
public class DistributionDetailInfo implements Serializable {

	private static final long serialVersionUID = 3979374257278088236L;

	private Long detailId;// 明细ID

	private Long distributionId;// 配送单ID

	private Long billDetailId;// 业务明细单据ID

	private Long productId;// 产品id

	private String productName;// 产品名称

	private String productNo;// 产品编号

	private Long productSkuId;// 产品SKU的id

	private String productSkuValue;// 产品SKU的值

	private Integer quantity;// 数量

	private BigDecimal price;// 价格

	private BigDecimal sum;// 金额

	private String remark;// 备注

	private String batchNo;// 药品、器械类商品的出厂批次号

	private Product product;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}

	public Long getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(Long billDetailId) {
		this.billDetailId = billDetailId;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
		return "DistributionDetailInfo [detailId=" + detailId + ", distributionId=" + distributionId
				+ ", billDetailId=" + billDetailId + ", productId=" + productId + ", productName=" + productName
				+ ", productNo=" + productNo + ", productSkuId=" + productSkuId + ", productSkuValue="
				+ productSkuValue + ", quantity=" + quantity + ", price=" + price + ", sum=" + sum + ", remark="
				+ remark + "]";
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}