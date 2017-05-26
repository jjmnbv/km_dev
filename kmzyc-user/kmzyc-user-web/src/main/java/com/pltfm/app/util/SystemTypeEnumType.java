package com.pltfm.app.util;

public enum SystemTypeEnumType {
  SYSTEM_TYPE_ORDER(2, "订单系统");

  private int type;
  private String title;

  SystemTypeEnumType(int type, String title) {
    this.type = type;
    this.title = title;
  }

  public int getType() {
    return type;
  }


  public String getTitle() {
    return title;
  }


  @Override
  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("SystemTypeEnumType[type=").append(this.type).append(",title=")
        .append(this.title).append("]");
    return strBuilder.toString();
  }



}
