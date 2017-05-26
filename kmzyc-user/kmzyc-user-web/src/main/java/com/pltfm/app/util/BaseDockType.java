package com.pltfm.app.util;

public enum BaseDockType {

  USER_LEVEL(1, "user_level"), USER_MODIFY_PUSH(2, "user_modify_push"), ORDER_PAID_PUSH(3,
      "order_paid_push"), ORDER_ALTER_PUSH(3, "order_alter_push"), USER_SCOREINFO(1,
          "user_score_info"), USER_REGISTER(1, "user_rigister");



  private BaseDockType(int key, String value) {
    this.key = key;
    this.value = value;
  }

  private int key;
  private String value;

  public int getKey() {
    return key;
  }


  public String getValue() {
    return value;
  }

}
