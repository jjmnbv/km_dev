package com.pltfm.app.enums;


/**
 * 活动报名状态
 * 
 * @author xkj
 *
 */
public enum ActivityPaymentStatus {
    /*
     * 1：待缴费 2：已缴费 3：待退款 4：已退款
     */
    NOT_PAY(1, "待缴费"), PAYED(2, "已缴费"), NOT_REFUND(3, "待退款"), REFUNDED(4, "已退款"), NEEDLESSREFUND(5,
            "无需退款");

    private Integer status;
    private String title = null;


    ActivityPaymentStatus(Integer status, String title) {
        this.status = status;
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("ActivityPaymentStatus[status=").append(this.status).append(",title=")
                .append(this.title).append("]");
        return strBuilder.toString();
    }
}
