package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;


public class ShopCartProductReminder implements Serializable {
  private static final long serialVersionUID = 1L;
  private int code = 0;// 默认0 正常
  private String message;


  /** 可以正常购买 */
  private boolean normal = true;

  /** 默认0正常 */
  public int getCode() {
    return code;
  }

  /** 默认NORMAL 正常 */
  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /** 可以正常购买 */
  public boolean isNormal() {
    return normal;
  }

  /** 可以正常购买 */
  public void setNormal(boolean normal) {
    this.normal = normal;
  }



}
