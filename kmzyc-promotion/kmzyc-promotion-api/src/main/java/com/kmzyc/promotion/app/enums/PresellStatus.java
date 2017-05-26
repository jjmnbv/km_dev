package com.kmzyc.promotion.app.enums;

public enum PresellStatus {
  /**
   * 预售状态，0：草稿，1：提交审核（待审核），3：终止，--动态状态：4：支付定金，5：未到尾款支付时间，6：支付尾款，7：已结束--
   */

  DRAFT(0, "草稿"), PRE_AUDIT(1, "待审核"), PRESELL_STOP(3, "终止"), DEPOSIT_TIME(4, "支付定金"), NO_FINALPAY_TIME(
      5, "未到尾款支付时间"), FINALPAY_TIME(6, "支付尾款"), PRESELL_END(7, "已结束");
  private Integer status;
  private String title = null;

  private PresellStatus(Integer status, String title) {
    this.status = status;
    this.title = title;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("PresellStatus[status=").append(this.status).append(",title=")
        .append(this.title).append("]");
    return strBuilder.toString();
  }
}
