package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ExeOrderData implements Serializable {
  private static final long serialVersionUID = -8299163003041246534L;
  private String tid;// 订单id
  private String status;// 订单状态
  private String modified;// 订单最后修改时间(北京时间)
  private String created;// 订单创建时间(北京时间)
  private String shipping_type;// 快递方式
  private BigDecimal post_fee = BigDecimal.ZERO;// 运费
  private String receiver_name;// 收货人姓名
  private String receiver_state;// 收货省份
  private String receiver_city;// 收货市
  private String receiver_district;// 收货地区
  private String receiver_address;// 收货人地址
  private String receiver_zip;// 邮编
  private String receiver_mobile;// 收货人手机
  private String receiver_phone;// 收货人电话
  private String buyer_nick;// 会员编码
  private String buyer_name;// 会员名称
  private String is_tax;// 是否开票
  private String invoice_type;// 开票类型
  private String invoice_title;// 开票抬头
  private BigDecimal real_pay;// 实际支付金额
  private BigDecimal total_fee;// 商品总金额
  private String buyer_message;// 用户备注
  private BigDecimal buyer_cod_fee;// 买家货到付款服务费
  private BigDecimal point_fee;// 积分支付
  private BigDecimal coupon_pay;// 优惠券支付
  private String paytime;// 付款时间
  private String seller_memo;// 卖家备注
  private String pay_type;// 支付方式
  private List<ExeOrderPayData> payments;
  private String paymentsStr;
  private List<ExeOrderItemData> itemlist;// 商品列表

  public String getTid() {
    return tid;
  }

  public void setTid(String tid) {
    this.tid = tid;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getModified() {
    return modified;
  }

  public void setModified(String modified) {
    this.modified = modified;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getShipping_type() {
    return shipping_type;
  }

  public void setShipping_type(String shippingType) {
    shipping_type = shippingType;
  }

  public BigDecimal getPost_fee() {
    return post_fee;
  }

  public void setPost_fee(BigDecimal postFee) {
    post_fee = postFee;
  }

  public String getReceiver_name() {
    return receiver_name;
  }

  public void setReceiver_name(String receiverName) {
    receiver_name = receiverName;
  }

  public String getReceiver_state() {
    return receiver_state;
  }

  public void setReceiver_state(String receiverState) {
    receiver_state = receiverState;
  }

  public String getReceiver_city() {
    return receiver_city;
  }

  public void setReceiver_city(String receiverCity) {
    receiver_city = receiverCity;
  }

  public String getReceiver_district() {
    return receiver_district;
  }

  public void setReceiver_district(String receiverDistrict) {
    receiver_district = receiverDistrict;
  }

  public String getReceiver_address() {
    return receiver_address;
  }

  public void setReceiver_address(String receiverAddress) {
      String regex = "[^0-9a-zA-Z\u4e00-\u9fa5-_#():]+";//只允许字母，数字，中文,特殊字符 '-' '_' '(' ')' ':'的正则表达      
      receiver_address =receiverAddress.replaceAll(regex, "");
  }

  public String getReceiver_zip() {
    return receiver_zip;
  }

  public void setReceiver_zip(String receiverZip) {
    receiver_zip = receiverZip;
  }

  public String getReceiver_mobile() {
    return receiver_mobile;
  }

  public void setReceiver_mobile(String receiverMobile) {
    receiver_mobile = receiverMobile;
  }

  public String getReceiver_phone() {
    return receiver_phone;
  }

  public void setReceiver_phone(String receiverPhone) {
    receiver_phone = receiverPhone;
  }

  public String getBuyer_nick() {
    return buyer_nick;
  }

  public void setBuyer_nick(String buyerNick) {
    buyer_nick = buyerNick;
  }

  public String getBuyer_name() {
    return buyer_name;
  }

  public void setBuyer_name(String buyerName) {
    buyer_name = buyerName;
  }

  public String getIs_tax() {
    return is_tax;
  }

  public void setIs_tax(String isTax) {
    is_tax = isTax;
  }

  public String getInvoice_type() {
    return invoice_type;
  }

  public void setInvoice_type(String invoiceType) {
    invoice_type = invoiceType;
  }

  public String getInvoice_title() {
    return invoice_title;
  }

  public void setInvoice_title(String invoiceTitle) {
    invoice_title = invoiceTitle;
  }

  public String getPay_type() {
    return pay_type;
  }

  public void setPay_type(String payType) {
    pay_type = payType;
  }

  public List<ExeOrderPayData> getPayments() {
    return payments;
  }

  public void setPayments(List<ExeOrderPayData> payments) {
    this.payments = payments;
  }

  public BigDecimal getReal_pay() {
    return real_pay;
  }

  public void setReal_pay(BigDecimal realPay) {
    real_pay = realPay;
  }

  public BigDecimal getTotal_fee() {
    return total_fee;
  }

  public void setTotal_fee(BigDecimal totalFee) {
    total_fee = totalFee;
  }

  public String getBuyer_message() {
    return buyer_message;
  }

  public void setBuyer_message(String buyerMessage) {
    buyer_message = buyerMessage;
  }

  public BigDecimal getBuyer_cod_fee() {
    return buyer_cod_fee;
  }

  public void setBuyer_cod_fee(BigDecimal buyerCodFee) {
    buyer_cod_fee = buyerCodFee;
  }

  public BigDecimal getPoint_fee() {
    return point_fee;
  }

  public void setPoint_fee(BigDecimal pointFee) {
    point_fee = pointFee;
  }

  public BigDecimal getCoupon_pay() {
    return coupon_pay;
  }

  public void setCoupon_pay(BigDecimal couponPay) {
    coupon_pay = couponPay;
  }

  public String getPaytime() {
    return paytime;
  }

  public void setPaytime(String paytime) {
    this.paytime = paytime;
  }

  public String getSeller_memo() {
    return seller_memo;
  }

  public void setSeller_memo(String sellerMemo) {
    seller_memo = sellerMemo;
  }

  public List<ExeOrderItemData> getItemlist() {
    return itemlist;
  }

  public void setItemlist(List<ExeOrderItemData> itemlist) {
    this.itemlist = itemlist;
  }

  public String getPaymentsStr() {
    return paymentsStr;
  }

  public void setPaymentsStr(String paymentsStr) {
    this.paymentsStr = paymentsStr;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("<order>");
    sb.append("<tid><![CDATA[").append(tid).append("]]></tid>");
    sb.append("<status><![CDATA[").append(status).append("]]></status>");
    sb.append("<modified><![CDATA[").append(modified).append("]]></modified>");
    sb.append("<created><![CDATA[").append(created).append("]]></created>");
    if (null != shipping_type)
      sb.append("<shipping_type><![CDATA[").append(shipping_type).append("]]></shipping_type>");
    if (null != post_fee)
      sb.append("<post_fee><![CDATA[").append(post_fee).append("]]></post_fee>");
    sb.append("<receiver_name><![CDATA[").append(receiver_name).append("]]></receiver_name>");
    sb.append("<receiver_state><![CDATA[").append(receiver_state).append("]]></receiver_state>");
    sb.append("<receiver_city><![CDATA[").append(receiver_city).append("]]></receiver_city>");
    sb.append("<receiver_district><![CDATA[").append(receiver_district).append(
        "]]></receiver_district>");
    sb.append("<receiver_address><![CDATA[").append(receiver_address).append(
        "]]></receiver_address>");
    if (null != receiver_zip)
      sb.append("<receiver_zip><![CDATA[").append(receiver_zip).append("]]></receiver_zip>");
    if (null != receiver_mobile)
      sb.append("<receiver_mobile><![CDATA[").append(receiver_mobile).append(
          "]]></receiver_mobile>");
    if (null != receiver_phone)
      sb.append("<receiver_phone><![CDATA[").append(receiver_phone).append("]]></receiver_phone>");
    if (null != buyer_nick)
      sb.append("<buyer_nick><![CDATA[").append(buyer_nick).append("]]></buyer_nick>");
    if (null != buyer_name)
      sb.append("<buyer_name><![CDATA[").append(buyer_name).append("]]></buyer_name>");
    if (null != buyer_name) sb.append("<is_tax><![CDATA[").append(is_tax).append("]]></is_tax>");
    if (null != invoice_type)
      sb.append("<invoice_type><![CDATA[").append(invoice_type).append("]]></invoice_type>");
    if (null != invoice_title)
      sb.append("<invoice_title><![CDATA[").append(invoice_title).append("]]></invoice_title>");
    if (null != real_pay)
      sb.append("<real_pay><![CDATA[").append(real_pay).append("]]></real_pay>");
    if (null != pay_type)
      sb.append("<pay_type><![CDATA[").append(pay_type).append("]]></pay_type>");
    if (null != total_fee)
      sb.append("<total_fee><![CDATA[").append(total_fee).append("]]></total_fee>");
    if (null != buyer_message)
      sb.append("<buyer_message><![CDATA[").append(buyer_message).append("]]></buyer_message>");
    if (null != buyer_cod_fee)
      sb.append("<buyer_cod_fee><![CDATA[").append(buyer_cod_fee).append("]]></buyer_cod_fee>");
    if (null != point_fee)
      sb.append("<point_fee><![CDATA[").append(point_fee).append("]]></point_fee>");
    if (null != coupon_pay)
      sb.append("<coupon_pay><![CDATA[").append(coupon_pay).append("]]></coupon_pay>");
    if (null != paytime) sb.append("<paytime><![CDATA[").append(paytime).append("]]></paytime>");
    if (null != seller_memo)
      sb.append("<seller_memo><![CDATA[").append(seller_memo).append("]]></seller_memo>");
    if (null != paymentsStr) {
      sb.append("<payments>").append(paymentsStr).append("</payments>");
    }
    if (null != itemlist && !itemlist.isEmpty()) {
      sb.append("<itemlist>");
      for (ExeOrderItemData eoid : itemlist) {
        sb.append(eoid.toString());
      }
      sb.append("</itemlist>");
    }
    sb.append("</order>");
    return sb.toString();
  }
}
