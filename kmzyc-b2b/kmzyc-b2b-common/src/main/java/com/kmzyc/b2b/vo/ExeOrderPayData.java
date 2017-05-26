package com.kmzyc.b2b.vo;

import java.io.Serializable;

public class ExeOrderPayData implements Serializable {

  private static final long serialVersionUID = -8299163003041246534L;
  private String orderCode;
  private String paytype;// 支付方式编码
  private String payname;// 支付方式名称
  private String paytime;// 支付时间
  private String amount;// 支付金额

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getPaytype() {
    return paytype;
  }

  public void setPaytype(String paytype) {
    this.paytype = paytype;
  }

  public String getPayname() {
    return payname;
  }

  public void setPayname(String payname) {
    this.payname = payname;
  }

  public String getPaytime() {
    return paytime;
  }

  public void setPaytime(String paytime) {
    this.paytime = paytime;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("<payment>");
    if (null != paytype) sb.append("<paytype><![CDATA[").append(paytype).append("]]></paytype>");
    if (null != payname) sb.append("<payname><![CDATA[").append(payname).append("]]></payname>");
    if (null != paytime) sb.append("<paytime><![CDATA[").append(paytime).append("]]></paytime>");
    if (null != amount) sb.append("<amount><![CDATA[").append(amount).append("]]></amount>");
    sb.append("</payment>");
    return sb.toString();
  }
}
