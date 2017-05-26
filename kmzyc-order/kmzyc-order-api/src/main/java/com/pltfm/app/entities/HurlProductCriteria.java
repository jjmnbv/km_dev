package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pltfm.app.entities.HurlProductExample.Criteria;

/**
 * 妥投商品结算明细 条件
 * 
 * @author lvzj
 * 
 */
@SuppressWarnings("unchecked")
public class HurlProductCriteria implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  /**
   * 结算妥投商品明细ID
   */
  private Long settlementHurlId;
  /**
   * 对应结算单
   */
  private String settlementNo;
  /**
   * 对应订单编号
   */
  private String orderCode;
  /**
   * 商品SKU编号
   */
  private String skuNo;
  /**
   * 商品标题
   */
  private String productTile;

  /**
   * 开始时间
   */
  private Date startDate;

  /**
   * 结束时间
   */
  private Date endDate;

  /**
   * 妥投产品id集合
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

  public Long getSettlementHurlId() {
    return settlementHurlId;
  }

  public void setSettlementHurlId(Long settlementHurlId) {
    this.settlementHurlId = settlementHurlId;
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

  public String getSkuNo() {
    return skuNo;
  }

  public void setSkuNo(String skuNo) {
    this.skuNo = skuNo;
  }

  public String getProductTile() {
    return productTile;
  }

  public void setProductTile(String productTile) {
    this.productTile = productTile;
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

  public HurlProductCriteria() {
    super();
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "HurlProductCriteria [settlementHurlId=" + settlementHurlId + ", settlementNo="
        + settlementNo + ", orderCode=" + orderCode + ", skuNo=" + skuNo + ", productTile="
        + productTile + ", startDate=" + startDate + ", endDate=" + endDate + ", startIndex="
        + startIndex + ", endIndex=" + endIndex + "]";
  }

  public static HurlProductExample converToExample(HurlProductCriteria criteria) {
    HurlProductExample example = new HurlProductExample();

    if (null == criteria) {
      return null;
    }
    Criteria cri = example.createCriteria();

    example.setEndIndex(criteria.getEndIndex());
    example.setStartIndex(criteria.getStartIndex());
    example.orderByClause = "SETTLEMENT_TIME desc";

    cri.andSettlementNoEqualTo(criteria.getSettlementNo());

    if (StringUtils.isNotEmpty(criteria.getOrderCode())) {
      cri.andOrderCodeEqualTo(criteria.getOrderCode());
    }

    if (StringUtils.isNotEmpty(criteria.getProductTile())) {
      cri.andProductTileLike("%" + criteria.getProductTile() + "%");
    }

    if (StringUtils.isNotEmpty(criteria.getSkuNo())) {
      cri.andSkuNoEqualTo(criteria.getSkuNo());
    }

    if (null != criteria.getStartDate()) {
      cri.andSettlementTimeGreaterThanOrEqualTo(criteria.getStartDate());
    }

    if (null != criteria.getEndDate()) {
      cri.andSettlementTimeLessThan(criteria.getEndDate());
    }

    if (null != criteria.getHurlIds()) {
      cri.andSettlementHurlIdIn(criteria.getHurlIds());
    }

    return example;
  }

}
