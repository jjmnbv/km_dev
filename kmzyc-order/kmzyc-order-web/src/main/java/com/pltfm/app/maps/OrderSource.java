package com.pltfm.app.maps;

public enum OrderSource {
  WEB(0, "1", "web"), APP(1, "2", "app"), WEIXIN(2, "3", "微信"), WAP(3, "4", "wap");
  private int index;
  private String key;
  private String value;

  private OrderSource(int index, String key, String value) {
    this.index = index;
    this.key = key;
    this.value = value;
  }

  public int getIndex() {
    return index;
  }


  public String getKey() {
    return key;
  }


  public String getValue() {
    return value;
  }


  public static String getValueByKey(String key) {
    if (null != key) {
      for (OrderSource oc : OrderSource.values()) {
        if (key.equals(oc.getKey())) {
          return oc.getValue();
        }
      }
    }
    return null;
  }
}
