package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 预售条件
 * 
 * @author Administrator
 * 
 */
public class PromotionPresellCriteria implements Serializable {
  private static final long serialVersionUID = 1L;
  // 预售ID
  private Long presellId;
  // 预售标题
  private String presellTitle;
  // 商家类别 ：1康美自营代销；2指定入驻商家；
  private Integer shopSort;
  // 活动所属入驻商家
  private Integer supplierId;
  // 预售状态，0：草稿，1：提交审核（待审核），3：终止，--动态状态：4：支付定金，5：未到尾款支付时间，6：支付尾款，7：已结束--
  private Integer presellStatus;
  // 审核状态,0：未审核，1：已审核，2：审核不通过
  private Integer auditStatus;
  // 初始预售人数
  private Long initialPresellNum;
  // 限购：每人最多购买，0：不限制，大于0：限制并为限购数量
  private Long byLimit;
  // 定金支付开始时间
  private Date depositStartTime;
  // 定金支付截止时间
  private Date depositEndTime;
  // 尾款支付开始时间
  private Date finalpayStartTime;
  // 尾款支付截止时间
  private Date finalpayEndTime;
  // 发货开始时间
  private Date deliveryStartTime;
  // 发货截止时间
  private Date deliveryEndTime;
  // 预售说明
  private String presellDescribe;
  // 创建时间
  private Date createTime;
  // 修改时间
  private Date modifyTime;
  // 创建人
  private Long createUser;
  // 修改人
  private Long modifUser;
  // 商品编码
  private String productNO;
  // 预售价格
  private Long presellPrice;
  // 产品标题
  private String productTitle;
  // 定金
  private Long depositPrice;
  private Date payTime;
  private Date payTime2;
  // 预售库存
  private String prsellStock;

  private String productSkuCode;

  // 自动结束处理标识 ：0未处理，1已处理
  private Integer autoStopFlag;

  /**
   * 分页条件
   */
  private Integer startIndex = 0;
  private Integer endIndex = 10;

  public Long getPresellId() {
    return presellId;
  }

  public void setPresellId(Long presellId) {
    this.presellId = presellId;
  }

  public String getPresellTitle() {
    return presellTitle;
  }

  public void setPresellTitle(String presellTitle) {
    this.presellTitle = presellTitle;
  }

  public Integer getShopSort() {
    return shopSort;
  }

  public void setShopSort(Integer shopSort) {
    this.shopSort = shopSort;
  }

  public Integer getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Integer supplierId) {
    this.supplierId = supplierId;
  }

  public Integer getPresellStatus() {
    return presellStatus;
  }

  public void setPresellStatus(Integer presellStatus) {
    this.presellStatus = presellStatus;
  }

  public Integer getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(Integer auditStatus) {
    this.auditStatus = auditStatus;
  }

  public Long getInitialPresellNum() {
    return initialPresellNum;
  }

  public void setInitialPresellNum(Long initialPresellNum) {
    this.initialPresellNum = initialPresellNum;
  }

  public Long getByLimit() {
    return byLimit;
  }

  public void setByLimit(Long byLimit) {
    this.byLimit = byLimit;
  }

  public Date getDepositStartTime() {
    return depositStartTime;
  }

  public void setDepositStartTime(Date depositStartTime) {
    this.depositStartTime = depositStartTime;
  }

  public Date getDepositEndTime() {
    return depositEndTime;
  }

  public void setDepositEndTime(Date depositEndTime) {
    this.depositEndTime = depositEndTime;
  }

  public Date getFinalpayStartTime() {
    return finalpayStartTime;
  }

  public void setFinalpayStartTime(Date finalpayStartTime) {
    this.finalpayStartTime = finalpayStartTime;
  }

  public Date getFinalpayEndTime() {
    return finalpayEndTime;
  }

  public void setFinalpayEndTime(Date finalpayEndTime) {
    this.finalpayEndTime = finalpayEndTime;
  }

  public Date getDeliveryStartTime() {
    return deliveryStartTime;
  }

  public void setDeliveryStartTime(Date deliveryStartTime) {
    this.deliveryStartTime = deliveryStartTime;
  }

  public Date getDeliveryEndTime() {
    return deliveryEndTime;
  }

  public void setDeliveryEndTime(Date deliveryEndTime) {
    this.deliveryEndTime = deliveryEndTime;
  }

  public String getPresellDescribe() {
    return presellDescribe;
  }

  public void setPresellDescribe(String presellDescribe) {
    this.presellDescribe = presellDescribe;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Long getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Long createUser) {
    this.createUser = createUser;
  }

  public Long getModifUser() {
    return modifUser;
  }

  public void setModifUser(Long modifUser) {
    this.modifUser = modifUser;
  }

  public Long getPresellPrice() {
    return presellPrice;
  }

  public void setPresellPrice(Long presellPrice) {
    this.presellPrice = presellPrice;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public Long getDepositPrice() {
    return depositPrice;
  }

  public void setDepositPrice(Long depositPrice) {
    this.depositPrice = depositPrice;
  }

  public Date getPayTime() {
    return payTime;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

  public Date getPayTime2() {
    return payTime2;
  }

  public void setPayTime2(Date payTime2) {
    this.payTime2 = payTime2;
  }

  public String getPrsellStock() {
    return prsellStock;
  }

  public void setPrsellStock(String prsellStock) {
    this.prsellStock = prsellStock;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
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

  public String getProductNO() {
    return productNO;
  }

  public void setProductNO(String productNO) {
    this.productNO = productNO;
  }

  public Integer getAutoStopFlag() {
    return autoStopFlag;
  }

  public void setAutoStopFlag(Integer autoStopFlag) {
    this.autoStopFlag = autoStopFlag;
  }


}
