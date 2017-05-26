package com.kmzyc.b2b.model;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.exception.ObjectTransformException;

public class OrderPreferential {
  // private static Logger logger = Logger.getLogger(AccountInfo.class);
  private static Logger logger = LoggerFactory.getLogger(OrderPreferential.class);

  private Long orderPreferentialId;

  private String orderCode;

  private Long orderItemId;

  private Long orderPreferentialType;

  private Long orderPreferentialSource;

  private String orderPreferentialCode;

  private BigDecimal orderPreferentialSum;
  /**
   * 活动名
   */
  private String promotionName;

  public com.pltfm.app.vobject.OrderPreferentialVO transFormToRemoteAddress()
      throws ObjectTransformException {
    com.pltfm.app.vobject.OrderPreferentialVO orderPreferential =
        new com.pltfm.app.vobject.OrderPreferentialVO();
    try {
      // 转换名称相同的属性
      BeanUtils.copyProperties(orderPreferential, this);
    } catch (IllegalAccessException e) {
      logger.error("将本地OrderPreferentialVO对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地OrderPreferentialVO对象转换为远程对象出错！");
    } catch (InvocationTargetException e) {
      logger.error("将本地OrderPreferentialVO对象转换为远程对象出错：" + e.getMessage(), e);
      throw new ObjectTransformException("将本地OrderPreferentialVO对象转换为远程对象出错！");
    }

    return orderPreferential;
  }

  public Long getOrderPreferentialId() {
    return orderPreferentialId;
  }

  public void setOrderPreferentialId(Long orderPreferentialId) {
    this.orderPreferentialId = orderPreferentialId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public Long getOrderPreferentialType() {
    return orderPreferentialType;
  }

  public void setOrderPreferentialType(Long orderPreferentialType) {
    this.orderPreferentialType = orderPreferentialType;
  }

  public Long getOrderPreferentialSource() {
    return orderPreferentialSource;
  }

  public void setOrderPreferentialSource(Long orderPreferentialSource) {
    this.orderPreferentialSource = orderPreferentialSource;
  }

  public String getOrderPreferentialCode() {
    return orderPreferentialCode;
  }

  public void setOrderPreferentialCode(String orderPreferentialCode) {
    this.orderPreferentialCode = orderPreferentialCode;
  }

  public BigDecimal getOrderPreferentialSum() {
    return orderPreferentialSum;
  }

  public void setOrderPreferentialSum(BigDecimal orderPreferentialSum) {
    this.orderPreferentialSum = orderPreferentialSum;
  }

  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }

}
