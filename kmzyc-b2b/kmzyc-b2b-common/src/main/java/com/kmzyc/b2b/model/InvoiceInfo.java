package com.kmzyc.b2b.model;

import java.io.Serializable;

public class InvoiceInfo implements Serializable {
  private static final long serialVersionUID = 7348076957136168622L;

  // 是否索取发票
  private boolean isNeed;
  // 发票类型
  private String invoiceType;
  // 发票抬头
  private String invoicetitle;
  // 发票内容
  private String invoiceContent;

  public boolean isNeed() {
    return isNeed;
  }

  public void setNeed(boolean isNeed) {
    this.isNeed = isNeed;
  }

  public String getInvoiceType() {
    return invoiceType;
  }

  public void setInvoiceType(String invoiceType) {
    this.invoiceType = invoiceType;
  }

  public String getInvoicetitle() {
    return invoicetitle;
  }

  public void setInvoicetitle(String invoicetitle) {
    this.invoicetitle = invoicetitle;
  }

  public String getInvoiceContent() {
    return invoiceContent;
  }

  public void setInvoiceContent(String invoiceContent) {
    this.invoiceContent = invoiceContent;
  }
}
