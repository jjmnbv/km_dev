package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ReserverBill implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 2714062782251150125L;
  // 账单id
  private BigDecimal billId;
  // 预备金Id
  private BigDecimal reserveId;
  // 登陆id
  private BigDecimal loginId;
  // 账单名称(自动生成如：201405,2014Q1)
  private String billName;
  // 开始日期
  private Date startDate;
  // 结算日期
  private Date settlementDate;
  // 还款日期
  private Date repayDate;
  // 本期账单（本期除还款和调整外所有记录汇总计算）
  private BigDecimal bill;
  // 本期应还（直接取上期账单的应还金额）
  private BigDecimal repay;
  // (本期已还)到本期结算日到本期还款之间的还款记录都计算为本期已还金额）
  private BigDecimal currentRepayed;
  // (提前还款)上期还款日之后到本期结算日之间的还款记录都计算为本期提前金额）
  private BigDecimal advanceRepayed;
  // 上期应还金额
  private BigDecimal lastPriodRepay;
  // 上期还款金额（上期结算日到还款日之间的还款记录都算为上）
  private BigDecimal lastPriodHavepay;
  // 本期调整金额（调整金额指对错账的冲平调整记录）
  private BigDecimal missPay;
  // 本期是否还清(1:是,2:否)
  private Short payOff;
  // 逾期还款(1:是,2:否)
  private Short overduePay;
  // 公司名
  private String corporateName;
  // 用户名
  private String accountLogin;
  /** 分页索引最小值 */
  private Integer minNum;
  /** 分页索引最大值 */
  private Integer maxNum;
  // 结算开始时间
  private Date settlementDateStarttime;
  // 结算结束时间
  private Date settlementDateEndtime;
  // 账单开始end时间
  private Date startDateEndtime;
  // 账单开始时间
  private Date startDateStarttime;

  public BigDecimal getBillId() {
    return billId;
  }

  public void setBillId(BigDecimal billId) {
    this.billId = billId;
  }

  public BigDecimal getReserveId() {
    return reserveId;
  }

  public void setReserveId(BigDecimal reserveId) {
    this.reserveId = reserveId;
  }

  public BigDecimal getLoginId() {
    return loginId;
  }

  public void setLoginId(BigDecimal loginId) {
    this.loginId = loginId;
  }

  public String getBillName() {
    return billName;
  }

  public void setBillName(String billName) {
    this.billName = billName;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getSettlementDate() {
    return settlementDate;
  }

  public void setSettlementDate(Date settlementDate) {
    this.settlementDate = settlementDate;
  }

  public Date getRepayDate() {
    return repayDate;
  }

  public void setRepayDate(Date repayDate) {
    this.repayDate = repayDate;
  }

  public BigDecimal getBill() {
    return bill;
  }

  public void setBill(BigDecimal bill) {
    this.bill = bill;
  }

  public BigDecimal getRepay() {
    return repay;
  }

  public void setRepay(BigDecimal repay) {
    this.repay = repay;
  }

  public BigDecimal getCurrentRepayed() {
    return currentRepayed;
  }

  public void setCurrentRepayed(BigDecimal currentRepayed) {
    this.currentRepayed = currentRepayed;
  }

  public BigDecimal getAdvanceRepayed() {
    return advanceRepayed;
  }

  public void setAdvanceRepayed(BigDecimal advanceRepayed) {
    this.advanceRepayed = advanceRepayed;
  }

  public BigDecimal getLastPriodRepay() {
    return lastPriodRepay;
  }

  public void setLastPriodRepay(BigDecimal lastPriodRepay) {
    this.lastPriodRepay = lastPriodRepay;
  }

  public BigDecimal getLastPriodHavepay() {
    return lastPriodHavepay;
  }

  public void setLastPriodHavepay(BigDecimal lastPriodHavepay) {
    this.lastPriodHavepay = lastPriodHavepay;
  }

  public BigDecimal getMissPay() {
    return missPay;
  }

  public void setMissPay(BigDecimal missPay) {
    this.missPay = missPay;
  }

  public Short getPayOff() {
    return payOff;
  }

  public void setPayOff(Short payOff) {
    this.payOff = payOff;
  }

  public Short getOverduePay() {
    return overduePay;
  }

  public void setOverduePay(Short overduePay) {
    this.overduePay = overduePay;
  }

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public Integer getMinNum() {
    return minNum;
  }

  public void setMinNum(Integer minNum) {
    this.minNum = minNum;
  }

  public Integer getMaxNum() {
    return maxNum;
  }

  public void setMaxNum(Integer maxNum) {
    this.maxNum = maxNum;
  }

  public Date getSettlementDateStarttime() {
    return settlementDateStarttime;
  }

  public void setSettlementDateStarttime(Date settlementDateStarttime) {
    this.settlementDateStarttime = settlementDateStarttime;
  }

  public Date getSettlementDateEndtime() {
    return settlementDateEndtime;
  }

  public void setSettlementDateEndtime(Date settlementDateEndtime) {
    this.settlementDateEndtime = settlementDateEndtime;
  }

  public Date getStartDateEndtime() {
    return startDateEndtime;
  }

  public void setStartDateEndtime(Date startDateEndtime) {
    this.startDateEndtime = startDateEndtime;
  }

  public Date getStartDateStarttime() {
    return startDateStarttime;
  }

  public void setStartDateStarttime(Date startDateStarttime) {
    this.startDateStarttime = startDateStarttime;
  }

  @Override
  public String toString() {
    return "ReserverBill [billId=" + billId + ", reserveId=" + reserveId + ", loginId=" + loginId
        + ", billName=" + billName + ", startDate=" + startDate + ", settlementDate="
        + settlementDate + ", repayDate=" + repayDate + ", bill=" + bill + ", repay=" + repay
        + ", currentRepayed=" + currentRepayed + ", advanceRepayed=" + advanceRepayed
        + ", lastPriodRepay=" + lastPriodRepay + ", lastPriodHavepay=" + lastPriodHavepay
        + ", missPay=" + missPay + ", payOff=" + payOff + ", overduePay=" + overduePay
        + ", corporateName=" + corporateName + ", accountLogin=" + accountLogin + ", minNum="
        + minNum + ", maxNum=" + maxNum + ", settlementDateStarttime=" + settlementDateStarttime
        + ", settlementDateEndtime=" + settlementDateEndtime + ", startDateEndtime="
        + startDateEndtime + ", startDateStarttime=" + startDateStarttime + "]";
  }



}
