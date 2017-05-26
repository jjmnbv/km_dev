package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPayInfo  implements Serializable {
    private static final long serialVersionUID = 1L;
    
 
    /**
     * 支付平台代码
     */
    private String platformCode;
    /**
     * 支付标识 1-- 支付，2--退款，3--余额充值
     */
    private String flag;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 第三方支付流水号
     */
    private String outsidePayStatementNo;
    /**
     * 下单账号
     */
    private String customerAccount;
    /**
     * 交易金额
     */
    private BigDecimal orderMoney;
    /**
     * 交易时间
     */
    private String operateDate;
    
   
    public String getPlatformCode() {
        return platformCode;
    }
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getOutsidePayStatementNo() {
        return outsidePayStatementNo;
    }
    public void setOutsidePayStatementNo(String outsidePayStatementNo) {
        this.outsidePayStatementNo = outsidePayStatementNo;
    }
    public String getCustomerAccount() {
        return customerAccount;
    }
    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }
    public BigDecimal getOrderMoney() {
        return orderMoney;
    }
    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }
    public String getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }
   
    
}
