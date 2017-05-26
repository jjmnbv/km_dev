package com.pltfm.app.maps;

public enum OrderChannel {
  B2B(0, "B2B", "KMB2B");
  private int index;
  private String key;
  private String value;

  OrderChannel(int index, String key, String value) {
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
      for (OrderChannel oc : OrderChannel.values()) {
        if (key.equals(oc.getKey())) {
          return oc.getValue();
        }
      }
    }
    return null;
  }
}
