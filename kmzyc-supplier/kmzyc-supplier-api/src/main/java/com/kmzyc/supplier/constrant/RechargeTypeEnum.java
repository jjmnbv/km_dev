package com.kmzyc.supplier.constrant;

/**
 *　账户余额交易类型
 * 
 * @author luoyi
 * @createDate 2013/11/08
 * 
 */
public enum RechargeTypeEnum {
	ONLINERECHARGE(1, "在线充值"),
	BACKGROUNDRECHARGE(2, "后台充值"),
	BALANCERECHARGE(3, "余额支付"),
	CANCELORDER(4, "取消订单"),
	ORDERRETURN(5, "订单退款"),
	ENCHASHMENT(6, "取现"),
	RESERVEOPENED(8,"预备金开通"),
	CHANGEINRESERVE(9,"预备金变更"),
	READYGOLDOFF(10,"预备金关闭"),
	PAYMENTORDER(11,"预备金支付订单"),
	ONKINEPAYMENTPREPARATION(12,"预备金在线还款"),
	RESERVEGOLDORDERREFUND(13,"预备金订单退款"),
	READYTOCANCELTHEORDER(14,"预备金取消订单"),
	RESERVEGOLDADJUSTMENT(15,"预备金调整"),
	INVITATIONAWARD(16,"邀请奖励"),
	MERCHANTCLEARUBG(17,"商家结算"),
	BUSINESSESTAKENOW(18,"商家取现"),
	BAKANCEFREEZE(19,"余额冻结"),
	BALANCETHAWING(20,"余额解冻"),
	CONSUMERREBATES(21,"消费返利"),
	SALESRETURN(22,"销售返佣"),
	DISTRIBUTIONRETURNCOMMISSION(23,"分销返佣"),
	TRANSACTION_TYPE_PROMOTION_PAY(24,"活动缴费"),
	TRANSACTION_TYPE_PROMOTION_REFUND(25,"活动退款");
	private int type;
	private String title = null;

	RechargeTypeEnum(int type, String title) {
		this.type = type;
		this.title = title;
	}

	public int getType() {
		return type;
	}

    private void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

    private void setTitle(String title) {
		this.title = title;
	}
}