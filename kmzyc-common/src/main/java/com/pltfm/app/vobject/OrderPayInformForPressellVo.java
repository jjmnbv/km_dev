package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderPayInformForPressellVo {

    /**
     * 订单号
     */
    private String orderCode;
    /**
     * ITEM ID
     */
    private Long orderItemId;
    /**
     * 尾款通知短信发送标识
     */
    private int messageSendFlag;
    /**
     * 订金支付完成时间
     */
    private Date depositPayFinishTime;
    /**
     * 尾款支付通知手机
     */
    private String informPayTel;

    /**
     * 尾款支付截止时间
     */
    private Date finalpayEndTime;
    /**
     * SKU CODE
     */
    private String productSkuCode;
    /**
     * 订单状态
     */
    private Long orderStatus;
    /**
     * 应付金额
     */
    private BigDecimal amountPayable;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getMessageSendFlag() {
        return messageSendFlag;
    }

    public void setMessageSendFlag(int messageSendFlag) {
        this.messageSendFlag = messageSendFlag;
    }

    public Date getDepositPayFinishTime() {
        return depositPayFinishTime;
    }

    public void setDepositPayFinishTime(Date depositPayFinishTime) {
        this.depositPayFinishTime = depositPayFinishTime;
    }

    public String getInformPayTel() {
        return informPayTel;
    }

    public void setInformPayTel(String informPayTel) {
        this.informPayTel = informPayTel;
    }

    public Date getFinalpayEndTime() {
        return finalpayEndTime;
    }

    public void setFinalpayEndTime(Date finalpayEndTime) {
        this.finalpayEndTime = finalpayEndTime;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public Long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(BigDecimal amountPayable) {
        this.amountPayable = amountPayable;
    }

    public String getDepositPayFinishTimeFormartMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        if (null == depositPayFinishTime) {
            return "";
        } else {
            return sdf.format(depositPayFinishTime);
        }
    }

    public String getFinalpayEndTimeFormartYMDHMS() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

        if (null == finalpayEndTime) {
            return "";
        } else {
            return sdf.format(finalpayEndTime);
        }
    }

}
