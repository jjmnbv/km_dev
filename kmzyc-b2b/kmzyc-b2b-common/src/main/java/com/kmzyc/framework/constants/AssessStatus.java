package com.kmzyc.framework.constants;

public enum AssessStatus {
  /**
   * 未评价
   */
  Assess_Wait("1", "未评价"),
  /**
   * 已评价
   */
  Assess_Done("2", "已评价"),

  Assess_Addition("3", "已追评");

  private String status = null;
  private String title = null;

  AssessStatus(String status, String title) {
    this.status = status;
    this.title = title;
  }

  public String getStatus() {
    return status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
