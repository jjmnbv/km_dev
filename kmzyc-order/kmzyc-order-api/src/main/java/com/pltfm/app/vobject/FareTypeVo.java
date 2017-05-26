package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

import com.pltfm.app.entities.FareType;

public class FareTypeVo extends FareType implements Serializable {

  private static final long serialVersionUID = -7792618110378674315L;

  private boolean isInGDProvince;

  private BigDecimal weight;

  private BigDecimal sum;

  public boolean getIsInGDProvince() {
    return isInGDProvince;
  }

  public void setInGDProvince(boolean isInGDProvince) {
    this.isInGDProvince = isInGDProvince;
  }

  public BigDecimal getWeight() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  public BigDecimal getSum() {
    return sum;
  }

  public void setSum(BigDecimal sum) {
    this.sum = sum;
  }

}
