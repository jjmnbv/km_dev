package com.kmzyc.framework.constants;

public enum OrderAssessPoint {
  ONE_POINT("1", "(差得太离谱，非常不满)"), TWO_POINT("2", "(与卖家描述的不符，不满意)"), THREE_POINT("3",
      "(一般，没有卖家描述的那么好)"), FOUR_POINT("4", "(不错，还是挺满意的)"), FIVE_POINT("5", "(非常好，让人感到惊喜)");

  private String titile;

  private String type;

  OrderAssessPoint(String types, String title) {
    this.type = types;
    this.titile = title;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return titile;
  }

  public void setTitle(String title) {
    this.titile = title;
  }

  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("orderAssessPoint[type=").append(this.type).append(",title=").append(
        this.titile).append("]");
    return strBuilder.toString();
  }
}
