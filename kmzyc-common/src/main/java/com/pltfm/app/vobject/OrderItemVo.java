package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

import com.pltfm.app.entities.OrderItem;

public class OrderItemVo extends OrderItem implements Serializable {
  private static final long serialVersionUID = 1L;

  // 是否正在退换货
  private Long isReturning;
  // orderitem扩展 返利率
  private BigDecimal outRebateRate;
  // orderitem扩展 返金额
  private BigDecimal outRebateMoney;

  public Long getIsReturning() {
    return isReturning;
  }

  public void setIsReturning(Long isReturning) {
    this.isReturning = isReturning;
  }

  public BigDecimal getOutRebateRate() {
    return outRebateRate;
  }

  public void setOutRebateRate(BigDecimal outRebateRate) {
    this.outRebateRate = outRebateRate;
  }

  public BigDecimal getOutRebateMoney() {
    return outRebateMoney;
  }

  public void setOutRebateMoney(BigDecimal outRebateMoney) {
    this.outRebateMoney = outRebateMoney;
  }


}
