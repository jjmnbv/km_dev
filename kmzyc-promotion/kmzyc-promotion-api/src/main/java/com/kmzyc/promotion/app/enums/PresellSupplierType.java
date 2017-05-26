package com.kmzyc.promotion.app.enums;

public enum PresellSupplierType {
  SELF_ENTER("1", "康美自营代销"), SELF_PROXY("2", "指定商家入驻");

  private String titile;

  private String type;

  private PresellSupplierType(String type, String titile) {
    this.titile = titile;
    this.type = type;
  }

  public String getTitile() {
    return titile;
  }

  public void setTitile(String titile) {
    this.titile = titile;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("PresellSupplierType[type=").append(this.type).append(",title=")
        .append(this.titile).append("]");
    return strBuilder.toString();
  }
}
