package com.pltfm.app.vobject;

import java.io.Serializable;

import com.pltfm.app.entities.OrderPreferential;

/**
 * 优惠明细VO
 */
public class OrderPreferentialEVO extends OrderPreferential implements Serializable {
  private static final long serialVersionUID = 1L;

  public String getOrderPreferentialTypeStr() {
    return orderPreferentialTypeStr;
  }

  public void setOrderPreferentialTypeStr(String orderPreferentialTypeStr) {
    this.orderPreferentialTypeStr = orderPreferentialTypeStr;
  }

  private String orderPreferentialTypeStr;// 优惠类型

}
