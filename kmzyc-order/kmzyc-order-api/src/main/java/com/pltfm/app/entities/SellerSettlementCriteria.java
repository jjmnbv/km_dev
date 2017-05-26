package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 结算单 条件
 * 
 * @author lvzj
 * 
 */
public class SellerSettlementCriteria implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  /**
	 */
  private Long settlementId;
  /**
   * 结算单号
   */
  private String settlementNo;
  /**
   * 对应商家会员ID
   */
  private String sellerId;
  /**
   * 结算账期标识
   */
  private String settlementPeriod;

  /**
   * 结算状态（未确认|商家已确认|运营已确认|财务审核通过|财务审核拒绝|已结出）
   */
  private Short settlementStatus;

  /**
   * 商家
   */
  private String sellerName;

  /**
   * 开始时间
   */
  private Date startDate;

  /**
   * 结束时间
   */
  private Date endDate;

  /**
   * 商户登录ID
   */
  private String loginId;

  /**
   * 账期集合
   */
  private List<String> inPeriods;

  private List<Integer> statuses;
  
  /**
   * 应结金额区间
   */
  private BigDecimal minMoney;
  private BigDecimal maxMoney;
  public BigDecimal getMinMoney() {
    return minMoney;
  }

  public void setMinMoney(BigDecimal minMoney) {
    this.minMoney = minMoney;
  }

  public BigDecimal getMaxMoney() {
    return maxMoney;
  }

  public void setMaxMoney(BigDecimal maxMoney) {
    this.maxMoney = maxMoney;
  }

 

  /**
   * 分页条件
   */
  private Integer startIndex = 0;
  private Integer endIndex = 20;

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public SellerSettlementCriteria() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Long getSettlementId() {
    return settlementId;
  }

  public void setSettlementId(Long settlementId) {
    this.settlementId = settlementId;
  }

  public String getSettlementNo() {
    return settlementNo;
  }

  public void setSettlementNo(String settlementNo) {
    this.settlementNo = settlementNo;
  }

  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  public String getSettlementPeriod() {
    return settlementPeriod;
  }

  public void setSettlementPeriod(String settlementPeriod) {
    this.settlementPeriod = settlementPeriod;
  }

  public Short getSettlementStatus() {
    return settlementStatus;
  }

  public void setSettlementStatus(Short settlementStatus) {
    this.settlementStatus = settlementStatus;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
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

  public List<String> getInPeriods() {
    return inPeriods;
  }

  public void setInPeriods(List<String> inPeriods) {
    this.inPeriods = inPeriods;
  }

  public List<Integer> getStatuses() {
    return statuses;
  }

  public void setStatuses(List<Integer> statuses) {
    this.statuses = statuses;
  }

  @Override
  public String toString() {
    return "SellerSettlementCriteria [settlementId=" + settlementId + ", settlementNo="
        + settlementNo + ", sellerId=" + sellerId + ", settlementPeriod=" + settlementPeriod
        + ", settlementStatus=" + settlementStatus + ", sellerName=" + sellerName + ", startDate="
        + startDate + ", endDate=" + endDate + ", loginId=" + loginId + ", inPeriods=" + inPeriods
        + ", startIndex=" + startIndex + ", endIndex=" + endIndex +"minMoney="+minMoney+"maxMoney="+maxMoney+ "]";
  }


}
