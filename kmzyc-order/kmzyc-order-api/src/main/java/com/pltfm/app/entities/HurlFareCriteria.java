package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pltfm.app.entities.HurlFareExample.Criteria;

/**
 * 妥投订单运费明细 条件
 * 
 * @author lvzj
 * 
 */
@SuppressWarnings("unchecked")
public class HurlFareCriteria implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private Long settlementFareId;
  /**
   * 对应订单编
   */
  private String settlementNo;
  /**
   * 对应订单编号
   */
  private String orderCode;
  /**
   * 订单收货人
   */
  private String consigneeName;
  /**
   * 收货地址
   */
  private String consigneeAddr;

  /**
   * 开始时间
   */
  private Date startDate;

  /**
   * 结束时间
   */
  private Date endDate;

  /**
   * 分页条件
   */
  private Integer startIndex = 0;
  private Integer endIndex = 20;

  private List hurlIds;

  public List getHurlIds() {
    return hurlIds;
  }

  public void setHurlIds(List hurlIds) {
    this.hurlIds = hurlIds;
  }

  public Long getSettlementFareId() {
    return settlementFareId;
  }

  public void setSettlementFareId(Long settlementFareId) {
    this.settlementFareId = settlementFareId;
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

  public String getConsigneeName() {
    return consigneeName;
  }

  public void setConsigneeName(String consigneeName) {
    this.consigneeName = consigneeName;
  }

  public String getConsigneeAddr() {
    return consigneeAddr;
  }

  public void setConsigneeAddr(String consigneeAddr) {
    this.consigneeAddr = consigneeAddr;
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

  public HurlFareCriteria() {
    super();
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "HurlFareCriteria [settlementFareId=" + settlementFareId + ", settlementNo="
        + settlementNo + ", orderCode=" + orderCode + ", consigneeName=" + consigneeName
        + ", consigneeAddr=" + consigneeAddr + ", startDate=" + startDate + ", endDate=" + endDate
        + ", startIndex=" + startIndex + ", endIndex=" + endIndex + "]";
  }

  public static HurlFareExample converToExample(HurlFareCriteria criteria) {
    HurlFareExample example = new HurlFareExample();

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

    if (StringUtils.isNotEmpty(criteria.getConsigneeName())) {
      cri.andConsigneeNameLike("%" + criteria.getConsigneeName() + "%");
    }

    if (StringUtils.isNotEmpty(criteria.getConsigneeAddr())) {
      cri.andConsigneeAddrLike("%" + criteria.getConsigneeAddr() + "%");
    }

    if (null != criteria.getStartDate()) {
      cri.andSettlementTimeGreaterThanOrEqualTo(criteria.getStartDate());
    }

    if (null != criteria.getEndDate()) {
      cri.andSettlementTimeLessThan(criteria.getEndDate());
    }

    if (null != criteria.getHurlIds()) {
      cri.andSettlementFareIdIn(criteria.getHurlIds());
    }

    return example;
  }

}
