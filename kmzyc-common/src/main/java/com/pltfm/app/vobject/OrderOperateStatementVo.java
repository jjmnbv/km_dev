package com.pltfm.app.vobject;

import java.io.Serializable;

import com.pltfm.app.entities.OrderOperateStatement;

public class OrderOperateStatementVo extends OrderOperateStatement implements Serializable {
  private static final long serialVersionUID = 1L;

  private String nowOrderStatusStr;// 订单状态

  public String getNowOrderStatusStr() {
    return nowOrderStatusStr;
  }

  public void setNowOrderStatusStr(String nowOrderStatusStr) {
    this.nowOrderStatusStr = nowOrderStatusStr;
  }

  private String nowOperateTypeStr;// 操作类型

  public String getNowOperateTypeStr() {
    return nowOperateTypeStr;
  }

  public void setNowOperateTypeStr(String nowOperateTypeStr) {
    this.nowOperateTypeStr = nowOperateTypeStr;
  }

}
