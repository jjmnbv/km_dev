package com.pltfm.app.util.pay;

public enum OrderAlterPayBackEnum {
  Yeepay(2, "易宝", 2, "Yeepay"), AliPay(3, "支付宝", 3, "Aliay"), TenPay(4, "财付通", 4, "TenPay"), WeiXin(
      5, "微信", 5, "WeiXin"), ShiDai(6, "时代", 6, "ShiDai"), KMT(7, "康美通", 7, "KMT");

  private int key;
  private String value;
  private int index;
  private String code;

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

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  /**
   * 构造函数
   */
  private OrderAlterPayBackEnum(int key, String value, int index, String code) {
    this.key = key;
    this.value = value;
    this.index = index;
    this.code = code;
  }

  public static String getValueByKey(int key) {
    for (OrderAlterPayBackEnum o : OrderAlterPayBackEnum.values()) {
      if (o.getKey() == key) {
        return o.getValue();
      }
    }
    return null;
  }

  public static int getKeyByIndex(int index) {
    for (OrderAlterPayBackEnum o : OrderAlterPayBackEnum.values()) {
      if (o.getIndex() == index) {
        return o.getKey();
      }
    }
    return 0;
  }
}
