package com.kmzyc.framework.constants;

/**
 * Description: User: lishiming Date: 13-12-2 上午10:26 Since: 1.0
 */
public enum ConsultCheckStatusEnum {

  Not_Check(0, "未审核"), Not_Pass(1, "审核不通过"), Pass(2, "审核通过");

  private int key;
  private String title;

  private ConsultCheckStatusEnum(int key, String title) {
    this.key = key;
    this.title = title;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
