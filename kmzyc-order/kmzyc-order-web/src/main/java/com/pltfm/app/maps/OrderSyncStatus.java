package com.pltfm.app.maps;

public enum OrderSyncStatus {
  SUCCESS(0, "成功"), FAIL(-1, "失败"), REPEAT(-2, "重复提交");
  private int key;
  private String value;

  private OrderSyncStatus(int key, String value) {
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

  public static String getValueByKey(String key) {
    if (null != key) {
      for (OrderSyncStatus oc : OrderSyncStatus.values()) {
        if (key.equals(String.valueOf(oc.getKey()))) {
          return oc.getValue();
        }
      }
    }
    return null;
  }
}
