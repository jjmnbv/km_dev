package com.pltfm.app.util;

import java.io.Serializable;

/**
 * 余额提现状态枚举类
 * 
 * @since 15-04-23
 * @author lijianjun
 * 
 */
public enum EnchashmentStatusEnumType implements Serializable {
  Status_Examine("0", "待审核"), Stuats_Complete("1", "提现完成"), Status_Refused("2",
      "审核拒绝"), Status_Pass("3", "审核通过"), Status_Fail("4", "提现失败");

  private String type;
  private String title;



  private EnchashmentStatusEnumType(String type, String title) {
    this.type = type;
    this.title = title;
  }

  public String getType() {
    return type;
  }


  public String getTitle() {
    return title;
  }




  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("EnchashmentStatusEnumType[type=").append(this.type).append(",title=")
        .append(this.title).append("]");
    return strBuilder.toString();
  }



}
