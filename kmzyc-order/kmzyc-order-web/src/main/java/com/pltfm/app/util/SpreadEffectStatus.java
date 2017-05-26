package com.pltfm.app.util;



/**
 * 推广效果订单状态
 * 
 * @author liajinjun
 */
public enum SpreadEffectStatus {
  ORDER_CANCELED(0, "订单取消"), NOT_PAYED(1, "未支付"), ALREADY_PAYED(2, "已支付"), FINISHED(3, "已完成"), SETTED(
      4, "已结算"), SETT_FORBIDEN(5, "禁止结算"), ALREADY_DISTRI(6, "已配送");

  private int key;
  private String value;

  private SpreadEffectStatus(int key, String value) {
    this.key = key;
    this.value = value;
  }


  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("SpreadEffectStatus[key=").append(this.key).append(",value=").append(
        this.value).append("]");
    return strBuilder.toString();
  }

}
