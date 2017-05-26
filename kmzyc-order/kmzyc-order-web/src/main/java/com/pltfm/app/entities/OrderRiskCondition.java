package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 风控条件
 * 
 * @author xlg
 * 
 */
public class OrderRiskCondition implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * 缓存key
   */
  private String key;
  /**
   * 条件
   */
  private BigDecimal condition;
  /**
   * 得分
   */
  private Integer score;

  /**
   * 限额
   */
  private BigDecimal max;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public BigDecimal getCondition() {
    return condition;
  }

  public void setCondition(BigDecimal condition) {
    this.condition = condition;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public BigDecimal getMax() {
    return max;
  }

  public void setMax(BigDecimal max) {
    this.max = max;
  }
}
