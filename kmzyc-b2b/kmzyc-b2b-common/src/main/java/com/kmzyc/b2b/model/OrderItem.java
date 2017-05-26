package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.exception.ObjectTransformException;

public class OrderItem implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -6961122831195374115L;

  // private static Logger logger = Logger.getLogger(OrderItem.class);
  private static Logger logger = LoggerFactory.getLogger(OrderItem.class);

  private Long orderItemId;

  private String orderCode;

  private String commodityName;

  private String commodityCode;

  private String commoditySku;

  private String commodityBatchNumber;

  private BigDecimal commodityCalledPrice;

  private BigDecimal commodityUnitPrice;

  private Long commodityNumber;

  private BigDecimal commoditySum;
  /**
   * 单品实收
   */
  private BigDecimal commodityUnitIncoming;

  private BigDecimal commodityCalledSum;

  private String commodityDescription;

  private BigDecimal warehouseId;

  private Long credits;

  private String imageUrl;

  private BigDecimal commodityPv;

  // 新增查询出来的SKU_ID;
  private Long productSkuId;
  // 存储数据
  private Long prodappraiseId;
  private Long commodityType;
  private ProdAppraise prodApraiseList;


  /*
   * 产品主图
   */
  private ProductImage defaultProductImage;


  private List<AppraiseAddtoContent> appraiseAdd;

  private List<AppraiseReply> appraiseReplyList;

  // 供应商
  private String supplier;
  // 供应商名称
  private String supplierName;
  // 供应商类型
  private Long supplierType;
  // 退换货数量（PV对账用）
  private Long alterNum;

  private BigDecimal outRebateMoney;
  private Long rebateType;
  private BigDecimal appraiseId;
  /**
   * 预售ID
   */
  private Long presellId;

  public com.pltfm.app.entities.OrderItem transFormToRemoteAddress()
      throws ObjectTransformException {
    com.pltfm.app.entities.OrderItem orderItem = new com.pltfm.app.entities.OrderItem();
    try {
      // 转换名称相同的属性
      BeanUtils.copyProperties(orderItem, this);
    } catch (IllegalAccessException e) {
      logger.error("将本地OrderItem对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地OrderItem对象转换为远程对象出错！");
    } catch (InvocationTargetException e) {
      logger.error("将本地OrderItem对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地OrderItem对象转换为远程对象出错！");
    }

    return orderItem;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public String getCommoditySku() {
    return commoditySku;
  }

  public void setCommoditySku(String commoditySku) {
    this.commoditySku = commoditySku;
  }

  public String getCommodityBatchNumber() {
    return commodityBatchNumber;
  }

  public void setCommodityBatchNumber(String commodityBatchNumber) {
    this.commodityBatchNumber = commodityBatchNumber;
  }

  public BigDecimal getCommodityCalledPrice() {
    return commodityCalledPrice;
  }

  public void setCommodityCalledPrice(BigDecimal commodityCalledPrice) {
    this.commodityCalledPrice = commodityCalledPrice;
  }

  public BigDecimal getCommodityUnitPrice() {
    return commodityUnitPrice;
  }

  public void setCommodityUnitPrice(BigDecimal commodityUnitPrice) {
    this.commodityUnitPrice = commodityUnitPrice;
  }

  public Long getCommodityNumber() {
    return commodityNumber;
  }

  public void setCommodityNumber(Long commodityNumber) {
    this.commodityNumber = commodityNumber;
  }

  public BigDecimal getCommoditySum() {
    return commoditySum;
  }

  public void setCommoditySum(BigDecimal commoditySum) {
    this.commoditySum = commoditySum;
  }

  public BigDecimal getCommodityCalledSum() {
    return commodityCalledSum;
  }

  public void setCommodityCalledSum(BigDecimal commodityCalledSum) {
    this.commodityCalledSum = commodityCalledSum;
  }

  public String getCommodityDescription() {
    return commodityDescription;
  }

  public void setCommodityDescription(String commodityDescription) {
    this.commodityDescription = commodityDescription;
  }

  public BigDecimal getWarehouseId() {
    return warehouseId;
  }

  public void setWarehouseId(BigDecimal warehouseId) {
    this.warehouseId = warehouseId;
  }

  public Long getCredits() {
    return credits;
  }

  public void setCredits(Long credits) {
    this.credits = credits;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public ProductImage getDefaultProductImage() {
    return defaultProductImage;
  }

  public void setDefaultProductImage(ProductImage defaultProductImage) {
    this.defaultProductImage = defaultProductImage;
  }

  @Override
  public String toString() {
    return "OrderItem [orderItemId=" + orderItemId + ", orderCode=" + orderCode
        + ", commodityName=" + commodityName + ", commodityCode=" + commodityCode
        + ", commoditySku=" + commoditySku + ", commodityBatchNumber=" + commodityBatchNumber
        + ", commodityCalledPrice=" + commodityCalledPrice + ", commodityUnitPrice="
        + commodityUnitPrice + ", commodityNumber=" + commodityNumber + ", commoditySum="
        + commoditySum + ", commodityCalledSum=" + commodityCalledSum + ", commodityDescription="
        + commodityDescription + ", warehouseId=" + warehouseId + ", credits=" + credits
        + ", imageUrl=" + imageUrl + ",commodityType=" + commodityType + "]";
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public Long getProdappraiseId() {
    return prodappraiseId;
  }

  public void setProdappraiseId(Long prodappraiseId) {
    this.prodappraiseId = prodappraiseId;
  }

  // 是否正在退换货
  private Long isReturning;
  private Long isOverdue;

  public Long getIsOverdue() {
    return isOverdue;
  }

  public void setIsOverdue(Long isOverdue) {
    this.isOverdue = isOverdue;
  }

  public Long getIsReturning() {
    return isReturning;
  }

  public void setIsReturning(Long isReturning) {
    this.isReturning = isReturning;
  }

  public List<AppraiseAddtoContent> getAppraiseAdd() {
    return appraiseAdd;
  }

  public void setAppraiseAdd(List<AppraiseAddtoContent> appraiseAdd) {
    this.appraiseAdd = appraiseAdd;
  }

  public ProdAppraise getProdApraiseList() {
    return prodApraiseList;
  }

  public void setProdApraiseList(ProdAppraise prodApraiseList) {
    this.prodApraiseList = prodApraiseList;
  }

  public List<AppraiseReply> getAppraiseReplyList() {
    return appraiseReplyList;
  }

  public void setAppraiseReplyList(List<AppraiseReply> appraiseReplyList) {
    this.appraiseReplyList = appraiseReplyList;
  }

  public Long getCommodityType() {
    return commodityType;
  }

  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  public BigDecimal getCommodityPv() {
    return commodityPv;
  }

  public void setCommodityPv(BigDecimal commodityPv) {
    this.commodityPv = commodityPv;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public Long getSupplierType() {
    return supplierType;
  }

  public void setSupplierType(Long supplierType) {
    this.supplierType = supplierType;
  }

  public Long getAlterNum() {
    return alterNum;
  }

  public void setAlterNum(Long alterNum) {
    this.alterNum = alterNum;
  }

  public BigDecimal getOutRebateMoney() {
    return outRebateMoney;
  }

  public void setOutRebateMoney(BigDecimal outRebateMoney) {
    this.outRebateMoney = outRebateMoney;
  }

  public Long getRebateType() {
    return rebateType;
  }

  public void setRebateType(Long rebateType) {
    this.rebateType = rebateType;
  }

  public BigDecimal getCommodityUnitIncoming() {
    return commodityUnitIncoming;
  }

  public void setCommodityUnitIncoming(BigDecimal commodityUnitIncoming) {
    this.commodityUnitIncoming = commodityUnitIncoming;
  }

  public BigDecimal getAppraiseId() {
    return appraiseId;
  }

  public void setAppraiseId(BigDecimal appraiseId) {
    this.appraiseId = appraiseId;
  }

  
  public Long getPresellId() {
    return presellId;
  }

  public void setPresellId(Long presellId) {
    this.presellId = presellId;
  }

}
