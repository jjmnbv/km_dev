package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 退款明细 条件
 * 
 * @author lvzj
 * 
 */
@SuppressWarnings("unchecked")
public class SettlementRefundCriteria implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private Long settlementRefundId;
  /**
   * 对应结算单
   */
  private String settlementNo;
  /**
   * 对应订单编号
   */
  private String orderCode;
  /**
   * 对应退换货单编号
   */
  private String orderAlterCode;
  /**
   * 服务类型
   */
  private Short serviceType;
  /**
   * 商品SKU编号
   */
  private String skuNo;
  /**
   * 商品标题
   */
  private String productTitle;

  /**
   * 开始时间
   */
  private Date startDate;

  /**
   * 结束时间
   */
  private Date endDate;

  /**
   * id集合
   */
  private List hurlIds;



  public List getHurlIds() {
    return hurlIds;
  }

  public void setHurlIds(List hurlIds) {
    this.hurlIds = hurlIds;
  }

  /**
   * 分页条件
   */
  private Integer startIndex = 0;
  private Integer endIndex = 20;

  public Long getSettlementRefundId() {
    return settlementRefundId;
  }

  public void setSettlementRefundId(Long settlementRefundId) {
    this.settlementRefundId = settlementRefundId;
  }

  public String getSettlementNo() {
    return settlementNo;
  }

  public void setSettlementNo(String settlementNo) {
    this.settlementNo = settlementNo;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getOrderAlterCode() {
    return orderAlterCode;
  }

  public void setOrderAlterCode(String orderAlterCode) {
    this.orderAlterCode = orderAlterCode;
  }

  public Short getServiceType() {
    return serviceType;
  }

  public void setServiceType(Short serviceType) {
    this.serviceType = serviceType;
  }

  public String getSkuNo() {
    return skuNo;
  }

  public void setSkuNo(String skuNo) {
    this.skuNo = skuNo;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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

  public SettlementRefundCriteria() {
    super();
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "SettlementRefundCriteria [settlementRefundId=" + settlementRefundId + ", settlementNo="
        + settlementNo + ", orderCode=" + orderCode + ", orderAlterCode=" + orderAlterCode
        + ", serviceType=" + serviceType + ", skuNo=" + skuNo + ", productTitle=" + productTitle
        + ", startDate=" + startDate + ", endDate=" + endDate + ", startIndex=" + startIndex
        + ", endIndex=" + endIndex + "]";
  }

  public static SettlementRefundExample converToExample(SettlementRefundCriteria criteria) {
    SettlementRefundExample example = new SettlementRefundExample();

    if (null == criteria) {
      return null;
    }
    com.pltfm.app.entities.SettlementRefundExample.Criteria cri = example.createCriteria();

    example.setEndIndex(criteria.getEndIndex());
    example.setStartIndex(criteria.getStartIndex());

    example.orderByClause = "SETTLEMENT_TIME desc";

    cri.andSettlementNoEqualTo(criteria.getSettlementNo());

    if (StringUtils.isNotEmpty(criteria.getOrderCode())) {
      cri.andOrderCodeEqualTo(criteria.getOrderCode());
    }

    if (StringUtils.isNotEmpty(criteria.getSkuNo())) {
      cri.andSkuNoEqualTo(criteria.getSkuNo());
    }
    if (StringUtils.isNotEmpty(criteria.getProductTitle())) {
      cri.andProductTitleLike("%" + criteria.getProductTitle() + "%");
    }

    if (StringUtils.isNotEmpty(criteria.getOrderAlterCode())) {
      cri.andOrderAlterCodeEqualTo(criteria.getOrderAlterCode());
    }

    if (null != criteria.getServiceType() && 0 != criteria.getServiceType()) {
      cri.andServiceTypeEqualTo(criteria.getServiceType());
    }

    if (null != criteria.getStartDate()) {
      cri.andSettlementTimeGreaterThanOrEqualTo(criteria.getStartDate());
    }

    if (null != criteria.getEndDate()) {
      cri.andSettlementTimeLessThan(criteria.getEndDate());
    }

    if (null != criteria.getHurlIds()) {
      cri.andSettlementRefundIdIn(criteria.getHurlIds());
    }

    return example;
  }

}
