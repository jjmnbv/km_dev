package com.pltfm.app.vobject;

import java.io.Serializable;

import com.pltfm.app.entities.OrderAlterOperateStatement;

public class OrderAlterOperateStatementVo extends OrderAlterOperateStatement
    implements
      Serializable {
  private static final long serialVersionUID = 1L;

  private String nowAlterStatusStr;// 退换货单状态

  public String getNowAlterStatusStr() {
    return nowAlterStatusStr;
  }

  public void setNowAlterStatusStr(String nowAlterStatusStr) {
    this.nowAlterStatusStr = nowAlterStatusStr;
  }

  private String nowOperateTypeStr;// 操作类型

  public String getNowOperateTypeStr() {
    return nowOperateTypeStr;
  }

  public void setNowOperateTypeStr(String nowOperateTypeStr) {
    this.nowOperateTypeStr = nowOperateTypeStr;
  }

}
