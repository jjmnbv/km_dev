package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LogisticAndDistributionInfoVO implements Serializable {

  private static final long serialVersionUID = -8299163003041246534L;
  // 物流单号
  private String logisticsOrderNo;
  // 物流公司代码
  private String logisticsCode;
  // 物流公司名称
  private String logisticsName;
  // 订单号
  private String orderCode;
  // 退换货单号
  private String orderAlterCode;
  // 配送单单号
  private Long distributionId;
  // 是否含有药品
  private boolean includeMedicineFlag;

  private String operator;// 操作人 名称
  private Integer operatorType;// 操作人类型，0位康美健康，1位供应商
  /*
   * //配送单信息（来源于产品系统） private DistributionInfo distributionInfo; //配送单信息（来源于产品系统） private
   * DistributionDetailInfo distributionDetailInfo;
   */
  // 配送单信息map
  private Map<Long, List<DistributionDetailInfo>> distributionInfoMap;

  public String getLogisticsOrderNo() {
    return logisticsOrderNo;
  }

  public void setLogisticsOrderNo(String logisticsOrderNo) {
    this.logisticsOrderNo = logisticsOrderNo;
  }

  public String getLogisticsCode() {
    return logisticsCode;
  }

  public void setLogisticsCode(String logisticsCode) {
    this.logisticsCode = logisticsCode;
  }

  public String getLogisticsName() {
    return logisticsName;
  }

  public void setLogisticsName(String logisticsName) {
    this.logisticsName = logisticsName;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  /*
   * public DistributionInfo getDistributionInfo() { return distributionInfo; } public void
   * setDistributionInfo(DistributionInfo distributionInfo) { this.distributionInfo =
   * distributionInfo; } public DistributionDetailInfo getDistributionDetailInfo() { return
   * distributionDetailInfo; } public void setDistributionDetailInfo(DistributionDetailInfo
   * distributionDetailInfo) { this.distributionDetailInfo = distributionDetailInfo; }
   */
  public Map<Long, List<DistributionDetailInfo>> getDistributionInfoMap() {
    return distributionInfoMap;
  }

  public void setDistributionInfoMap(Map<Long, List<DistributionDetailInfo>> distributionInfoMap) {
    this.distributionInfoMap = distributionInfoMap;
  }

  public String getOrderAlterCode() {
    return orderAlterCode;
  }

  public void setOrderAlterCode(String orderAlterCode) {
    this.orderAlterCode = orderAlterCode;
  }

  public Long getDistributionId() {
    return distributionId;
  }

  public void setDistributionId(Long distributionId) {
    this.distributionId = distributionId;
  }

  public boolean isIncludeMedicineFlag() {
    return includeMedicineFlag;
  }

  public void setIncludeMedicineFlag(boolean includeMedicineFlag) {
    this.includeMedicineFlag = includeMedicineFlag;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Integer getOperatorType() {
    return operatorType;
  }

  public void setOperatorType(Integer operatorType) {
    this.operatorType = operatorType;
  }

}
