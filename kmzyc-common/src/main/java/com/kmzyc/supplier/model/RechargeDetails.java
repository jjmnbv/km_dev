package com.kmzyc.supplier.model;

import java.io.Serializable;
import java.util.Date;


public class RechargeDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号id
     */
    private Integer accountId;
    /**
     * 登陆id
     */
    private Integer loginId;
    /**
     * 账号余额
     */
    private Double amountAvlibal;
    /**
     * 账号状态
     */
    private Integer accountStatus;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 冻结金额
     */
    private Double amountFrozen;
    /**
     * 充值单号
     */
    private String accountNumber;
    /**
     * 充值额
     */
    private Double amount;
    /**
     * 充值状态
     */
    private Integer status;
    /**
     * 支付银行
     */
    private String tranBank;
    /**
     * 开始时间
     */
    private Date aginCreateDate;
    /**
     * 结束时间
     */
    private Date endCreateDate;
    /**
     * 交易类型
     */
    private Integer type;

    @Override
    public String toString() {
        return "RechargeDetails [accountId=" + accountId + ", loginId="
                + loginId + ", amountAvlibal=" + amountAvlibal
                + ", accountStatus=" + accountStatus + ", createDate="
                + createDate + ", amountFrozen=" + amountFrozen + ", accountNumber=" + accountNumber + ", amount="
                + amount + ", status=" + status + ", tranBank=" + tranBank
                + ", aginCreateDate=" + aginCreateDate + ", endCreateDate="
                + endCreateDate + "]";
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Double getAmountAvlibal() {
        return amountAvlibal;
    }

    public void setAmountAvlibal(Double amountAvlibal) {
        this.amountAvlibal = amountAvlibal;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTranBank() {
        return tranBank;
    }

    public void setTranBank(String tranBank) {
        this.tranBank = tranBank;
    }

    public Date getAginCreateDate() {
        return aginCreateDate;
    }

    public void setAginCreateDate(Date aginCreateDate) {
        this.aginCreateDate = aginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Double getAmountFrozen() {
        return amountFrozen;
    }

    public void setAmountFrozen(Double amountFrozen) {
        this.amountFrozen = amountFrozen;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}