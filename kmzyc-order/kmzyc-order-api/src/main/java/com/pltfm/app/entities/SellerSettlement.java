package com.pltfm.app.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 结算单
 * 
 * @author weijl
 */
public class SellerSettlement implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -3468785257271365705L;
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
   * 结算时间
   */
  private Date settlementCreateTime;
  /**
   * 妥投实收汇总
   */
  private Double receiveSum;
  /**
   * 妥投佣金汇总
   */
  private Double commissionSum;
  /**
   * 妥投运费汇总
   */
  private Double fareSum;
  /**
   * 退款金额汇总
   */
  private Double refundSum;
  /**
   * 退款佣金汇总
   */
  private Double refundComSum;
  /**
   * 差异调整汇总
   */
  private Double diffAdjSum;
  /**
   * 本期应结金额
   */
  private Double currSettleAccounts;
  /**
   * 结算状态（结算状态，1:未确认,2:商家已确认,3:运营已确认,4:财务审核通过,5:财务审核拒绝,6:已结出）
   */
  private Short settlementStatus;
  /**
   * 商家确认意见
   */
  private String sellerConfirmation;
  /**
   * 商家确认时间
   */
  private Date serllerConfirmTime;
  /**
   * 运营确认意见
   */
  private String operateConfirmation;
  /**
   * 运营确认时间
   */
  private Date operateConfirmTime;
  /**
   * 财务审核意见
   */
  private String financialConfirmation;
  /**
   * 财务审核时间
   */
  private Date financialConfirmTime;
  /**
   * 结出时间
   */
  private Date settlementFinishTime;

  /**
   * 商家
   */
  private String sellerName;
  /**
   * 店铺名
   */
  private String shopName;
  /**
   * 操作说明
   */
  private List<SettmentOperateStatement> operates;

  /**
   * 区间表达
   */
  private String settlementPeriodExp;

  private String settlementPeriodFlag;
  
  /**
   * 退货返运费汇总
   */
  private Double returnFareSum;
  
  /**
   * 妥投推广服务费
   */
  private Double addpvsum;
  
  /**
   * 退款返推广服务费
   */
  private Double returnpvsum;

  public Double getAddpvsum() {
    return addpvsum;
}

public void setAddpvsum(Double addpvsum) {
    this.addpvsum = addpvsum;
}

public Double getReturnpvsum() {
    return returnpvsum;
}

public void setReturnpvsum(Double returnpvsum) {
    this.returnpvsum = returnpvsum;
}

public Double getReturnFareSum() {
    return returnFareSum;
  }

  public void setReturnFareSum(Double returnFareSum) {
    this.returnFareSum = returnFareSum;
  }

  public String getSettlementPeriodFlag() {
    return settlementPeriodFlag;
  }

  public void setSettlementPeriodFlag(String settlementPeriodFlag) {
    this.settlementPeriodFlag = settlementPeriodFlag;
  }

  public String getSettlementPeriodExp() {
    Calendar calendar = Calendar.getInstance();
    if (StringUtils.isEmpty(this.settlementPeriod)) {
      return null;
    }

    try {
      Date date = new SimpleDateFormat("yyyyMM").parse(this.settlementPeriod);
      calendar.setTime(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
    if (this.settlementPeriod.endsWith("h1") || this.settlementPeriod.endsWith("H1")) {

      // 2014-04-01 00:00:00 至 2014-04-16 00:00:00

      String t = new SimpleDateFormat("yyyy-MM-01 00:00:00 ").format(calendar.getTime());
      String t2 = new SimpleDateFormat("yyyy-MM-16 00:00:00").format(calendar.getTime());

      settlementPeriodExp = t + " 至 " + t2;

    } else {

      String t = new SimpleDateFormat("yyyy-MM-16 00:00:00 ").format(calendar.getTime());
      calendar.add(Calendar.MONTH, +1);
      String t2 = new SimpleDateFormat("yyyy-MM-01 00:00:00").format(calendar.getTime());

      settlementPeriodExp = t + " 至 " + t2;

    }

    return settlementPeriodExp;
  }

  public void setSettlementPeriodExp(String settlementPeriodExp) {
    this.settlementPeriodExp = settlementPeriodExp;
  }

  public List<SettmentOperateStatement> getOperates() {
    return operates;
  }

  public void setOperates(List<SettmentOperateStatement> operates) {
    this.operates = operates;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
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

  public Date getSettlementCreateTime() {
    return settlementCreateTime;
  }

  public void setSettlementCreateTime(Date settlementCreateTime) {
    this.settlementCreateTime = settlementCreateTime;
  }

  public Double getReceiveSum() {
    return receiveSum;
  }

  public void setReceiveSum(Double receiveSum) {
    this.receiveSum = receiveSum;
  }

  public Double getCommissionSum() {
    return commissionSum;
  }

  public void setCommissionSum(Double commissionSum) {
    this.commissionSum = commissionSum;
  }

  public Double getFareSum() {
    return fareSum;
  }

  public void setFareSum(Double fareSum) {
    this.fareSum = fareSum;
  }

  public Double getRefundSum() {
    return refundSum;
  }

  public void setRefundSum(Double refundSum) {
    this.refundSum = refundSum;
  }

  public Double getRefundComSum() {
    return refundComSum;
  }

  public void setRefundComSum(Double refundComSum) {
    this.refundComSum = refundComSum;
  }

  public Double getDiffAdjSum() {
    return diffAdjSum;
  }

  public void setDiffAdjSum(Double diffAdjSum) {
    this.diffAdjSum = diffAdjSum;
  }

  public Double getCurrSettleAccounts() {
    return currSettleAccounts;
  }

  public void setCurrSettleAccounts(Double currSettleAccounts) {
    this.currSettleAccounts = currSettleAccounts;
  }

  public Short getSettlementStatus() {
    return settlementStatus;
  }

  public void setSettlementStatus(Short settlementStatus) {
    this.settlementStatus = settlementStatus;
  }

  public String getSellerConfirmation() {
    return sellerConfirmation;
  }

  public void setSellerConfirmation(String sellerConfirmation) {
    this.sellerConfirmation = sellerConfirmation;
  }

  public Date getSerllerConfirmTime() {
    return serllerConfirmTime;
  }

  public void setSerllerConfirmTime(Date serllerConfirmTime) {
    this.serllerConfirmTime = serllerConfirmTime;
  }

  public String getOperateConfirmation() {
    return operateConfirmation;
  }

  public void setOperateConfirmation(String operateConfirmation) {
    this.operateConfirmation = operateConfirmation;
  }

  public Date getOperateConfirmTime() {
    return operateConfirmTime;
  }

  public void setOperateConfirmTime(Date operateConfirmTime) {
    this.operateConfirmTime = operateConfirmTime;
  }

  public String getFinancialConfirmation() {
    return financialConfirmation;
  }

  public void setFinancialConfirmation(String financialConfirmation) {
    this.financialConfirmation = financialConfirmation;
  }

  public Date getFinancialConfirmTime() {
    return financialConfirmTime;
  }

  public void setFinancialConfirmTime(Date financialConfirmTime) {
    this.financialConfirmTime = financialConfirmTime;
  }

  public Date getSettlementFinishTime() {
    return settlementFinishTime;
  }

  public void setSettlementFinishTime(Date settlementFinishTime) {
    this.settlementFinishTime = settlementFinishTime;
  }

  public SellerSettlement() {
    super();
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {
    return "SellerSettlement [settlementId=" + settlementId + ", settlementNo=" + settlementNo
        + ", sellerId=" + sellerId + ", settlementPeriod=" + settlementPeriod
        + ", settlementCreateTime=" + settlementCreateTime + ", receiveSum=" + receiveSum
        + ", commissionSum=" + commissionSum + ", fareSum=" + fareSum + ", refundSum=" + refundSum
        + ", refundComSum=" + refundComSum + ", diffAdjSum=" + diffAdjSum + ", currSettleAccounts="
        + currSettleAccounts + ", settlementStatus=" + settlementStatus + ", sellerConfirmation="
        + sellerConfirmation + ", serllerConfirmTime=" + serllerConfirmTime
        + ", operateConfirmation=" + operateConfirmation + ", operateConfirmTime="
        + operateConfirmTime + ", financialConfirmation=" + financialConfirmation
        + ", financialConfirmTime=" + financialConfirmTime + ", settlementFinishTime="
        + settlementFinishTime + "]";
  }

}
