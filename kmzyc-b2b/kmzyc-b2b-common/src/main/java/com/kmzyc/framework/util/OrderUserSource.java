package com.kmzyc.framework.util;

public enum OrderUserSource {
  YIMA("yima", "易码"), CHENGGUOWANG("chengguowang", "成果网"), LANHAI("lanhai", "蓝海"), ERA("era", "时代"), KM("km", "康美");

    //YUNSHANG("yunshang", "云商"), FANLI("51fanli", "51返利"),
  private String type;
  private String title = null;

  OrderUserSource(String type, String title) {
    this.type = type;
    this.title = title;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


}
