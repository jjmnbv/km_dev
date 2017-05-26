package com.kmzyc.b2b.model;

public class RefundParamInfo {
  /**
   * 业务类型
   */
  private String p0_Cmd = "RefundOrd";
  /**
   * 商户编号
   */
  private String p1_MerId;
  /**
   * 支付交易流水号
   */
  private String pb_TrxId;
  /**
   * 退款金额
   */
  private String p3_Amt;
  /**
   * 交易币种
   */
  private String p4_Cur = "CNY";
  /**
   * 退款说明
   */
  private String p5_Desc;
  /**
   * 签名校验
   */
  private String hmac;
  /**
   * 退款批次号
   */
  private String batchNo;
  /**
   * 扩展参数
   */
  private String extParmas;

  public String getP0_Cmd() {
    return p0_Cmd;
  }

  public void setP0_Cmd(String p0_Cmd) {
    this.p0_Cmd = p0_Cmd;
  }

  public String getP1_MerId() {
    return p1_MerId;
  }

  public void setP1_MerId(String p1_MerId) {
    this.p1_MerId = p1_MerId;
  }

  public String getPb_TrxId() {
    return pb_TrxId;
  }

  public void setPb_TrxId(String pb_TrxId) {
    this.pb_TrxId = pb_TrxId;
  }

  public String getP3_Amt() {
    return p3_Amt;
  }

  public void setP3_Amt(String p3_Amt) {
    this.p3_Amt = p3_Amt;
  }

  public String getP4_Cur() {
    return p4_Cur;
  }

  public void setP4_Cur(String p4_Cur) {
    this.p4_Cur = p4_Cur;
  }

  public String getP5_Desc() {
    return p5_Desc;
  }

  public void setP5_Desc(String p5_Desc) {
    this.p5_Desc = p5_Desc;
  }

  public String getHmac() {
    return hmac;
  }

  public void setHmac(String hmac) {
    this.hmac = hmac;
  }

  public String getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  public String getExtParmas() {
    return extParmas;
  }

  public void setExtParmas(String extParmas) {
    this.extParmas = extParmas;
  }
}
