package com.kmzyc.framework.constants;

/**
 *　账户余额交易类型
 * 
 * @author luoyi
 * @createDate 2013/11/08
 * 
 */
public enum RechargeTypeEnum {
  ONLINERECHARGE(1, "在线充值"), BACKGROUNDRECHARGE(2, "后台充值"), BALANCERECHARGE(3, "余额支付"), CANCELORDER(
      4, "取消订单"), ORDERRETURN(5, "订单退款"), ENCHASHMENT(6, "取现"), TRANREWARD(16, "邀请返利"), TRANSACTION_TYPE_MERCHANT(
      17, "商家结算"), TRANSACTION_TYPE_ENCHASHMENT(18, "商家取现"), TRANSACTION_TYPE_FREEZING(19, "余额冻结"), TRANSACTION_TYPE_THAW(
      20, "余额解冻"), TRANSACTION_TYPE_CONSUMPTION(21, "消费返利"), TRANSACTION_TYPE_SALE(22, "销售返佣"), TRANSACTION_TYPE_DISTRIBUTION(
      23, "分销返佣");

  private int type;
  private String title = null;

  RechargeTypeEnum(int type, String title) {
    this.type = type;
    this.title = title;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
