package com.pltfm.app.vobject;

import java.io.Serializable;

import com.pltfm.app.entities.Invoice;

public class InvoiceVo extends Invoice implements Serializable {
  private static final long serialVersionUID = 1L;

  // 发票类型
  private String createTypeStr;

  public String getCreateTypeStr() {
    return createTypeStr;
  }

  public void setCreateTypeStr(String createTypeStr) {
    this.createTypeStr = createTypeStr;
  }

}
