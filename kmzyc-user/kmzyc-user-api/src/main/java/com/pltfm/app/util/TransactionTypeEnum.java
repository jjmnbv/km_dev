package com.pltfm.app.util;

/**
 * 交易流水类型枚举类
 * 
 * @author lijianjun
 * @since 15-06-26
 */
public enum TransactionTypeEnum {
    TRANSACTION_TYPE_ONLINE_RECHARGE(1, "在线充值"), TRANSACTION_TYPE_BACKGROUND_RECHARGE(2, "后台充值"), TRANSACTION_TYPE_BALANCE_PAYMENT(
            3, "余额支付"), TRANSACTION_TYPE_CANCEL_ORDER(4, "取消订单"), TRANSACTION_TYPE_ORDER_REFUND(5,
            "订单退款"), TRANSACTION_TYPE_TAKE_NOW(6, "取现"), /*TRANSACTION_TYPE_RESERVE_OPEN(8, "预备金开通"), TRANSACTION_TYPE_RESERVE_UPDATE(
            9, "预备金变更"), TRANSACTION_TYPE_RESERVE_CLOSE(10, "预备金关闭"), TRANSACTION_TYPE_RESERVE_ORDER_PAYMENT(
            11, "预备金支付订单"), TRANSACTION_TYPE_RESERVE_REPAYMENT_ONLINE(12, "预备金在线还款"), TRANSACTION_TYPE_RESERVE_ORDER_REFUND(
            13, "预备金订单退款"), TRANSACTION_TYPE_RESERVE_ORDER_CANCEL(14, "预备金取消订单"), TRANSACTION_TYPE_RESERVE_UPDATEMENT(
            15, "预备金调整"), TRANSACTION_TYPE_INVITATION_AWARD(16, "邀请奖励"),*/ TRANSACTION_TYPE_MERCHAANT_CLEARING(
            17, "供应商结算"), TRANSACTION_TYPE_MERCHAANT_TAKE(18, "商家取现"), TRANSACTION_TYPE_BALANCE_FROZEN(
            19, "余额冻结"), TRANSACTION_TYPE_BALANCE_THAW(20, "余额解冻"), /*TRANSACTION_TYPE_CONSUMER_REBATES(
            21, "消费返利"), TRANSACTION_TYPE_PROMOTION_RETURN(22, "销售返佣"), TRANSACTION_TYPE_DISTRIBUTION_RETURN(
            23, "分销返佣"),*/ TRANSACTION_TYPE_PROMOTION_PAY(24, "活动缴费"), TRANSACTION_TYPE_PROMOTION_REFUND(
            25, "活动退款");

    private Integer type;
    private String title;

    TransactionTypeEnum(Integer type, String title) {
        this.type = type;
        this.title = title;
    }

    public Integer getType() {
        return type;
    }


    public String getTitle() {
        return title;
    }




    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("TransactionTypeEnum[type=").append(this.type).append(",title=")
                .append(this.title).append("]");
        return strBuilder.toString();
    }



}
